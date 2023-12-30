package com.abatplus.busroute.controller;

import com.abatplus.busroute.model.Bus;
import com.abatplus.busroute.model.Route;
import com.abatplus.busroute.services.BusServices;
import com.abatplus.busroute.services.RouteServices;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RouteController {

     private RouteServices routeServices;
     private BusServices busServices;
    @Autowired
    public RouteController(RouteServices routeService, BusServices busServices) {
        this.routeServices = routeService;
        this.busServices = busServices;
    }
    @Hidden
    @PostMapping (value = "/post")
    public Route addRoute(@RequestBody Route route){

        return routeServices.saveRoute(route);
    }
    @Operation(summary = "get all existing routes")
    @GetMapping(value = "/routs")
    public List<Route> getAlleRoute(){
        return routeServices.getAlleRoute();
    }
    @Operation(summary = "get all existing buses")
    @GetMapping("/buses")
    public List<Bus> getAllBuses(){
        return busServices.findAllBuses();
    }
    @Hidden
    @PutMapping("/add/{busId}/{routeId}")
    public void addBusToRoute(@PathVariable int busId,@PathVariable int routeId){
        busServices.addBusToRoute(busId,routeId);
    }

    @Hidden
    @PostMapping("/save_bus")
    public void createBus(@RequestBody Bus bus) {
        busServices.SaveBus(bus);

    }
    @Hidden
    @DeleteMapping("/delete/route/{id}")
    public void deleteRoute(@PathVariable int id){
        routeServices.deleteRoute(id);
    }
    @Hidden
    @DeleteMapping("/delete/bus/{id}")
    public void deleteBus(@PathVariable int id){
        busServices.deleteBus(id);
    }

   @Operation(summary = "get the particular route by max price, current location and destination")
   @GetMapping("filter/preis/{minPreis}/{aktuelOrt}/{zielOrt}")
    public List<Route> findRouteByPrice(int minPreis, String aktuelOrt, String zielOrt){

        return routeServices.findRouteByPrice(minPreis,aktuelOrt,zielOrt);

    }
    @Operation(summary = "getting particular route by max speed, current location and destination")
    @GetMapping("filter/speed/{maxGeschwindigkeit}/{aktuelOrt}/{zielOrt}")
    public List<Route> findRouteBySpeed(int maxGeschwindigkeit, String aktuelOrt, String zielOrt){

        return routeServices.findRouteBySpeed(maxGeschwindigkeit,aktuelOrt,zielOrt);

    }





}
