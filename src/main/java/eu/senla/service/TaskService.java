package eu.senla.service;

import eu.senla.domain.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    List<Task> findAll(Pageable pageable);

    Task findById(Long id);

    Task save(Task task);

    void deleteById(Long id);

    Task updateTask(Task task);

    Task addObservers(Long id, List<Long> observerIds);
}
