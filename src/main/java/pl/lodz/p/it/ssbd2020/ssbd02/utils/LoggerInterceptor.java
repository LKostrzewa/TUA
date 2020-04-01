package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggerInterceptor implements Serializable {

    private static final Logger LOGGER = Logger.getGlobal();


    private final SecurityContext securityContext;

    @Inject
    public LoggerInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object collectLoggingInformation(InvocationContext invocationContext) throws Exception {
        String className = invocationContext.getTarget().getClass().getSimpleName();
        String methodName = invocationContext.getMethod().getName();
        String callerName;

        if (securityContext.getCallerPrincipal() != null) {
            callerName = securityContext.getCallerPrincipal().getName();
        } else {
            callerName = "unauthenticated";
        }

        StringBuilder param = new StringBuilder();

        if(invocationContext.getParameters() != null){
            for(Object p : invocationContext.getParameters()){
                param.append(p.getClass().getSimpleName()).append("=").append(p.toString()).append(",");
            }
        }

        LOGGER.log(Level.INFO,
                "{0} - {1}({2}) called by: {3}",
                new Object[] {className,methodName, param.toString(),callerName});


        return invocationContext.proceed();
    }
}
