package com.abatplus.busroute.services;

import com.abatplus.busroute.model.Bus;
import com.abatplus.busroute.model.Route;
import com.abatplus.busroute.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImp implements RouteServices {


    private RouteRepository routeRepository;
    @Autowired
    public RouteServiceImp(RouteRepository busRepository) {
        this.routeRepository = busRepository;
    }

    @Override
    public Route saveRoute(Route route) {
      return  routeRepository.save(route);
    }

    @Override
    public void updateRoute(int routeId ,Route updatedRoute ) {
        List<Route> routes = routeRepository.findAll();

        routes.stream()
                .filter(route -> route.getRouteId() == routeId )
                .findFirst()
                .ifPresent(existingRoute -> {

                    existingRoute.setAktuelOrt(updatedRoute.getAktuelOrt());
                    existingRoute.setZielOrt(updatedRoute.getZielOrt());

                    routeRepository.save(existingRoute);
                });

    }

    @Override
    public void deleteRoute(int routeId) {
        routeRepository.deleteById(routeId);
    }

    @Override
    public List<Route> findRoute(String aktuelOrt, String zielOrt) {
        return routeRepository.findAll().stream()
                .filter(route -> route.getAktuelOrt().equalsIgnoreCase(aktuelOrt)
                        && route.getZielOrt().equalsIgnoreCase(zielOrt))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> findRouteByPrice(double minPreis, String aktuelOrt, String zielOrt ) {
        return routeRepository.findAll().stream()
                .filter(route -> route.getAktuelOrt().equalsIgnoreCase(aktuelOrt)
                        && route.getZielOrt().equalsIgnoreCase(zielOrt)
                        && route.getBuses().stream().anyMatch(busService -> busService.getPreis() >= minPreis))
                .collect(Collectors.toList());
    }


    @Override
    public List<Route> findRouteBySpeed(double maxGeschwindigkeit, String aktuelOrt, String zielOrt  ) {
        return  routeRepository.findAll().stream()
                .filter(route -> route.getAktuelOrt().equalsIgnoreCase(aktuelOrt)
                        && route.getZielOrt().equalsIgnoreCase(zielOrt)
                        && route.getBuses().stream().anyMatch(busService -> busService.getGeschwindigkeit() <= maxGeschwindigkeit))
                .collect(Collectors.toList());
    }

    @Override
    public List<Route> getAlleRoute() {
        return (List<Route>) routeRepository.findAll();
    }



}