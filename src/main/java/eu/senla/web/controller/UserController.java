package eu.senla.web.controller;

import eu.senla.mapper.UserMapper;
import eu.senla.service.UserService;
import eu.senla.web.dto.request.UserRequest;
import eu.senla.web.dto.response.UserListResponse;
import eu.senla.web.dto.response.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers(Pageable pageable) {
        UserListResponse users = userMapper.toListResponse(userService.findAllUsers(pageable));
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userMapper.toUserResponse(userService.findById(id));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createuser(@RequestBody UserRequest request) {
        UserResponse user = userMapper.toUserResponse(userService.createUser(userMapper.toUser(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        UserResponse user = userMapper.toUserResponse(userService.updateUser(userMapper.toUser(id, request)));
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}