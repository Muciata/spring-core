package ua.epam.spring.hometask.service;

import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final Map<Long, User> users = new HashMap<>();

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        Optional<User> foundUser = users.values().stream()
                .filter(user -> Objects.nonNull(user.getEmail()))
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return foundUser.orElse(null);
    }

    @Override
    public User save(@Nonnull User user) {
        Long id = user.getId();
        if (id != null) {
            users.put(id, user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void remove(@Nonnull User object) {
        if (object.getId() != null || users.containsKey(object.getId())) {
            users.remove(object.getId());
        }
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.getOrDefault(id, null);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users.values();
    }
}
