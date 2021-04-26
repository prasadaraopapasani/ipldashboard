package io.prasad.ipldashboard.team.services;

import org.springframework.stereotype.Service;
import io.prasad.ipldashboard.model.TeamResponseVO;
import io.prasad.ipldashboard.team.repository.TeamRepository;

@Service
public class TeamServiceImpl  implements TeamService {

    private TeamRepository  teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


    @Override
    public TeamResponseVO getTeamByName(String teamName) {
        return TeamResponseVO.builder().team(teamRepository.findByTeamName(teamName.toLowerCase())).build();
    }
}
