package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;

public class YachtReservedException extends AppBaseException {
    public static final String YACHT_RESERVED_KEY = "exception.yachtReserved";

    private Yacht yacht;

    public YachtReservedException(String message) {
        super(message);
    }

    public Yacht getYacht() {
        return yacht;
    }

    public void setYacht(Yacht yacht) {
        this.yacht = yacht;
    }

    public static YachtReservedException createYachtReservedException(Yacht yacht) {
        YachtReservedException yre = new YachtReservedException(YACHT_RESERVED_KEY);
        yre.setYacht(yacht);
        return yre;
    }
}
