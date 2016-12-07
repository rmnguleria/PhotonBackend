package com.expedia.web.rest;

import com.expedia.domain.User;
import com.expedia.repository.UserRepository;
import com.expedia.security.LdapSearchUser;
import com.expedia.security.jwt.JWTConfigurer;
import com.expedia.security.jwt.TokenProvider;
import com.expedia.web.rest.dto.JWTToken;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserJWTController {

    private static final Logger logger = LoggerFactory.getLogger(UserJWTController.class);

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private UserRepository userRepository;

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
            Optional<User> loggedInUser = userRepository.findOneByLogin(loginDTO.getUsername());

            if(!loggedInUser.isPresent()){
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
            }

            boolean rememberMe = (loginDTO.isRememberMe() == null) ? false : loginDTO.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException exception) {
            logger.error("Error while authenticating", exception.getMessage(),exception);
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}

