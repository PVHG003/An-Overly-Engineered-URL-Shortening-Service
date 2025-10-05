package vn.pvhg.minilink.core.dto;

import java.time.LocalDateTime;

public record MiniLinkResponse(
        String shortenUrl,
        String originalUrl,
        LocalDateTime createdAt,
        LocalDateTime expiredAt
) {
}
