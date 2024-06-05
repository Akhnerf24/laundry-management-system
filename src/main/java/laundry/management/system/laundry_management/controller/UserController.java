package laundry.management.system.laundry_management.controller;

import io.swagger.v3.oas.annotations.Operation;
import laundry.management.system.laundry_management.entity.User;
import laundry.management.system.laundry_management.model.RegisterUserRequest;
import laundry.management.system.laundry_management.model.UpdateUserRequest;
import laundry.management.system.laundry_management.model.UserResponse;
import laundry.management.system.laundry_management.model.WebResponse;
import laundry.management.system.laundry_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @Operation(summary = "Register a new user")
    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("Berhasil Menambahkan Akun Admin").build();
    }
    
    
    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user) {
        UserResponse userResponse = userService.get(user);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
    
    @PatchMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<UserResponse> update(User user, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.update(user, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
}
