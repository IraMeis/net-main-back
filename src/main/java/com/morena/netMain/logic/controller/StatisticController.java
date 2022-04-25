package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    /**
     * /api/statistic/getUserStatistic/{id}
     * @param id
     * @return
     */
    @GetMapping("/getUserStatistic/{id}")
    public ResponseEntity<PStatistic> getUserStatistic(@PathVariable Long id){
        PStatistic statistic = statisticService.getUserStatistic(id);
        return statistic != null ?
                ResponseEntity.ok(statistic) :
                ResponseEntity.notFound().build();
    }
}
