package com.morena.netMain.logic.controller;

import com.morena.netMain.auth.jwt.JwtAuthentication;
import com.morena.netMain.logic.service.AuthService;
import com.morena.netMain.logic.service.SysUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

   /* private final AuthService authService;
    private final SysUsersService sysUsersService;

    @PreAuthorize("hasAuthority('comment_modifier')")
    @GetMapping("/hello/user")
    public ResponseEntity<String> helloUser() {
        final JwtAuthentication authInfo = authService.getAuthInfo();

        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }

    @PreAuthorize("hasAuthority('system')")
    @GetMapping("/hello/admin")
    public ResponseEntity<String> helloAdmin() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
    }

    @GetMapping("/wtf")
    public ResponseEntity<?> wtf() {
        return ResponseEntity.ok(authService.getCurrentUserRoles());
    }

    @GetMapping("/wtfu")
    public ResponseEntity<?> wtfu() {
        return ResponseEntity.ok(sysUsersService.getCurrentUser());
    }*/

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    //@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
   // @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
   // @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

}