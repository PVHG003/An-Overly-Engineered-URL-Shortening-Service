package vn.pvhg.minilink.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import vn.pvhg.minilink.auth.model.Role;
import vn.pvhg.minilink.auth.model.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}