package eu.senla.service.impl;

import eu.senla.config.properties.AppCacheProperties;
import eu.senla.dao.UserRepository;
import eu.senla.domain.User;
import eu.senla.exception.NotFoundException;
import eu.senla.service.UserService;
import eu.senla.utils.BeanUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CacheConfig(cacheManager = "redisCacheManager")
public class UserServiceImpl implements UserService {

    UserRepository userRepository;


    @Override
    @Cacheable(cacheNames = AppCacheProperties.CacheNames.ALL_USERS,
            key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public List<User> findAllUsers(Pageable pageable) {
        Page<User> all = userRepository.findAll(pageable);
        return all.getContent();
    }

    @Override
    public List<User> findAllByIds(Collection<Long> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    @Cacheable(cacheNames = AppCacheProperties.CacheNames.USER_BY_ID, key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("User with ID {0} not found", id)
                )
        );
    }

    @Override
    @CacheEvict(
            cacheNames = AppCacheProperties.CacheNames.ALL_USERS, allEntries = true
    )
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.ALL_USERS, allEntries = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.USER_BY_ID, key = "#user.id", beforeInvocation = true)
    })
    public User updateUser(User user) {
        User fromDb = findById(user.getId());
        BeanUtils.copyNonNullValues(user, fromDb);
        return userRepository.save(user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.ALL_USERS, allEntries = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.USER_BY_ID, key = "#id")
    })
    public void deleteUserById(Long id) {
        User toDelete = findById(id);
        userRepository.delete(toDelete);
    }
}
