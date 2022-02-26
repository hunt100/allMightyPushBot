package data.enums;

import data.constants.ApplicationConstants;

public enum LocationHost {
    DANILA(ApplicationConstants.Commands.Gulamba.TO_DANILA_MSG),
    VLADIMIR(ApplicationConstants.Commands.Gulamba.TO_VLADIMIR_MSG),
    ANVAR(ApplicationConstants.Commands.Gulamba.TO_ANVAR_MSG);

    private final String locationKey;

    LocationHost(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getLocationKey() {
        return locationKey;
    }
}
