package eu.senla.mapper;

import eu.senla.domain.Task;
import eu.senla.web.dto.request.AddObserversRequest;
import eu.senla.web.dto.request.TaskSubmitRequest;
import eu.senla.web.dto.request.TaskUpdateRequest;
import eu.senla.web.dto.response.TaskListResponse;
import eu.senla.web.dto.response.TaskResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(TaskMapperDelegate.class)
public interface TaskMapper {

    TaskResponse toTaskResponse(Task task);

    default TaskListResponse toListResponse(List<Task> tasks) {
        return TaskListResponse.builder()
                .tasks(tasks.stream()
                        .map(this::toTaskResponse)
                        .toList())
                .build();
    }

    Task toTask(TaskSubmitRequest request);

    Task toTask(TaskUpdateRequest request);

    default Task toTask(Long id, TaskUpdateRequest request) {
        Task task = toTask(request);
        task.setId(id);
        return task;
    }

    Task toTask(AddObserversRequest request);

    default Task toTask(Long id, AddObserversRequest request) {
        Task task = toTask(request);
        task.setId(id);
        return task;
    }
}