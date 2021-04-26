package io.prasad.ipldashboard.match.service;

import java.util.List;
import org.springframework.stereotype.Service;

import io.prasad.ipldashboard.batch.data.model.Match;
import io.prasad.ipldashboard.match.repository.MatchRepository;

@Service
public class MatchServiceImpl implements MatchService{
    
    private MatchRepository  matchRepository;

    public MatchServiceImpl(MatchRepository  matchRepository){
        this.matchRepository = matchRepository;
    }


    public List<Match> getLatesgMatchesByTeamName(String teamName,int count ){
       return  matchRepository.getLatestMatchesByTeamName(teamName.toLowerCase(),count);
    }

}
