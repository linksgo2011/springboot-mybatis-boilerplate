package boilerplate.controller;

import boilerplate.controller.dto.TokenRequest;
import boilerplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TokenController {

    @Autowired
    private UserService userService;

    @PostMapping("/token")
    public String getToken(@RequestBody @Valid TokenRequest tokenRequest) {

        String token = userService.login(tokenRequest.getUsername(), tokenRequest.getPassword());
        if (StringUtils.isEmpty(token)) {
            return "no token found";
        }
        return token;
    }
}
