package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.InformationService;

import java.sql.Time;
import java.util.*;

public class InformationServiceImpl implements InformationService {

    private Repository<WeeklySchedule, String> weeklyScheduleRepo;
    private Repository<CustomSchedule, String> customScheduleRepo;
    private Repository<FlightInstance, String> flightInstRepo;
    private Repository<Flight, String> flightRepository;

    final private Collection<FlightInstance> flightInstances = flightInstRepo.getInstances();

    @Override
    public WeeklySchedule checkFlightWeeklySchedule(String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) {
            throw new IllegalArgumentException("FlightNumber for search cannot be null, empty or blank");
        }
        final WeeklySchedule fidedWeeklySched = weeklyScheduleRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElse((WeeklySchedule) Collections.EMPTY_LIST);

        return fidedWeeklySched;
    }

    @Override
    public CustomSchedule checkFlightCustomSchedule(String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) {
            throw new IllegalArgumentException("FlightNumber for search cannot be null, empty or blank");
        }
        final CustomSchedule findedCustomSched = customScheduleRepo.getInstances().stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElse((CustomSchedule) Collections.EMPTY_LIST);

        return findedCustomSched;
    }

    @Override
    public List<FlightSeat> checkAvailableSeats(FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getSeats();
    }

    @Override
    public Time checkDepartureTime(FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getDepartureTime();
    }

    @Override
    public Time checkArrivalTime(FlightInstance flightInstance) {

        return getNecessaryFlightInstanse(flightInstance).getArrivalTime();
    }

    @Override
    public List<Flight> searchFlightByDate(Date date) {
        if (Optional.ofNullable(date).isPresent()) {
            final List<Flight> findedFlight = (List<Flight>) flightRepository.getInstances().stream()
                    .filter(i -> (date).equals(i.getDate()))
                    .findAny()
                    .orElse((Flight) Collections.EMPTY_LIST);
            return findedFlight;
        }
        throw new IllegalArgumentException("Date cannot be null, empty or blank");
    }

    @Override
    public List<Flight> searchFlightByDepartureAirport(Airport airport) {

        if (Optional.ofNullable(airport).isPresent()) {
            final List<Flight> findedFlight = (List<Flight>) flightRepository.getInstances().stream()
                    .filter(i -> (airport).equals(i.getArrival()))
                    .findAny()
                    .orElse((Flight) Collections.EMPTY_LIST);
            return findedFlight;
        }
        throw new IllegalArgumentException("Airport cannot be null, empty or blank");
    }

    @Override
    public List<Flight> searchFlightByArrivalAirport(Airport airport) {
        if (Optional.ofNullable(airport).isPresent()) {
            final List<Flight> findedFlight = (List<Flight>) flightRepository.getInstances().stream()
                    .filter(i -> (airport).equals(i.getDeparture()))
                    .findAny()
                    .orElse((Flight) Collections.EMPTY_LIST);
            return findedFlight;
        }
        throw new IllegalArgumentException("Airport cannot be null, empty or blank");
    }

    private FlightInstance getNecessaryFlightInstanse(FlightInstance flightInstance) {
        if (Optional.ofNullable(flightInstance).isPresent()) {
            final FlightInstance findedFlightInst = flightInstances.stream()
                    .filter(i -> (flightInstance).equals(i))
                    .findAny()
                    .orElse((FlightInstance) Collections.EMPTY_LIST);
            return findedFlightInst;
        }
        throw new IllegalArgumentException("FlightInstance cannot be null, empty or blank");
    }
}
