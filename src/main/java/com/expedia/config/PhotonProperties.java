package com.expedia.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "photon")
public class PhotonProperties {

    private final Security security = new Security();

    private final Mail mail = new Mail();

    public Mail getMail() {return mail;}

    public static class Mail{
        private String from = "coupon-admin@expedia.com";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }
    }

    public Security getSecurity() {
        return security;
    }

    public static class Security {

        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return authentication;
        }



        public static class Authentication {

            private final Jwt jwt = new Jwt();
            private final LdapAuth ldapAuth = new LdapAuth();

            public Jwt getJwt() {
                return jwt;
            }

            public LdapAuth getLdapAuth(){ return ldapAuth; }

            public static class LdapAuth {
                private String ldapDomain = "sea.corp.expecn.com";

                private String ldapURL = "ldap://ldap.sea.corp.expecn.com:389";

                private String searchBase = "OU=All Users,dc=sea,dc=corp,dc=expecn,dc=com";

                private String searchField = "sAMAccountName";

                public String getSearchBase() {
                    return searchBase;
                }

                public void setSearchBase(String searchBase) {
                    this.searchBase = searchBase;
                }

                public String getLdapDomain() {
                    return ldapDomain;
                }

                public void setLdapDomain(String ldapDomain) {
                    this.ldapDomain = ldapDomain;
                }

                public String getLdapURL() {
                    return ldapURL;
                }

                public void setLdapURL(String ldapURL) {
                    this.ldapURL = ldapURL;
                }

                public String getSearchField() {
                    return searchField;
                }

                public void setSearchField(String searchField) {
                    this.searchField = searchField;
                }
            }

            public static class Jwt {

                private String secret = "PewDiPie";



                private long tokenValidityInSeconds = 2592000;
                private long tokenValidityInSecondsForRememberMe = 2592000;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }

            }
        }
    }
}
