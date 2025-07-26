package org.Scsp.com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRankingDTO {
    private String username;
    private long quitDays;
    private double moneySaved;
    private int taskCompleted;
    private String avatarUrl;

    public UserRankingDTO() {
    }
    public UserRankingDTO(String username, long quitDays, double moneySaved, int taskCompleted,String avatarUrl) {
        this.username = username;
        this.quitDays = quitDays;
        this.moneySaved = moneySaved;
        this.taskCompleted = taskCompleted;
        this.avatarUrl = avatarUrl;
    }
}
