package com.humanbooster.authentification.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotNull(message = "Veuillez choisir la ligne")
    private Integer lane;

    @Column(name = "_column")
    private Integer column;
    @Column(name = "reservation_date")
    private Date date_to;

    @Column(nullable = false)
    @NotNull(message = "Veuillez indiquer si la commande est acceptée")
    private boolean isAccepted = false;

    @Column(nullable = false)
    @NotNull(message = "Veuillez choisir une option")
    private String equipment;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Command command;

    public Reservation(int id, @NotNull(message = "Veuillez choisir la ligne") Integer lane, Integer column, Date date_to, @NotNull(message = "Veuillez indiquer si la commande est acceptée") boolean isAccepted, @NotNull(message = "Veuillez choisir une option") String equipment, Command command) {
        this.id = id;
        this.lane = lane;
        this.column = column;
        this.date_to = date_to;
        this.isAccepted = isAccepted;
        this.equipment = equipment;
        this.command = command;
    }

    public Reservation(@NotNull(message = "Veuillez choisir la ligne") Integer lane, Integer column, Date date_to, @NotNull(message = "Veuillez indiquer si la commande est acceptée") boolean isAccepted, @NotNull(message = "Veuillez choisir une option") String equipment, Command command) {
        this.lane = lane;
        this.column = column;
        this.date_to = date_to;
        this.isAccepted = isAccepted;
        this.equipment = equipment;
        this.command = command;
    }

    public Reservation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
