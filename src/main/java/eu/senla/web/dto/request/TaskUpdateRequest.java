package eu.senla.web.dto.request;

import eu.senla.domain.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskUpdateRequest {

    @Size(min = 3, max = 32)
    String name;
    @Size(max = 1204)
    String description;
    TaskStatus status;
    Long assigneeId;
}
