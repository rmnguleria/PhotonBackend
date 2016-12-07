package com.expedia.web.rest;

import com.expedia.service.UserService;
import com.expedia.web.rest.dto.RequestRoleDTO;
import com.expedia.web.rest.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class AccountResource {

    private static final Logger logger = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserService userService;

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount() {
        logger.debug("Fetching user info");
        UserDTO userDTO = userService.getUserWithRoleInfo();
        logger.debug("User Details fetched",userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * POST  /requestrole : Request to Add a new Role for the current user.
     **/
    @RequestMapping(value = "/requestrole",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> requestRole(@RequestBody RequestRoleDTO requestRoleDTO) {
        userService.requestNewRole(requestRoleDTO);
        return new ResponseEntity<>("Done", HttpStatus.OK);
    }

}

