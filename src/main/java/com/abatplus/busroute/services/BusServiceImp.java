package com.abatplus.busroute.services;

import com.abatplus.busroute.model.Bus;
import com.abatplus.busroute.model.Route;
import com.abatplus.busroute.repository.BusRepository;
import com.abatplus.busroute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusServiceImp implements BusServices{

    private BusRepository busRepository;
    private RouteRepository routeRepository;

    @Autowired
    public BusServiceImp(BusRepository busRepository, RouteRepository routeRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }
    @Override
    public Bus SaveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Override
    public void updateBus(int busId, Bus updatedBus) {
        Optional<Bus> optionalBus = busRepository.findById(busId);

        if (optionalBus.isPresent()) {
            Bus existingBus = optionalBus.get();

            // Update properties with new values
            existingBus.setBusFahrer(updatedBus.getBusFahrer());
            existingBus.setPreis(updatedBus.getPreis());
            existingBus.setGeschwindigkeit(updatedBus.getGeschwindigkeit());

            // Save the updated entity back to the database
            busRepository.save(existingBus);
        } else {
            // Handle the case where the bus with the given ID is not found
            throw new IllegalArgumentException("Bus not found with ID: " + busId);
        }
    }

    @Override
    public void deleteBus(int busId) {
        busRepository.deleteById(busId);
    }

    @Override
    public void addBusToRoute(int busId, int routeId) {
        // Find the Bus by busId
        Optional<Bus> optionalBus = busRepository.findById(busId);
        Bus bus = optionalBus.orElseGet(Bus::new);

        // Find the Route by routeId
        Optional<Route> optionalRoute = routeRepository.findById(routeId);
        Route route = optionalRoute.orElseGet(Route::new);

        // Add the Bus to the Route using the existing method in the Route class
        route.addBusToRoute(bus);

        // Save the updated Route
        routeRepository.save(route);
    }

    @Override
    public List<Bus> findBus(int busId) {

        return busRepository.findById(busId).stream().collect(Collectors.toList());
    }

    public List<Bus> findAllBuses(){
        return busRepository.findAll();
    }
}
