package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;


import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;


@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'jdbc/ssbd02authDS'}",
        callerQuery = "#{'select password from public.auth_view where login = ?'}",
        groupsQuery = "select access_level from public.auth_view where login = ?",
        hashAlgorithm = BCryptPasswordHash.class
)


@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login/login.xhtml",
                errorPage = "/login/errorLogin.xhtml",
                useForwardToLogin = false
        )
)

@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}

