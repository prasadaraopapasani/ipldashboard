package io.prasad.ipldashboard.batch.data;


import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import io.prasad.ipldashboard.batch.data.model.Team;
import io.prasad.ipldashboard.match.repository.MatchRepository;
import io.prasad.ipldashboard.team.repository.TeamRepository;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
  private MatchRepository matchRepository;
  private TeamRepository teamRepository;

  public  JobCompletionNotificationListener(MatchRepository matchRepository,TeamRepository teamRepository){
      this.matchRepository = matchRepository;
      this.teamRepository = teamRepository;
  }
 /* private EntityManager em;
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }*/

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      Map<String,Team> teamdata=new HashMap<String,Team>();

      /**  reading data from data jpa  */
        /* em.createQuery("SELECT m.team1,count(*) from Match m group by m.team1",Object[].class)
            .getResultList()
            .stream()
            .map(m-> Team.builder().teamName(((String)m[0]).toLowerCase()).totalMatches((long)m[1]).build() )
            .forEach(t-> teamdata.put(t.getTeamName().toLowerCase(), t));
    
            em.createQuery("SELECT m.team2,count(*) from Match m group by m.team2",Object[].class)
            .getResultList()
            .stream()
            .forEach(t-> {
             Team team= teamdata.get(((String)t[0]).toLowerCase());
               if(ObjectUtils.isEmpty(team)){
                   teamdata.put(((String)t[0]).toLowerCase(), Team.builder().teamName((String)t[0]).totalMatches((long)t[1]).build() );
               }else{
                team.setTotalMatches(team.getTotalMatches()+(long)t[1]); 
                teamdata.put(team.getTeamName().toLowerCase(), team);
               }
            });

            em.createQuery("SELECT m.matchWinner,count(*) from Match m group by m.matchWinner",Object[].class)
            .getResultList()
            .stream()
            .forEach(t-> {
              Team team=teamdata.get(((String)t[0]).toLowerCase());
              if(!ObjectUtils.isEmpty(team)){
                   team.setTotalWins(team.getTotalWins()+(long)t[1]);
              }
            });
            teamdata.values().stream().forEach(team-> em.persist(team));
           

           /** reading data using spring data jpa */
            matchRepository.findByTeam1AndCountGroupByTeam1()
                           .stream()
                           .map(m-> Team.builder().teamName(((String)m[0]).toLowerCase()).totalMatches((long)m[1]).build() )
                           .forEach(t-> teamdata.put(t.getTeamName().toLowerCase(), t));

            matchRepository.findByTeam2AndCountGroupByTeam2()
                           .stream()
                           .forEach(t-> {
                                 Team team= teamdata.get(((String)t[0]).toLowerCase());
                                 if(ObjectUtils.isEmpty(team))
                                         teamdata.put(((String)t[0]).toLowerCase(), Team.builder().teamName((String)t[0]).totalMatches((long)t[1]).build() );
                                   else
                                         team.setTotalMatches(team.getTotalMatches()+(long)t[1]); 
                                         teamdata.put(team.getTeamName().toLowerCase(), team);
                              });
            matchRepository.findByMatchWinnerAndCountGroupByMatchWinner()
                           .stream()
                           .forEach(t-> {
                                 Team team=teamdata.get(((String)t[0]).toLowerCase());
                                 if(!ObjectUtils.isEmpty(team))
                                      team.setTotalWins(team.getTotalWins()+(long)t[1]);
                                });
              
            teamdata.values().stream().forEach(team-> teamRepository.save(team));
                         
            log.info("!!! JOB FINISHED! Time to verify the results");
            System.out.println("completed JOB");
    }
  }
}
