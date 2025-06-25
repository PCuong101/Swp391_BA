package org.Scsp.com.controller;

import org.Scsp.com.dto.UserRankingDTO;
import org.Scsp.com.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<UserRankingDTO>> getRankings() {
        List<UserRankingDTO> rankings = rankingService.getUserRanking();
        return ResponseEntity.ok(rankings);
    }
    @GetMapping("/Rankingmoney")
    public ResponseEntity<List<UserRankingDTO>> getRankingsMoney() {
        List<UserRankingDTO> rankings = rankingService.getUserRankingMonney();
        return ResponseEntity.ok(rankings);
    }
    @GetMapping("/RankingsMission")
    public ResponseEntity<List<UserRankingDTO>> getRankingsMission() {
        List<UserRankingDTO> rankings = rankingService.getUserRankingMission();
        return ResponseEntity.ok(rankings);
    }
}
