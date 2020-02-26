package com.ra.course.ams.airline.manag.system.entity;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat;
import com.ra.course.ams.airline.manag.system.entity.notification.Notification;
import com.ra.course.ams.airline.manag.system.entity.payment.Payment;
import com.ra.course.ams.airline.manag.system.entity.person.Pilot;

import java.util.HashMap;
import java.util.List;

public class FlightReservation {
    private String reservationNumber;
    private FlightInstance flight;
    private HashMap <Passenger, FlightSeat> seatMap;
    private ReservationStatus status;
    private List<Notification> notifications;
    private Payment payment;
    private List<Passenger> passengers;


    public FlightReservation(String reservationNumber, FlightInstance flight, HashMap<Passenger, FlightSeat> seatMap, ReservationStatus status, List<Notification> notifications, Payment payment) {
        this.reservationNumber = reservationNumber;
        this.flight = flight;
        this.seatMap = seatMap;
        this.status = status;
        this.notifications = notifications;
        this.payment = payment;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public FlightInstance getFlight() {
        return flight;
    }

    public void setFlight(FlightInstance flight) {
        this.flight = flight;
    }

    public HashMap<Passenger, FlightSeat> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(HashMap<Passenger, FlightSeat> seatMap) {
        this.seatMap = seatMap;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
