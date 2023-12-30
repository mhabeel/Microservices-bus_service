package com.abatplus.busroute;
import com.abatplus.busroute.model.Route;
import com.abatplus.busroute.model.Bus;
import com.abatplus.busroute.repository.RouteRepository;
import com.abatplus.busroute.repository.BusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RouteRepositoryTest {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    @Test
    void testSaveRouteWithBuses() {
        // Create a Route with Buses
        Route route = new Route("Start", "End");
        Bus bus1 = new Bus("Driver1", 20, 60);
        Bus bus2 = new Bus("Driver2", 25, 70);
        route.addBusToRoute(bus1);
        route.addBusToRoute(bus2);

        // Save the Route with Buses to the database
        routeRepository.save(route);

        // Fetch the saved Route from the database
        Route savedRoute = routeRepository.findById(route.getRouteId()).orElse(null);

        // Check if the saved Route and associated Buses are retrieved correctly
        assertThat(savedRoute).isNotNull();
        assertThat(savedRoute.getAktuelOrt()).isEqualTo("Start");
        assertThat(savedRoute.getZielOrt()).isEqualTo("End");
        assertThat(savedRoute.getBuses()).hasSize(2);

        // Check if Bus details are correct
        List<Bus> savedBuses = busRepository.findByRoute(savedRoute);
        assertThat(savedBuses).hasSize(2);
        assertThat(savedBuses.get(0).getBusFahrer()).isEqualTo("Driver1");
        assertThat(savedBuses.get(1).getBusFahrer()).isEqualTo("Driver2");
    }
}
