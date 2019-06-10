package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.dao.AuditoriumDao;

import java.util.Set;

@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {

    private final Set<Auditorium> auditoriumGroup ;

    @Autowired
    public AuditoriumDaoImpl(Set<Auditorium> auditoriumGroup) {
        this.auditoriumGroup = auditoriumGroup;
    }


    @Override
    public Set<Auditorium> getAll() {
        return auditoriumGroup;
    }
}
