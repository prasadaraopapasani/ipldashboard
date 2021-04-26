package io.prasad.ipldashboard.batch.data;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import io.prasad.ipldashboard.batch.data.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>{
    

  private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

  @Override
  public Match process(final MatchInput matchInput) throws Exception {
    log.debug("starts process the Match input data");
    String firstInningsTeam,secondInningsTeam;

    if("bat".equalsIgnoreCase(matchInput.getToss_decision())){
      firstInningsTeam=matchInput.getToss_winner();
      secondInningsTeam=matchInput.getToss_winner().equalsIgnoreCase(matchInput.getTeam1())?matchInput.getTeam2():matchInput.getTeam1();
    }else{
      secondInningsTeam=matchInput.getToss_winner();
      firstInningsTeam=matchInput.getToss_winner().equalsIgnoreCase(matchInput.getTeam1())?matchInput.getTeam2():matchInput.getTeam1();
    }
    return Match.builder().
                id(Long.parseLong(matchInput.getId())).
                city(matchInput.getCity()).
                matchDate(LocalDate.parse(matchInput.getDate())).
                playerOfMatch(matchInput.getPlayer_of_match()).
                venue(matchInput.getVenue()).
                team1(firstInningsTeam.toLowerCase()).
                team2(secondInningsTeam.toLowerCase()).
                tossWinner(matchInput.getToss_winner()).
                tossDecision(matchInput.getToss_decision()).
                matchWinner(matchInput.getWinner()).
                result(matchInput.getResult()).
                resultMargin(matchInput.getResult_margin()).
                umpire1(matchInput.getUmpire1()).
                umpire2(matchInput.getUmpire2()).
                build();
                
  }

}

