package pl.lodz.p.it.ssbd2020.ssbd02.exceptions.listener;

import com.sun.faces.application.ActionListenerImpl;

import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRequiredException;
import javax.ejb.NoSuchEJBException;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionActionListener extends ActionListenerImpl {

    private static final Logger LOGGER = Logger.getGlobal();

    @Override
    public void processAction(ActionEvent actionEvent) {
        try {
            super.processAction(actionEvent);
        }
        catch (FacesException e) {
            Throwable cause = e.getCause().getCause();
            try{
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                if(cause instanceof EJBAccessException)
                    externalContext.redirect(request.getContextPath() + "/errors/ejbAccessExp.xhtml");
                else if (cause instanceof EJBTransactionRequiredException)
                    externalContext.redirect(request.getContextPath() + "/errors/ejbTranReqExp.xhtml");
                else if(cause instanceof NoSuchEJBException)
                    externalContext.redirect(request.getContextPath() + "/errors/noSuchEJBExp.xhtml");
                else if(cause instanceof EJBException)
                    externalContext.redirect(request.getContextPath() + "/errors/ejbExp.xhtml");
            }
            catch (IOException exception) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                exception.printStackTrace(pw);
                String exceptionInfo = exception.getClass().getName() + ": \"" + exception.getMessage() + "\""
                        + sw.toString();

                LOGGER.log(Level.WARNING,
                        "IOException during ejb exception handling" + exceptionInfo
                        );
            }
        }

    }
}
