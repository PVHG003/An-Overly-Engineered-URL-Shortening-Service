package vn.pvhg.minilink.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.pvhg.minilink.auth.dto.*;
import vn.pvhg.minilink.auth.service.AuthService;
import vn.pvhg.minilink.common.ApiResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        SignUpResponse response = authService.signUp(request);
        ApiResponse<SignUpResponse> apiResponse = ApiResponse.<SignUpResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("User registered successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(
            @Valid @RequestBody SignInRequest request) {
        SignInResponse response = authService.signIn(request);
        ApiResponse<SignInResponse> apiResponse = ApiResponse.<SignInResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Login successful")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refresh(
            @Valid @RequestBody TokenRefreshRequest request) {
        TokenRefreshResponse response = authService.refreshToken(request);
        ApiResponse<TokenRefreshResponse> apiResponse = ApiResponse.<TokenRefreshResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Token refreshed successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request) {
        authService.logout(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Logged out successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendCode(@RequestBody VerificationCodeRequest request) {
        authService.sendVerificationCode(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Verification code sent successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verify(@RequestBody VerificationCodeRequest request) {
        authService.verify(request);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(true)
                .status(HttpStatus.OK)
                .message("Verification successful")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
