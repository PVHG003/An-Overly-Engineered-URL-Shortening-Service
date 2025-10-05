package vn.pvhg.minilink.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping("/anonymous")
    public String anonymousAccess() {
        return "Accessible by anyone (no authentication required)";
    }

    @GetMapping("/user")
    public String userAccess() {
        return "Accessible by authenticated users with ROLE_USER";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Accessible by authenticated users with ROLE_ADMIN";
    }
}
