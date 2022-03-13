package com.morena.netMain.auth.controller;

import com.morena.netMain.auth.model.RegisterUser;
import com.morena.netMain.auth.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    /**
     * /api/register
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<String> register (@RequestBody RegisterUser user){
        return registerService.saveUser(user) ?
                ResponseEntity.status(HttpStatus.CREATED).body("Registered") :
                ResponseEntity.badRequest().body("This login already exists");
    }
}
