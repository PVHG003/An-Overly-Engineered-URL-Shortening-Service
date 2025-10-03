package vn.pvhg.minilink.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.pvhg.minilink.auth.model.Role;
import vn.pvhg.minilink.auth.model.RoleName;
import vn.pvhg.minilink.auth.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<RoleName> defaultRoles = Arrays.asList(
                RoleName.ROLE_ANONYMOUS,
                RoleName.ROLE_USER,
                RoleName.ROLE_ADMIN
        );

        for (RoleName roleName : defaultRoles) {
            roleRepository.findByRoleName(roleName)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName(roleName);
                        return roleRepository.save(role);
                    });
        }

        System.out.println("✅ Default roles ensured in database.");
    }
}
