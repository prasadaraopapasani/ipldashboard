package io.prasad.ipldashboard.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.prasad.ipldashboard.match.service.MatchService;
import io.prasad.ipldashboard.model.TeamResponseVO;
import io.prasad.ipldashboard.team.services.TeamService;


@RestController
@RequestMapping("/api/v1")
public class TeamController {
    private TeamService  teamService;
    private MatchService matchService;

    @Autowired
    public TeamController(TeamService teamService,MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
        
    }
 
    @GetMapping("/teams/{teamName}")
    public TeamResponseVO getTeamByName(@PathVariable(name = "teamName") String teamName){
        TeamResponseVO teamResponseVO=teamService.getTeamByName(teamName);
        if(!ObjectUtils.isEmpty(teamResponseVO) && !ObjectUtils.isEmpty(teamResponseVO.getTeam())){
            teamResponseVO.getTeam().setMatches(matchService.getLatesgMatchesByTeamName(teamName,4));
        }
        return teamResponseVO;
    }


}
