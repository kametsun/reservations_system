package client_system.view.component;

import model.Facility;

import java.awt.*;
import java.util.List;

public class ChoiceFacility extends Choice {
    public ChoiceFacility(){
        List<String> facilityNames = Facility.getAllFacilityNames();
        for (String name : facilityNames) add(name);
    }
}
