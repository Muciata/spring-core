package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import java.util.Set;

public interface AuditoriumDao {
    Set<Auditorium> getAll();
}
