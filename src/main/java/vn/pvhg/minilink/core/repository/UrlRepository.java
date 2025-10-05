package vn.pvhg.minilink.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import vn.pvhg.minilink.core.model.Url;

import java.util.UUID;

public interface UrlRepository extends JpaRepository<Url, UUID> {
}