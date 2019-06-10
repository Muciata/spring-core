package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author Yuriy_Tkach
 */
public interface AuditoriumService {

    /**
     * Getting all auditoriums from the system
     *
     * @return set of all auditoriums
     */
    public @Nonnull
    Set<Auditorium> getAll();


    public void save(Auditorium auditorium);

    /**
     * Finding auditorium by name
     *
     * @param name Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable
    Auditorium getByName(@Nonnull String name);

}
