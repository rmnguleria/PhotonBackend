package com.expedia.service;

import com.expedia.domain.*;
import com.expedia.repository.GroupsRepository;
import com.expedia.repository.UserGroupRoleRepository;
import com.expedia.repository.UserRepository;
import com.expedia.security.SecurityUtils;
import com.expedia.web.rest.dto.GroupRoleDTO;
import com.expedia.web.rest.dto.RequestRoleDTO;
import com.expedia.web.rest.dto.RoleDTO;
import com.expedia.web.rest.dto.UserDTO;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserGroupRoleRepository userGroupRoleRepository;

    @Inject
    private GroupsRepository groupsRepository;

    @Inject
    private GroupsService groupsService;

    @Inject
    private MailService mailService;

    @Transactional(readOnly = true)
    public UserDTO getUserWithRoleInfo(){

        HashMap<String, String> posMap = groupsService.getPOSMap();

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        QUserGroupRole userGroupRole = QUserGroupRole.userGroupRole;
        QGroupRole groupRole = QGroupRole.groupRole;
        QGroups groups = QGroups.groups;
        QRole role = QRole.role;

        JPAQuery<?> jpaQuery = new JPAQuery<Void>(em);

        UserDTO userDTO = new UserDTO(user.getLogin(),user.getFirstName(),user.getLastName(),user.getEmail());

        List<RoleDTO> rolesList = new ArrayList<>();

        List<Tuple> roles = jpaQuery.select(groupRole.id, groups.name, groups.type, role.name, userGroupRole.status, userGroupRole.approverMail)
                .from(userGroupRole, groupRole, groups, role)
                .where(userGroupRole.userId.eq(user.getId()))
                .where(userGroupRole.groupRoleId.eq(groupRole.id))
                .where(groupRole.groupId.eq(groups.id))
                .where(groupRole.roleId.eq(role.id)).fetch();

        roles.stream().forEach(row -> {
            String tenant = row.get(groups.name);
            String pos = "All";
            if("pos".equalsIgnoreCase(row.get(groups.type))){
                tenant = posMap.get(tenant);
                pos = row.get(groups.name);
            }
            rolesList.add(
                    new RoleDTO(row.get(groupRole.id),tenant,pos,row.get(role.name),row.get(userGroupRole.status), row.get(userGroupRole.approverMail))
            );
        });
        userDTO.setRoles(rolesList);
        return userDTO;
    }

    @Transactional(readOnly = true)
    public List<GroupRoleDTO> getGroupRoleDTOs(){
        QGroupRole groupRole = QGroupRole.groupRole;
        QGroups groups = QGroups.groups;
        QRole role = QRole.role;

        JPAQuery<?> jpaQuery = new JPAQuery<Void>(em);

        List<Tuple> groupRoleList = jpaQuery.select(groupRole.id,groups.name,groups.parentId,role.name)
                .from(groupRole,groups,role)
                .where(groupRole.roleId.in(5,8,9))
                .where(groupRole.groupId.eq(groups.id))
                .where(groupRole.roleId.eq(role.id))
                .fetch();

        List<Groups> groupsList = groupsRepository.findAll();
        HashMap<Integer,Groups> groupsMap = new HashMap<>();
        groupsList.forEach(group -> {
            groupsMap.put(group.getId(), group);
        });

        List<GroupRoleDTO> groupRoleDTOList = new ArrayList<>();

        groupRoleList.stream().filter( tuple ->
            tuple.get(groups.parentId) == null || // either its a brand
                    ( groupsMap.get(tuple.get(groups.parentId)) != null && // or its a pos not a region.
                            groupsMap.get(tuple.get(groups.parentId)).getParentId() != null) )
                .forEach(tuple1 -> {
                    String pointOfSale = "All";
                    String tenant = tuple1.get(groups.name);
                    if(tuple1.get(groups.parentId) != null){
                        pointOfSale = tuple1.get(groups.name);
                        tenant = groupsMap.get(groupsMap.get(tuple1.get(groups.parentId)).getParentId()).getName();
                    }
                    GroupRoleDTO groupRoleDTO = new GroupRoleDTO(tuple1.get(groupRole.id),
                            tenant,
                            pointOfSale,
                            tuple1.get(role.name));
                    groupRoleDTOList.add(groupRoleDTO);
                });
        return groupRoleDTOList;
    }

    @Transactional
    public void requestNewRole(RequestRoleDTO requestRoleDTO){
        UserDTO userDTO = getUserWithRoleInfo();
        User user = userRepository.findOneByLogin(userDTO.getLogin()).get();
        List<UserGroupRole> requestedRoles = new ArrayList<>();
        requestRoleDTO.getRequestedRoles().stream().forEach(groupRole -> {
            UserGroupRole userGroupRole = new UserGroupRole(user.getId(),
                    groupRole.getGroupRoleId(),"Waiting",requestRoleDTO.getManagerEmail());
            requestedRoles.add(userGroupRole);
        });
        List<UserGroupRole> savedRequests = userGroupRoleRepository.save(requestedRoles);
        String userGroupRoleIds = StringUtils.arrayToCommaDelimitedString(savedRequests.stream().map(UserGroupRole::getId).toArray());
        mailService.sendRequestRoleEmail(userDTO,requestRoleDTO,userGroupRoleIds,user.getId());
    }



    @Transactional
    public void takeActionOnRequest(ApprovalBO approvalBO){
        Optional<List<UserGroupRole>> userGroupRoles = userGroupRoleRepository.findByUserId(approvalBO.getUserId());
        if(!userGroupRoles.isPresent()){
            throw new IllegalArgumentException("Requested user does not exist: " + approvalBO.getUserId());
        }
        HashMap<Integer,UserGroupRole> userGroupRoleMap = new HashMap<>();
        userGroupRoles.get().forEach(userGroupRole -> {
            userGroupRoleMap.put(userGroupRole.getId(),userGroupRole);
        });
        Integer[] roleRequestIds = Arrays.asList(approvalBO.getRoleRequests().split(",")).stream().
                map(Integer::parseInt).toArray(Integer[]::new);
        List<UserGroupRole> approvedRoles = new ArrayList<>();
        for(Integer roleRequestId : roleRequestIds){
            if(userGroupRoleMap.containsKey(roleRequestId)){
                UserGroupRole requestedRole = userGroupRoleMap.get(roleRequestId);
                if(requestedRole.getStatus().equalsIgnoreCase("Waiting") && requestedRole.getApproverMail().equalsIgnoreCase(approvalBO.getManagerEmail())){
                    if(approvalBO.getAction().equalsIgnoreCase("A")) {
                        requestedRole.setStatus("Active");
                    }else{
                        requestedRole.setStatus("Rejected");
                    }
                    approvedRoles.add(requestedRole);
                }
            }
        }
        if(!approvedRoles.isEmpty()) {
            userGroupRoleRepository.save(approvedRoles);
        }
    }
}
