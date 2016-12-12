package com.expedia.security;

import com.expedia.config.Constants;
import com.expedia.config.PhotonProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.HashMap;
import java.util.Hashtable;

@Service
public class LdapSearchService {

    @Inject
    private PhotonProperties photonProperties;

    String[] attrIDs = {Constants.ATTR_FIRST_NAME, Constants.ATTR_LAST_NAME, Constants.ATTR_EMAIL,Constants.ATTR_MANAGER};

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
            NamingEnumeration results = ctx.search(photonProperties.getSecurity().getAuthentication().getLdapAuth().getSearchBase(),
                    filter, getSearchControls());

            if (results.hasMore())
            {
                resultMap = new HashMap<>();
                SearchResult res = (SearchResult) results.next();
                Attributes attr = res.getAttributes();

                for(String attribute : attrIDs){
                    if(attr.get(attribute) != null)
                        resultMap.put(attribute, attr.get(attribute).toString().replaceAll(attribute + ": ",""));
                }

                //addAttributeToMap(resultMap, attr, Constants.ATTR_FIRST_NAME);
                //addAttributeToMap(resultMap, attr, Constants.ATTR_LAST_NAME);
                //addAttributeToMap(resultMap, attr, Constants.ATTR_EMAIL);

            }
        }
        catch (NamingException e)
        {
            e.printStackTrace();
            //log.error("Error getLdapUser()", e);
        }
        finally
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
        return resultMap;
    }

    /*private void addAttributeToMap(HashMap<String,String> resultMap, Attributes attr, String attrName) {
        if(attr.get(attrName)!=null){
            resultMap.put(attrName,
                    attr.get(attrName).toString().replaceAll(attrName + ": ", ""));
        }
    }*/

    private SearchControls getSearchControls()
    {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(attrIDs);
        return searchControls;
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
