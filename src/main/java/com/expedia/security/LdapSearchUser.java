package com.expedia.security;

import com.expedia.config.PhotonProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.HashMap;
import java.util.Hashtable;


public final class LdapSearchUser {

    public static final String ATTR_FIRST_NAME = "givenName";
    public static final String ATTR_LAST_NAME = "sn";
    public static final String ATTR_EMAIL = "mail";

    private PhotonProperties photonProperties = new PhotonProperties();

    /**
     * Finds the user in ldap
     * @param userId to be find
     * @return Map of attributes of found user
     */
    public HashMap<String, String> getLdapUser(String userId)
    {
        HashMap<String,String> resultMap = null;
        if(userId.trim().isEmpty())
        {
            return null;
        }
        Hashtable env = initializeDir();
        String filter = "(" + photonProperties.getSecurity().getAuthentication().getLdapAuth().getSearchField() +"=" + userId +")";

        DirContext ctx = null;
        try
        {
            ctx = new InitialDirContext(env);
            NamingEnumeration results = ctx.search(photonProperties.getSecurity().getAuthentication().getLdapAuth().getSearchBase(), filter, getSearchControls());

            if (results.hasMore())
            {
                resultMap = new HashMap<>();
                SearchResult res = (SearchResult) results.next();
                Attributes attr = res.getAttributes();


                addAttributeToMap(resultMap, attr, ATTR_FIRST_NAME);
                addAttributeToMap(resultMap, attr, ATTR_LAST_NAME);
                addAttributeToMap(resultMap, attr, ATTR_EMAIL);

            }
        }
        catch (NamingException e)
        {
            e.printStackTrace();
            //log.error("Error getLdapUser()", e);
        }
        finally
        {
            closeConnection(ctx);
        }
        return resultMap;
    }

    private void addAttributeToMap(HashMap<String,String> resultMap, Attributes attr, String attrName) {
        if(attr.get(attrName)!=null){
            resultMap.put(attrName,
                    attr.get(attrName).toString().replaceAll(attrName + ": ", ""));
        }
    }

    private SearchControls getSearchControls()
    {
        String[] attrIDs = {ATTR_FIRST_NAME, ATTR_LAST_NAME, ATTR_EMAIL,"manager"};
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(attrIDs);
        return searchControls;
    }

    private void closeConnection(DirContext ctx)
    {
        if(null != ctx)
        {
            try
            {
                ctx.close();
            }
            catch (NamingException e)
            {
                //log.error("Error getLdapUser()", e);
            }
        }
    }

    private Hashtable initializeDir()
    {
        Hashtable<String,String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, photonProperties.getSecurity().getAuthentication().getLdapAuth().getLdapURL());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        env.put(Context.SECURITY_PRINCIPAL, ((LdapUserDetails)auth.getPrincipal()).getDn());
        env.put(Context.SECURITY_CREDENTIALS,auth.getCredentials().toString());
        return env;
    }
}
