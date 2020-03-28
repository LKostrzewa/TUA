package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@SessionScoped
@Named
public class CurrentUser implements Serializable {

    private String currentRole;

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;

    }

    public String getCurrentRole() {
        return currentRole;
    }


    @PostConstruct
    private void init() {
        if(isAdministrator()&&currentRole==null) currentRole = "ADMINISTRATOR";
        if(isManager()&&currentRole==null) currentRole = "MANAGER";
        if(isClient()&&currentRole==null) currentRole = "CLIENT";
        try{
            if(isNowAdministrator()) FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.xhtml");
            if(isNowManager()) FacesContext.getCurrentInstance().getExternalContext().redirect("/manager/index.xhtml");
            if(isNowClient()) FacesContext.getCurrentInstance().getExternalContext().redirect("/client/index.xhtml");
        }catch (IOException e){
            System.out.println("Could not redirect.");
        }
    }

    public boolean isUserInRole(String role) {  // sprawdza jaka jest rola zalogowanego uzytkownika
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    public boolean isAdministrator() {
        return isUserInRole("ADMINISTRATOR");
    }

    public boolean isManager() {
        return isUserInRole("MANAGER");
    }

    public boolean isClient() {
        return isUserInRole("CLIENT");
    }

    public String getCurrentUserLogin() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public String allUserAccessLevel() {
        String string = "";
        if (isAdministrator())
            string += "ADMINISTRATOR ";
        if (isManager())
            string += "MANAGER ";
        if (isClient())
            string += "CLIENT";

        return string;
    }


    public boolean isNowAdministrator() {
        return currentRole.equals("ADMINISTRATOR");
    }

    public boolean isNowManager() {
        return currentRole.equals("MANAGER");
    }

    public boolean isNowClient() {
        return currentRole.equals("CLIENT");
    }

    public String redirectAdmin(){
        currentRole = "ADMINISTRATOR";
        return "adminMain";
    }

    public String redirectManager(){
        currentRole = "MANAGER";
        return "managerMain";
    }

    public String redirectClient(){
        currentRole = "CLIENT";
        return "clientMain";
    }
}
