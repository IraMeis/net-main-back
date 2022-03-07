package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PStatistic;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portal")
@AllArgsConstructor
public class PortalController {

    @GetMapping("/getStatistic")
    public ResponseEntity<PStatistic> getStatistic(){
        return null;
    }
}
