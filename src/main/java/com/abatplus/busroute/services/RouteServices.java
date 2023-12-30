package com.abatplus.busroute.services;

import com.abatplus.busroute.model.Route;

import java.util.List;


public interface RouteServices {


    // Save Rout  (Post)
    public Route saveRoute(Route route);

    //update Route ()
    public void updateRoute(int routeid, Route route);

    //deleat Route
    public void deleteRoute(int routeid);

    //Find Route
    public List<Route> findRoute(String aktuelOrt, String zielOrt);

    public List<Route> findRouteByPrice(double gunstigePreice, String aktuelOrt, String zielOrt);

    public List<Route> findRouteBySpeed(double maxGeschwindigkeit, String aktuelOrt, String zielOrt);

    public List<Route> getAlleRoute();


}