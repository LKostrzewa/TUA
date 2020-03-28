package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;


import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;


@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:app/jdbc/ssbd02authDS'}",
        callerQuery = "#{'select password from public.auth_view where login = ?'}",
        groupsQuery = "select access_level from public.auth_view where login = ?",
        hashAlgorithm = BCryptPasswordHash.class
)


@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login/login.xhtml?redirect=true",
                errorPage = "/login/errorLogin.xhtml?redirect=true",
                useForwardToLogin = true
        )
)

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {

}

