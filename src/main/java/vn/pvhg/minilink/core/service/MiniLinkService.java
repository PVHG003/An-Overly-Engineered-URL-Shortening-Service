package vn.pvhg.minilink.core.service;

import org.springframework.security.oauth2.jwt.Jwt;
import vn.pvhg.minilink.core.dto.MiniLinkRequest;
import vn.pvhg.minilink.core.dto.MiniLinkResponse;

public interface MiniLinkService {
    MiniLinkResponse createUrl(Jwt principal, MiniLinkRequest request);
}
