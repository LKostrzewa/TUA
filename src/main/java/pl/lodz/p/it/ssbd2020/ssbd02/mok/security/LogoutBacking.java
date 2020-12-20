package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.IOException;
import java.io.Serializable;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

/***
 * Klasa której metoda powoduje powrót do głównej strony logowania (mechanizm wylogowywania).
 */
@Named
@RequestScoped
@Interceptors(LoggerInterceptor.class)
public class LogoutBacking implements Serializable {
    @Inject
    private ExternalContext externalContext;

    @Counted(unit = MetricUnits.NONE,
            name = "logout_method_count",
            absolute = true,
            displayName = "Method invocation",
            description = "Metrics to show how many times logout method was called.",
            tags = "method_invocation=logout")
    @Timed(name = "logout_handling_time",
            description = "Time of handling the logout method",
            unit = MetricUnits.MILLISECONDS,
            tags = "method_handling_time=logout",
            absolute = true)
    public void submit() throws IOException {
        externalContext.redirect(externalContext.getRequestContextPath() + "/login/login.xhtml");
        externalContext.invalidateSession();
    }
}