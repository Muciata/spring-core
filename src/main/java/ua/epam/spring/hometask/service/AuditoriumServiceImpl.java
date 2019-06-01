package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class AuditoriumServiceImpl implements AuditoriumService {

    private final Set<Auditorium> auditoriumGroup;

    public AuditoriumServiceImpl(Set<Auditorium> auditoriumGroup) {
        this.auditoriumGroup = auditoriumGroup;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumGroup;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumGroup.stream()
                .filter(aud -> aud.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
