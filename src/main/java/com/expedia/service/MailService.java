package com.expedia.service;

import com.expedia.config.PhotonProperties;
import com.expedia.web.rest.dto.RequestRoleDTO;
import com.expedia.web.rest.dto.RoleInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Locale;

/**
 * Service for sending e-mails.
 */
@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private PhotonProperties photonProperties;

    @Inject
    private MessageSource messageSource;

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        logger.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
            message.setTo(to);
            message.setFrom(photonProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);

            javaMailSender.send(mimeMessage);
            logger.info("Sent e-mail to User '{}'"+ to);
        } catch (Exception e) {
            logger.error("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage(),e);
        }
    }


    public void sendRequestRoleEmail(RequestRoleDTO requestRoleDTO){
        Locale locale = Locale.ENGLISH;
        Context context = new Context(locale);
        context.setVariable("systemmail",photonProperties.getMail().getFrom());
        context.setVariable("requestRoleDTO",requestRoleDTO);
        String userGroupRoleIds = StringUtils.arrayToCommaDelimitedString(requestRoleDTO.getRequestedRoles().stream().map(RoleInfoDTO::getGroupRoleId).toArray());
        context.setVariable("ids",new String(Base64.getEncoder().encode(userGroupRoleIds.getBytes())));
        context.setVariable("userid",new String(Base64.getEncoder().encode(requestRoleDTO.getUserId().getBytes())));
        context.setVariable("manageremail",new String(Base64.getEncoder().encode(requestRoleDTO.getManagerEmail().getBytes())));
        String content = templateEngine.process("RoleRequest", context);
        String subject = "Role Request";
        sendEmail(requestRoleDTO.getManagerEmail(), subject, content, false, true);
    }
}
