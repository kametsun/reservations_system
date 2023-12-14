package client_system.view.component;

import java.awt.*;
import java.util.List;
import client_system.model.Facility;

public class ChoiceFacility extends Choice {
    public ChoiceFacility(){
        List<String> facilityNames = Facility.getAllFacilityNames();
        for (String name : facilityNames) add(name);
    }
}
