package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import javax.servlet.http.HttpSessionListener;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa loggera, będąca obiektem przechwytującym (interceptor),
 * wykonuje zapisy do dziennika zdarzeń.
 */

public class LoggerInterceptor implements Serializable, HttpSessionListener {
    private static final Logger LOGGER = Logger.getGlobal();
    @Inject
    private  SecurityContext securityContext;

    public LoggerInterceptor() {
    }




    /**
     * Metoda opakowująca oznaczoną metodę. Loguje odpowiednie informacje do dziennika zdarzeń.
     *
     * @param invocationContext informacje kontekstowe o przechwyconym wywołaniu
     * @return wynik wywołania metody kontekstu
     * @throws Exception rzucony przez opakowaną metodę
     */
    @AroundInvoke
    public Object collectLoggingInformation(InvocationContext invocationContext) throws Exception {

        String className = invocationContext.getTarget().getClass().getName();
        String methodName = invocationContext.getMethod().getName();
        String callerName;

        if (securityContext.getCallerPrincipal() != null) {
            callerName = securityContext.getCallerPrincipal().getName();
        } else {
            callerName = "unauthenticated";
        }

        StringBuilder param = new StringBuilder();

        if (invocationContext.getParameters() != null) {
            for (Object p : invocationContext.getParameters()) {
                if (p.getClass().getSimpleName().equals("String")) {
                    param.append(p.getClass().getSimpleName()).append("=").append("***").append(",");
                } else {
                    param.append(p.getClass().getSimpleName()).append("=").append(p.toString()).append(",");
                }
            }
        }

        LOGGER.log(Level.INFO,
                "{0} - {1}({2}) called by: {3}",
                new Object[]{className, methodName, param.toString(), callerName});

        Object result;
        try {
            result = invocationContext.proceed();
        } catch (Exception exception) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String exceptionInfo = exception.getClass().getName() + ": \"" + exception.getMessage() + "\"";
            Throwable cause = exception.getCause();

            if (cause != null) {
                exceptionInfo += " caused by " + cause.getClass().getName() + ": \"" + cause.getMessage() + "\"";
            }

            exceptionInfo += sw.toString();

            LOGGER.log(Level.WARNING,
                    "{0} - {1}({2}) called by: {3} has thrown\n{4}",
                    new Object[]{className, methodName, param.toString(), callerName, exceptionInfo});

            throw exception;
        }

        LOGGER.log(Level.INFO,
                "{0} - {1}({2}) called by: {3} has returned {4}",
                new Object[]{className, methodName, param.toString(), callerName, result});

        return result;
    }
}
