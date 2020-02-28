package com.ra.course.ams.airline.manag.system.service.impl;

import com.ra.course.ams.airline.manag.system.entity.flight.CustomSchedule;
import com.ra.course.ams.airline.manag.system.entity.flight.Flight;
import com.ra.course.ams.airline.manag.system.entity.flight.FlightInstance;
import com.ra.course.ams.airline.manag.system.entity.flight.WeeklySchedule;
import com.ra.course.ams.airline.manag.system.repository.Repository;
import com.ra.course.ams.airline.manag.system.service.FlightService;

import java.util.List;

public class FlightServiceImpl implements FlightService {

    private final Repository<Flight, String> flightRepository;

    public FlightServiceImpl(Repository<Flight, String> flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<FlightInstance> getFlightInstances(Flight flight) {
        return flight.getFlightInstances();
    }

    @Override
    public Flight add(Flight flight) {
        return flightRepository.addInstance(flight);
    }

    @Override
    public Flight update(Flight flight) {
        flightRepository.updateInstance(flight);
        return flight;
    }

    @Override
    public boolean cancel(Flight flight) {
        flightRepository.removeInstance(flight);
        return true;
    }

    @Override
    public void addFlightSchedule(Flight flight, WeeklySchedule schedule) {
        Flight updatedFlight = flightRepository.getInstance(flight.getFlightNumber());
        List<WeeklySchedule> weeklySchedules = updatedFlight.getWeeklySchedules();
        weeklySchedules.add(schedule);
        updatedFlight.setWeeklySchedules(weeklySchedules);
        flightRepository.updateInstance(updatedFlight);
    }

    @Override
    public void addFlightSchedule(Flight flight, CustomSchedule schedule) {
        Flight updatedFlight = flightRepository.getInstance(flight.getFlightNumber());
        List<CustomSchedule> customSchedules = updatedFlight.getCustomSchedules();
        customSchedules.add(schedule);
        updatedFlight.setCustomSchedules(customSchedules);
        flightRepository.updateInstance(updatedFlight);
    }

    @Override
    public WeeklySchedule updateSchedule(Flight flight, WeeklySchedule weeklySchedule) {
        Flight updatedFlight = flightRepository.getInstance(flight.getFlightNumber());
        List<WeeklySchedule> weeklySchedules = updatedFlight.getWeeklySchedules();
        weeklySchedules.stream()
                .filter(weeklyScheduleItem -> weeklyScheduleItem.getDayOfWeek() == weeklySchedule.getDayOfWeek()
                        && weeklyScheduleItem.getDepartureTime() == weeklySchedule.getDepartureTime())
                .forEach(weeklyScheduleItemForUpdate -> weeklyScheduleItemForUpdate = weeklySchedule);
        updatedFlight.setWeeklySchedules(weeklySchedules);
        flightRepository.updateInstance(updatedFlight);
        return weeklySchedule;
    }

    @Override
    public CustomSchedule updateSchedule(Flight flight, CustomSchedule customSchedule) {
        Flight updatedFlight = flightRepository.getInstance(flight.getFlightNumber());
        List<CustomSchedule> customSchedules = updatedFlight.getCustomSchedules();
        customSchedules.stream()
                .filter(customScheduleItem -> customScheduleItem.getCustomDate().compareTo(customSchedule.getCustomDate()) == 0
                        && customScheduleItem.getDepartureTime() == customSchedule.getDepartureTime())
                .forEach(weeklyScheduleItemForUpdate -> weeklyScheduleItemForUpdate = customSchedule);
        updatedFlight.setCustomSchedules(customSchedules);
        flightRepository.updateInstance(updatedFlight);
        return customSchedule;
    }

}
