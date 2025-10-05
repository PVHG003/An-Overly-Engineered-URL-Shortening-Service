package vn.pvhg.minilink.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.pvhg.minilink.auth.model.RefreshToken;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}