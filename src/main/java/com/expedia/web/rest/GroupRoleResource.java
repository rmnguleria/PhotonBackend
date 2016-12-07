package com.expedia.web.rest;

import com.expedia.service.UserService;
import com.expedia.web.rest.dto.GroupRoleDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupRoleResource {

    @Inject
    private UserService userService;

    /**
     * GET  /grouprole : get group role mapping
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @RequestMapping(value = "/grouprole",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<GroupRoleDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getGroupRoleDTOs());
    }

}
