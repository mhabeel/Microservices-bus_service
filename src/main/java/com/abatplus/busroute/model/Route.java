package com.abatplus.busroute.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Route")
public class Route {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;
    @NotEmpty(message = "Ort koente nicht leer sein")
    @Column
    private String aktuelOrt;
    @NotEmpty(message = "Ort koente nicht leer sein")
    @Column
    private String zielOrt;

    @OneToMany(mappedBy="route")
    //@OneToMany(targetEntity = BusService.class, cascade = CascadeType.ALL)
    //@JoinColumn(name = "fg", referencedColumnName = ("routeId"))
    private List<Bus> buses;

    public Route() {

        this.buses = new ArrayList<>();

    }

    public Route(String aktuelOrt, String zielOrt ) {

        this.aktuelOrt = aktuelOrt;
        this.zielOrt = zielOrt;
        this.buses = new ArrayList<>();
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getAktuelOrt() {
        return aktuelOrt;
    }

    public void setAktuelOrt(String aktuelOrt) {
        this.aktuelOrt = aktuelOrt;
    }

    public String getZielOrt() {
        return zielOrt;
    }

    public void setZielOrt(String zielOrt) {
        this.zielOrt = zielOrt;
    }

    public List<com.abatplus.busroute.model.Bus> getBuses() {

        return buses;
    }
    public void addBusToRoute(Bus bus) {
        if (bus != null) {
            this.buses.add(bus);
            bus.setRoute(this);
        }
    }
    public void setBuses(List<com.abatplus.busroute.model.Bus> buses) {
        this.buses = buses;
    }
}
