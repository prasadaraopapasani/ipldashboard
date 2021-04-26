package io.prasad.ipldashboard.match.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.prasad.ipldashboard.batch.data.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match,Long>{
   
    List<Match> getByTeam1OrTeam2OrderByMatchDateDesc(String team1,String team2,Pageable pageable);

    default List<Match> getLatestMatchesByTeamName(String teamName,int count){
        return getByTeam1OrTeam2OrderByMatchDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
