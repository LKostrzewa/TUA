package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    private final Logger LOGGER = Logger.getGlobal();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting up!");
        LOGGER.log(Level.INFO,"Context starting up!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.log(Level.INFO,"Context shutting down!");
        EntityManagerFactory emfMOK = Persistence.createEntityManagerFactory("ssbd02mokPU");
        EntityManagerFactory emfMOJ = Persistence.createEntityManagerFactory("ssbd02mojPU");
        emfMOK.close();
        emfMOJ.close();
    }
}