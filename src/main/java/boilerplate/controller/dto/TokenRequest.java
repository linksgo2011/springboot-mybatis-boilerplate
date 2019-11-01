package boilerplate.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
