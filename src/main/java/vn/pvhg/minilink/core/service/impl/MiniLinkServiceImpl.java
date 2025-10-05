package vn.pvhg.minilink.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import vn.pvhg.minilink.auth.model.User;
import vn.pvhg.minilink.auth.repository.UserRepository;
import vn.pvhg.minilink.core.dto.MiniLinkRequest;
import vn.pvhg.minilink.core.dto.MiniLinkResponse;
import vn.pvhg.minilink.core.model.Url;
import vn.pvhg.minilink.core.repository.UrlRepository;
import vn.pvhg.minilink.core.service.MiniLinkService;
import vn.pvhg.minilink.util.UrlGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MiniLinkServiceImpl implements MiniLinkService {
    private final UserRepository userRepository;
    private final UrlGenerator urlGenerator;
    private final UrlRepository urlRepository;

    @Override
    public MiniLinkResponse createUrl(Jwt principal, MiniLinkRequest request) {
        UUID userId = UUID.fromString(principal.getClaim("uuid"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Url url = Url.builder()
                .originalUrl(request.originalUrl())
                .shortenUrl(urlGenerator.generateUrl(request.originalUrl()))
                .owner(user)
                .expiresAt(Instant.now().plusMillis(request.expireIn()))
                .build();
        url = urlRepository.save(url);

        return new MiniLinkResponse(
                url.getShortenUrl(),
                url.getOriginalUrl(),
                LocalDateTime.ofInstant(url.getCreatedDate(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(url.getExpiresAt(), ZoneId.systemDefault())
        );
    }
}
