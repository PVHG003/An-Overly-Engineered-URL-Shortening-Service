package vn.pvhg.minilink.auth.dto;

import java.util.UUID;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn,
        UUID userId,
        String email,
        boolean isVerified
) {
}
