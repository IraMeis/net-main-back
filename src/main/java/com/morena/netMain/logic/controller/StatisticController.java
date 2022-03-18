package com.morena.netMain.logic.controller;

import com.morena.netMain.logic.model.dao.PStatistic;
import com.morena.netMain.logic.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistic")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    /**
     * /api/statistic/getStatistic/{id}
     * @param id
     * @return
     */
    @GetMapping("/getStatistic/{id}")
    public ResponseEntity<PStatistic> getStatistic(@PathVariable Long id){
        PStatistic statistic = statisticService.getUserStatistic(id);
        return statistic != null ?
                ResponseEntity.ok(statistic) :
                ResponseEntity.notFound().build();
    }
}
