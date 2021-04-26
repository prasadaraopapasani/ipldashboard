package io.prasad.ipldashboard.team.services;

import io.prasad.ipldashboard.model.TeamResponseVO;


public interface TeamService {
    public TeamResponseVO getTeamByName(String teamName);
    
}
