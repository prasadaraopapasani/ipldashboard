package io.prasad.ipldashboard.team.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.prasad.ipldashboard.batch.data.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team,Long>{
    Team findByTeamName(String teamName);
}
