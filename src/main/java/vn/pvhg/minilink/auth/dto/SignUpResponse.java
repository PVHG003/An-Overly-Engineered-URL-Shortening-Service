package vn.pvhg.minilink.auth.dto;

public record SignUpResponse(
        String email,
        String role,
        boolean enabled,
        String message
) {
}
