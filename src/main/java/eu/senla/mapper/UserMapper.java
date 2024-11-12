package eu.senla.mapper;

import eu.senla.domain.User;
import eu.senla.web.dto.request.UserRequest;
import eu.senla.web.dto.response.UserListResponse;
import eu.senla.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    default UserListResponse toListResponse(Collection<User> users) {
        return UserListResponse.builder()
                .users(users.stream()
                        .map(this::toUserResponse)
                        .toList())
                .build();
    }

    User toUser(UserRequest request);

    default User toUser(Long id, UserRequest request) {
        User user = toUser(request);
        user.setId(id);
        return user;
    }
}
