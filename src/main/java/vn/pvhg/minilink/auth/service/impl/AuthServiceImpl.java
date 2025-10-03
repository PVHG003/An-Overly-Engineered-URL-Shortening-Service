package vn.pvhg.minilink.auth.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.pvhg.minilink.auth.dto.*;
import vn.pvhg.minilink.auth.model.Provider;
import vn.pvhg.minilink.auth.model.Role;
import vn.pvhg.minilink.auth.model.RoleName;
import vn.pvhg.minilink.auth.model.User;
import vn.pvhg.minilink.auth.repository.RoleRepository;
import vn.pvhg.minilink.auth.repository.UserRepository;
import vn.pvhg.minilink.auth.service.AuthService;
import vn.pvhg.minilink.exception.auth.InvalidCredentailsException;
import vn.pvhg.minilink.exception.auth.UserAlreadyExistsException;
import vn.pvhg.minilink.exception.auth.UserNotEnabledException;
import vn.pvhg.minilink.util.JwtUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    //    private static final int VERIFICATION_CODE_LENGTH = 6;
    //    private static final int VERIFICATION_CODE_EXPIRY_MINUTES = 15;
    //    private static final int REFRESH_TOKEN_EXPIRY_DAYS = 30;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        log.info("Sign up attempt for email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        Role role = roleRepository.findByRoleName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .enabled(true)
                .provider(Provider.LOCAL)
                .providerId(null)
                .role(role)
                .build();

        user = userRepository.save(user);

        log.info("User registered successfully: {}", user.getEmail());

        return new SignUpResponse(
                user.getEmail(),
                user.getRole().getRoleName().toString(),
                user.isEnabled(),
                "User registered successfully. Please verify your email address."
        );
    }

    @Override
    @Transactional
    public SignInResponse signIn(SignInRequest request) {
        log.info("Sign in attempt for: {}", request.email());

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentailsException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new UserNotEnabledException("User is not enabled");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        log.info("User signed in successfully: {}", user.getEmail());

        return new SignInResponse(
                accessToken,
                refreshToken,
                "Bearer",
                jwtUtil.getAccessTokenExpiry(),
                user.getUserId(),
                user.getEmail(),
                user.isEnabled()
        );
    }

    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        return null;
    }

    @Override
    public void logout(LogoutRequest request) {

    }

    @Override
    public void sendVerificationCode(VerificationCodeRequest request) {

    }

    @Override
    public void verify(VerificationCodeRequest request) {

    }
}
