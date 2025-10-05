package vn.pvhg.minilink.core.dto;

public record MiniLinkRequest(
        String originalUrl,
        long expireIn
) {
}
