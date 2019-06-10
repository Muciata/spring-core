package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@Service("AuditoriumService")
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDao auditoriumDao;

    @Autowired
    public AuditoriumServiceImpl(AuditoriumDao auditoriumDao) {
        this.auditoriumDao = auditoriumDao;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDao.getAll().stream()
                .filter(aud -> aud.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
