package vn.pvhg.minilink.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import vn.pvhg.minilink.common.ApiResponse;
import vn.pvhg.minilink.core.dto.MiniLinkRequest;
import vn.pvhg.minilink.core.dto.MiniLinkResponse;
import vn.pvhg.minilink.core.service.MiniLinkService;

@Slf4j
@RestController
@RequestMapping("/api/v1/mini-link")
@RequiredArgsConstructor
public class MiniLinkController {

    private final MiniLinkService miniLinkService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ApiResponse<MiniLinkResponse>> createUrl(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody MiniLinkRequest request
    ) {
        MiniLinkResponse response = miniLinkService.createUrl(principal, request);
        ApiResponse<MiniLinkResponse> apiResponse = ApiResponse.<MiniLinkResponse>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("New URL generated successfully")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}
