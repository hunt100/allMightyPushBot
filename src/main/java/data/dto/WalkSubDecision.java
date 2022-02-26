package data.dto;

import data.enums.LocationHost;

public class WalkSubDecision extends Decision {

    private final LocationHost locationHost;

    public WalkSubDecision(LocationHost locationHost) {
        super();
        this.locationHost = locationHost;
    }

    public LocationHost getLocationHost() {
        return locationHost;
    }

}
