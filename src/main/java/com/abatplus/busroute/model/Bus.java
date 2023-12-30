
package com.abatplus.busroute.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Bus_service")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;
    @NotEmpty(message = "Name koente nicht leer sein")
    @Column
    private String busFahrer;
    @Min(value = 0, message = "Preis koente nicht negativ sein")
    @NotNull(message = "Preis koente nicht leer sein")
    @Column
    private double preis;
    @Min(value = 0, message = "Geschwindigkeit koente nicht negativ sein")
    @Max(value= 200 , message = "Geschwindigkeit darf nicht mehr als 200 KpH sein")
    @NotNull(message = "Geschwindigkeit koente nicht leer sein")
    @Column
    private double geschwindigkeit;

    @ManyToOne()
    @JoinColumn( name = "routeId")
    @JsonIgnore
    private Route route;

  /**
    public Bus(String busFahrer, int preis, int geschwindigkeit , Route route ) {
        this.busFahrer = busFahrer;
        this.preis = preis;
        this.geschwindigkeit = geschwindigkeit;
        this.route = route;
    }

   */
    public Bus(String busFahrer, double preis, double geschwindigkeit ) {
        this.busFahrer = busFahrer;
        this.preis = preis;
        this.geschwindigkeit = geschwindigkeit;

    }
    public Bus(){
    }

    public Long getBusId() {
        return busId;
    }

    public String getBusFahrer() {
        return busFahrer;
    }

    public void setBusFahrer(String busFahrer) {
        this.busFahrer = busFahrer;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(double geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }


    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "BusService{" +
                "busId=" + busId +
                ", busFahrer='" + busFahrer + '\'' +
                ", preis=" + preis +
                ", geschwindigkeit=" + geschwindigkeit +
                ", route=" + route +
                '}';
    }
}
