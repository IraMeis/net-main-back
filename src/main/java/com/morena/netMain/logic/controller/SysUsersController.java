package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PSysUsers;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class SysUsersController {

    @GetMapping("/getMyInfo")
    public ResponseEntity<PSysUsers> getMyInfo () {
        return null;
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<PSysUsers> getUser(@PathVariable Long id){
        return null;
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody PSysUsers user){
        return null;
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody PSysUsers user){
        return null;
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody PSysUsers user){
        return null;
    }

    @GetMapping("/getStatistic/{id}")
    public ResponseEntity<PSysUsers> getStatistic(@PathVariable Long id){
        return null;
    }

    @GetMapping("/getFilteredUsers")
    public ResponseEntity<List<PSysUsers>> getFilteredUsers(
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate to,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Long scope,
            @RequestParam(required = false) Boolean inDeleted,
            @RequestParam(required = false) Boolean inActive
            ){
        return null;
    }

}
