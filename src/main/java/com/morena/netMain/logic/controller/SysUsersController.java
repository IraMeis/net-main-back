package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.pojo.PSysUsers;
import com.morena.netMain.logic.service.SysUsersService;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import com.morena.netMain.logic.utils.Pair;
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

    private final SysUsersService sysUsersService;

    /**
     * /api/user/getMyInfo
     * @return
     */
    @GetMapping("/getMyInfo")
    public ResponseEntity<PSysUsers> getMyInfo () {
        PSysUsers pSysUsers = sysUsersService.getCurrentMapped();
        return pSysUsers == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(pSysUsers);
    }

    /**
     * /api/user/getUser/{id}
     * @param id
     * @return
     */
    @GetMapping("/getUser/{id}")
    public ResponseEntity<PSysUsers> getUser(@PathVariable Long id){
        PSysUsers pSysUsers = sysUsersService.getByIdMapped(id);
        return pSysUsers == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok(pSysUsers);
    }

    /**
     * /api/user/updateAdditional
     * @param user
     * @return
     */
    @PutMapping("/updateAdditional")
    public ResponseEntity<String> updateAdditionalUserData(@RequestBody Pair user){
        return sysUsersService.updateAdditionalData(user) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.notFound().build();
    }

    /**
     * /api/user/deleteUser/{id}
     * @param id
     * @return
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        sysUsersService.deleteUser(id);
        return ResponseEntity.ok("Deleted");
    }

    /**
     * /api/user/banUser/{id}
     * @param id
     * @return
     */
    @PutMapping("/banUser/{id}")
    public ResponseEntity<String> banUser(@PathVariable Long id){
        sysUsersService.banUser(id);
        return ResponseEntity.ok("Banned");
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
