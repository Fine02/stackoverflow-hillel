package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.*;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.InformationService;

import java.sql.Time;
import java.util.*;

public class InformationServiceImpl implements InformationService {

    private Repository<WeeklySchedule, String> weeklyScheduleRepo;
    private Repository<CustomSchedule, String> customScheduleRepo;
    private Repository<FlightInstance, String> flightInstanceStringRepository;


    @Override
    public WeeklySchedule checkFlightWeeklySchedule(String flightNumber) {
        if (flightNumber == null || flightNumber.isBlank()) {
            throw new IllegalArgumentException("FlightNumber for search cannot be null, empty or blank");
        }
        final Collection<WeeklySchedule> weeklySchedules = weeklyScheduleRepo.getInstances();
        final WeeklySchedule fidedWeeklySched = weeklySchedules.stream()
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
        final Collection<CustomSchedule> customSchedules = customScheduleRepo.getInstances();
        final CustomSchedule findedCustomSched = customSchedules.stream()
                .filter(i -> flightNumber.equals(i.getId()))
                .findAny()
                .orElse((CustomSchedule) Collections.EMPTY_LIST);

        return findedCustomSched;
    }

    @Override
    public Time checkDepartureTime(FlightInstance flightInstance) {
        if (Optional.ofNullable(flightInstance).isPresent()) {
            final Collection<FlightInstance> flightInstances = flightInstanceStringRepository.getInstances();
            final FlightInstance findedFlightInst = flightInstances.stream()
                    .filter(i -> (flightInstance).equals(i))
                    .findAny()
                    .orElse((FlightInstance) Collections.EMPTY_LIST);

            return findedFlightInst.getDepartureTime();
        }
        throw new IllegalArgumentException("FlightInstance cannot be null, empty or blank");
    }

    @Override
    public List<FlightSeat> checkAvailableSeats(FlightInstance flightInstance) {
        if (Optional.ofNullable(flightInstance).isPresent()) {
        return null;
    } return null; }

    @Override
    public Time checkArrivalTime(FlightInstance flightInstance) {
        return null;
    }

    @Override
    public Flight searchFlightByDate(Date date) {
        return null;
    }

    @Override
    public Flight searchFlightBySourceAirport(String airportCode) {
        return null;
    }

    @Override
    public Flight searchFlightByDestinationAirport(String airportCode) {
        return null;
    }
}
