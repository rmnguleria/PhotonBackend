package com.expedia.service;

import com.expedia.domain.Groups;
import com.expedia.repository.GroupsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class GroupsService {

    @Inject
    private GroupsRepository groupsRepository;

    @Transactional(readOnly = true)
    public HashMap<String,String> getPOSMap(){
        HashMap<String,String> posMap = new HashMap<>();
        List<Groups> groupsList = groupsRepository.findAll();
        HashMap<Integer,Groups> groupsMap = new HashMap<>();
        groupsList.forEach(group -> {
            groupsMap.put(group.getId(), group);
        });
        groupsList.stream().
                filter(groups -> groups.getType().equalsIgnoreCase("POS")).
                forEach(pos -> {
                    Groups region = groupsMap.get(pos.getParentId());
                    if(region != null) {
                        Groups brand = groupsMap.get(region.getParentId());
                        if(brand != null) {
                            String tenant = brand.getName();
                            posMap.put(pos.getName(),tenant);
                        }
                    }
                });
        return posMap;
    }
}
