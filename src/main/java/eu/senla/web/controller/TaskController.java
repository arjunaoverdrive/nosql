package eu.senla.web.controller;

import eu.senla.mapper.TaskMapper;
import eu.senla.service.TaskService;
import eu.senla.web.dto.request.AddObserversRequest;
import eu.senla.web.dto.request.TaskSubmitRequest;
import eu.senla.web.dto.request.TaskUpdateRequest;
import eu.senla.web.dto.response.TaskListResponse;
import eu.senla.web.dto.response.TaskResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskController {

    TaskService taskService;
    TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<TaskListResponse> getAllTasks(
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        TaskListResponse tasks = taskMapper.toListResponse(taskService.findAll(pageable));
        return ResponseEntity.ok().body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse taskResponse = taskMapper.toTaskResponse(taskService.findById(id));
        return ResponseEntity.ok().body(taskResponse);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskSubmitRequest request) {
        TaskResponse taskResponse = taskMapper.toTaskResponse(taskService.save(taskMapper.toTask(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTaskById(@Valid @RequestBody TaskUpdateRequest request) {
        TaskResponse taskResponse = taskMapper.toTaskResponse(taskService.updateTask(taskMapper.toTask(request)));
        return ResponseEntity.ok().body(taskResponse);
    }

    @PatchMapping("/{id}/observers")
    public ResponseEntity<TaskResponse> addObserversToTask(@PathVariable Long id, @Valid @RequestBody AddObserversRequest request) {
        TaskResponse response = taskMapper.toTaskResponse(taskService.addObservers(id, request.getObserverIds()));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
