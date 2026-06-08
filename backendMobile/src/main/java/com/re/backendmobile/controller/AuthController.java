package com.re.backendmobile.controller;

import com.re.backendmobile.dto.ApiResponse;
import com.re.backendmobile.entity.User;
import com.re.backendmobile.repository.UserRepository;
import jakarta.validation.Valid;
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
    public ApiResponse<User> register(@Valid @RequestBody User request) {

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
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ApiResponse<>(false, "Vui lòng nhập email", null, null);
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ApiResponse<>(false, "Vui lòng nhập mật khẩu", null, null);
        }

        User found = userRepository.findByEmail(user.getEmail())
                .orElse(null);

        if (found == null) {
            return new ApiResponse<>(false, "Email không tồn tại", null, null);
        }

        if (!found.getPassword().equals(user.getPassword())) {
            return new ApiResponse<>(false, "Sai mật khẩu", null, null);
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