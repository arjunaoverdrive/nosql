package eu.senla.mapper;

import eu.senla.domain.Task;
import eu.senla.domain.User;
import eu.senla.service.UserService;
import eu.senla.web.dto.request.AddObserversRequest;
import eu.senla.web.dto.request.TaskSubmitRequest;
import eu.senla.web.dto.request.TaskUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TaskMapperDelegate implements TaskMapper {

    @Autowired
    private UserService userService;

    @Override
    public Task toTask(TaskSubmitRequest request) {
        User author = userService.findById(request.getAuthorId());
        User assignee = request.getAssigneeId() == null ? null : userService.findById(request.getAssigneeId());
        Set<User> observers = request.getObserverIds() == null ? new HashSet<>() :
                new HashSet<>(userService.findAllByIds(request.getObserverIds()));
        return Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .author(author)
                .assignee(assignee)
                .observers(observers)
                .build();
    }

    @Override
    public Task toTask(TaskUpdateRequest request) {
        User assignee = request.getAssigneeId() == null ? null : userService.findById(request.getAssigneeId());
        return Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus())
                .assignee(assignee)
                .build();
    }

    @Override
    public Task toTask(AddObserversRequest request) {
        List<Long> observerIds = request.getObserverIds();
        Set<User> observers = new HashSet<>(userService.findAllByIds(observerIds));
        return Task.builder()
                .observers(observers).build();
    }
}
