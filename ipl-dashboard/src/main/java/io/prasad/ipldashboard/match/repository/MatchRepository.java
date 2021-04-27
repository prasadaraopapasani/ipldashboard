package io.prasad.ipldashboard.match.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.prasad.ipldashboard.batch.data.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match,Long>{
   
    List<Match> getByTeam1OrTeam2OrderByMatchDateDesc(String team1,String team2,Pageable pageable);
    @Query(value = "SELECT m.team1,count(*) from Match m group by m.team1")
    List<Object[]> findByTeam1AndCountGroupByTeam1();
    @Query(value = "SELECT m.team2,count(*) from Match m group by m.team2")
    List<Object[]> findByTeam2AndCountGroupByTeam2();
    @Query(value = "SELECT m.matchWinner,count(*) from Match m group by m.matchWinner")
    List<Object[]> findByMatchWinnerAndCountGroupByMatchWinner();

    default List<Match> getLatestMatchesByTeamName(String teamName,int count){
        return getByTeam1OrTeam2OrderByMatchDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
