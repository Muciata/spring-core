package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

public interface AuditoriumDao {
    Set<Auditorium> getAll();
}
