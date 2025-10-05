package vn.pvhg.minilink.auth.dto;

import java.util.UUID;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        UUID userId,
        String email,
        boolean isVerified
) {
}
