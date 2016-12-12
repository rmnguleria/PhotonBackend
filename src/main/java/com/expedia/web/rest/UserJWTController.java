package com.expedia.web.rest;

import com.expedia.config.Constants;
import com.expedia.security.LdapSearchService;
import com.expedia.security.jwt.JWTConfigurer;
import com.expedia.security.jwt.TokenProvider;
import com.expedia.web.rest.dto.LUserDTO;
import com.expedia.web.rest.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class UserJWTController {

    private static final Logger logger = LoggerFactory.getLogger(UserJWTController.class);

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private LdapSearchService ldapSearchService;

    //@Inject
    //private UserRepository userRepository;

    @Inject
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO loginDTO, HttpServletResponse response) {

        logger.debug("Attempting to login the user", loginDTO.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //Optional<User> loggedInUser = userRepository.findOneByLogin(loginDTO.getUsername());
            /*if(!loggedInUser.isPresent()){
                logger.debug("Adding new user to coupon admin tool",loggedInUser.get().getLogin());
                LdapSearchUser ldapSearchUser = new LdapSearchUser();
                HashMap<String,String> map = ldapSearchUser.getLdapUser(loginDTO.getUsername());
                if (map != null) {
                    User user = new User(loginDTO.getUsername(), map.get(LdapSearchUser.ATTR_FIRST_NAME), map.get(LdapSearchUser.ATTR_LAST_NAME), "Active");
                    user.setCreatedBy("System");
                    userRepository.save(user);
                }
            }else if(StringUtils.isEmpty(loggedInUser.get().getEmail())){
                LdapSearchUser ldapSearchUser = new LdapSearchUser();
                HashMap<String,String> map = ldapSearchUser.getLdapUser(loginDTO.getUsername());
                User logInUser = loggedInUser.get();
                if (map != null) {
                    logInUser.setEmail(map.get(LdapSearchUser.ATTR_EMAIL));
                    userRepository.save(logInUser);
                }
            }*/

            HashMap<String,String> userMap = ldapSearchService.getLdapUser(loginDTO.getUsername());
            String managerId = userMap.get(Constants.ATTR_MANAGER);
            managerId = managerId.substring(managerId.indexOf('(')+1,managerId.indexOf(')'));
            HashMap<String,String> managerMap = ldapSearchService.getLdapUser(managerId);
            boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            LUserDTO lUserDTO = new LUserDTO(jwt,userMap.get(Constants.ATTR_FIRST_NAME) + " " + userMap.get(Constants.ATTR_LAST_NAME),loginDTO.getUsername(),userMap.get(Constants.ATTR_EMAIL),managerMap.get(Constants.ATTR_EMAIL),managerMap.get(Constants.ATTR_FIRST_NAME)+ " " + managerMap.get(Constants.ATTR_LAST_NAME));
            return ResponseEntity.ok(lUserDTO);
        } catch (AuthenticationException exception) {
            logger.error("Error while authenticating", exception.getMessage(),exception);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}

