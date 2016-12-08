package com.andy.baesapp.beans;

import java.util.List;

/**
 * Created by andy on 2016/12/8.
 */
public class GetShotsListResult {
    private List<ShotInfo> shotsList;

    public List<ShotInfo> getShotsList() {
        return shotsList;
    }

    public void setShotsList(List<ShotInfo> shotsList) {
        this.shotsList = shotsList;
    }
}
