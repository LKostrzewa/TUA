package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;

public class YachtPortChangedException extends AppBaseException {
    public static final String NOT_ASSIGNED_MESSAGE_KEY = "exception.yachtNotAssigned";
    public static final String ASSIGNED_MESSAGE_KEY = "exception.yachtAssigned";
    private Yacht yacht;

    public YachtPortChangedException(String message) {
        super(message);
    }

    public static YachtPortChangedException createYachtNotAssignedException(Yacht yacht) {
        YachtPortChangedException yachtPortChangedException = new YachtPortChangedException(NOT_ASSIGNED_MESSAGE_KEY);
        yachtPortChangedException.setYacht(yacht);
        return yachtPortChangedException;
    }

    public static YachtPortChangedException createYachtAssignedException(Yacht yacht) {
        YachtPortChangedException yachtPortChangedException = new YachtPortChangedException(ASSIGNED_MESSAGE_KEY);
        yachtPortChangedException.setYacht(yacht);
        return yachtPortChangedException;
    }

    public Yacht getYacht() {
        return yacht;
    }

    public void setYacht(Yacht yacht) {
        this.yacht = yacht;
    }
}


