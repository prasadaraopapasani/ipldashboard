package io.prasad.ipldashboard.model;

import java.io.Serializable;
import io.prasad.ipldashboard.batch.data.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class TeamResponseVO implements Serializable{
    
    private Team team;
    
}
