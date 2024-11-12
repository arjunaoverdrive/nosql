package eu.senla.web.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TaskResponse {

    String id;
    String name;
    String description;
    Instant createdAt;
    Instant updatedAt;
    String status;
    UserResponse author;
    UserResponse assignee;
    @Builder.Default
    Set<UserResponse> observers = new HashSet<>();
}