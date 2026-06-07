package com.re.backendmobile.controller;

import com.re.backendmobile.dto.ApiResponse;
import com.re.backendmobile.entity.User;
import com.re.backendmobile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody User request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ApiResponse<>(false, "Email đã tồn tại", null, null);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("USER");

        User saved = userRepository.save(user);

        return new ApiResponse<>(
                true,
                "Đăng ký thành công",
                "demo-token-" + saved.getId(),
                saved
        );
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody User user) {

        User found = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));

        if (!found.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        return new ApiResponse<>(
                true,
                "Đăng nhập thành công",
                "demo-token-" + found.getId(),
                found
        );
    }

    @GetMapping("/users")
    public java.util.List<User> getUsers() {
        return userRepository.findAll();
    }
}