package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;

public class YachtNotAssignedException extends AppBaseException {
    public static final String YACHT_MESSAGE_KEY = "exception.yachtNotAssigned";
    private Yacht yacht;

    public YachtNotAssignedException(String message) {
        super(message);
    }

    public static YachtNotAssignedException createYachtNotAssignedException(Yacht yacht) {
        YachtNotAssignedException yachtNotAssignedException = new YachtNotAssignedException(YACHT_MESSAGE_KEY);
        yachtNotAssignedException.setYacht(yacht);
        return yachtNotAssignedException;
    }

    public Yacht getYacht() {
        return yacht;
    }

    public void setYacht(Yacht yacht) {
        this.yacht = yacht;
    }
}


