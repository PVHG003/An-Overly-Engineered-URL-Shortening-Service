package vn.pvhg.minilink.auth.service;

import jakarta.validation.Valid;
import vn.pvhg.minilink.auth.dto.*;

public interface AuthService {
    SignUpResponse signUp(SignUpRequest request);

    SignInResponse signIn(SignInRequest request);

    TokenRefreshResponse refreshToken(@Valid TokenRefreshRequest request);

    void logout(@Valid LogoutRequest request);

    void sendVerificationCode(VerificationCodeRequest request);

    void verify(VerificationCodeRequest request);
}
