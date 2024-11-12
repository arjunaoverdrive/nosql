package eu.senla.service.impl;

import eu.senla.dao.UserRepository;
import eu.senla.domain.User;
import eu.senla.exception.NotFoundException;
import eu.senla.service.UserService;
import eu.senla.utils.BeanUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;


    @Override
    public List<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public List<User> findAllByIds(Collection<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("User with ID {0} not found", id)
                )
        );
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User fromDb = findById(user.getId());
        BeanUtils.copyNonNullValues(user, fromDb);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User toDelete = findById(id);
        userRepository.delete(toDelete);
    }
}
