package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.model.dao.PSysUsers;
import com.morena.netMain.logic.model.filter.UserFilterRequest;
import com.morena.netMain.logic.service.SysUsersService;
import com.morena.netMain.logic.utils.LocalDateConvertor;
import com.morena.netMain.logic.utils.Pair;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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
     * /api/user/updateByAdmin
     * @return
     */
    @PutMapping("/updateByAdmin")
    public ResponseEntity<String> updateByAdmin(@RequestBody PSysUsers user){
        return sysUsersService.updateByAdmin(user) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.notFound().build();
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

    /**
     * /api/user/setScope/{id}/{scopeCode}
     * @param id
     * @param scopeCode
     * @return
     */
    @PutMapping("/setScope/{id}/{scopeCode}")
    public ResponseEntity<String> setScope(@PathVariable Long id, @PathVariable Long scopeCode){
        return sysUsersService.changeScope(id, scopeCode) ?
                ResponseEntity.ok("Updated") :
                ResponseEntity.badRequest().build();
    }

    /**
     * /api/user/getFilteredUsers
     * @param from
     * @param to
     * @param label
     * @param scopes
     * @param isDeleted
     * @param isActive
     * @param isUser
     * @return
     */
    @GetMapping("/getFilteredUsers")//todo @QuerydslPredicate or filterRequest (+ postMapping) as params
    public ResponseEntity<Collection<PSysUsers>> getFilteredUsers(
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(pattern = LocalDateConvertor.dateFormat) LocalDate to,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) Long[] scopes,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Boolean isUser){

        UserFilterRequest request = UserFilterRequest.builder()
                .label(label)
                .from(from)
                .to(to)
                .scopes(scopes == null ? null : List.of(scopes))
                .isUser(isUser)
                .isDeleted(isDeleted)
                .isActive(isActive)
                .build();

        return ResponseEntity.ok(request.isNullFilter() ?
                sysUsersService.getAll() :
                sysUsersService.getFilteredUsers(request));
    }

}
