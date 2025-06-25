package org.Scsp.com.service;

import org.Scsp.com.dto.UserRankingDTO;
import org.Scsp.com.model.User;

import java.util.List;

public interface RankingService {

    public List<UserRankingDTO> getUserRanking();
    public List<UserRankingDTO> getUserRankingMonney();
    public List<UserRankingDTO> getUserRankingMission();
}
