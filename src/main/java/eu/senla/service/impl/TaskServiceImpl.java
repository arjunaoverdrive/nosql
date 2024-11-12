package eu.senla.service.impl;
import eu.senla.config.properties.AppCacheProperties;
import eu.senla.dao.TaskRepository;
import eu.senla.domain.Task;
import eu.senla.domain.User;
import eu.senla.exception.NotFoundException;
import eu.senla.service.TaskService;
import eu.senla.service.UserService;
import eu.senla.utils.BeanUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CacheConfig(cacheManager = "redisCacheManager")
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    UserService userService;


    @Override
    public List<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable).getContent();
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        MessageFormat.format("Task with ID {0} not found", id)
                )
        );
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        Task toDelete = findById(id);
        taskRepository.delete(toDelete);
    }

    @Override
    public Task updateTask(Task task) {
        Task fromDb = findById(task.getId());
        BeanUtils.copyNonNullValues(task, fromDb);
        return taskRepository.save(fromDb);
    }

    @Override
    @Transactional
    public Task addObservers(Long id, List<Long> observerIds) {
        List<User> allByIds = userService.findAllByIds(observerIds);
        Task task = findById(id);
        allByIds.forEach(u -> u.addObservedTasks(task));
        return taskRepository.save(task);
    }
}
