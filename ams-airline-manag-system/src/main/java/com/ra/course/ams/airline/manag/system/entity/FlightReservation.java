package com.ra.course.ams.airline.manag.system.entity;

import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightSeat;
import com.ra.course.ams.airline.manag.system.entity.notification.Notification;
import com.ra.course.ams.airline.manag.system.entity.payment.Payment;

import java.util.HashMap;
import java.util.List;

public class FlightReservation {

    private String reservationNumber;
    private FlightInstance flightInstance;
    private HashMap <Passenger, FlightSeat> seatMap;
    private ReservationStatus status;
    private List<Notification> notifications;
    private Payment payment;
    private List<Passenger> passengers;

    public FlightReservation(ReservationStatus status) {
        this.status = status;
    }

    public FlightReservation(FlightInstance flightInstance, ReservationStatus status) {
        this.flightInstance = flightInstance;
        this.status = status;
    }

    public FlightReservation(String reservationNumber, FlightInstance flightInstance, HashMap<Passenger, FlightSeat> seatMap, ReservationStatus status, List<Notification> notifications, Payment payment) {
        this.reservationNumber = reservationNumber;
        this.flightInstance = flightInstance;
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

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
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

    @Override
    public String toString() {
        return "FlightReservation{" +
                "reservationNumber='" + reservationNumber + '\'' +
                ", flightInstance=" + flightInstance +
                ", seatMap=" + seatMap +
                ", status=" + status +
                ", notifications=" + notifications +
                ", payment=" + payment +
                ", passengers=" + passengers +
                '}';
    }
}
