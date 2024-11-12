package eu.senla.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @NotBlank
    String username;
    @Email
    String email;
    @NotBlank
    @Size(min = 6, max = 12)
    String password;
}
