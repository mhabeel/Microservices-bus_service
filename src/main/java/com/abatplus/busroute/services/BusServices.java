package com.abatplus.busroute.services;
import com.abatplus.busroute.model.Bus;

import java.util.List;

public interface BusServices {

    public Bus SaveBus (Bus bus);

    public void updateBus (int busId, Bus bus);

    public void deleteBus (int busId );

    public void addBusToRoute (int busId , int rauteId);

    public List<Bus> findBus (int busId);

    public List<Bus> findAllBuses();
}
