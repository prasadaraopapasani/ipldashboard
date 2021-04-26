package io.prasad.ipldashboard.model;

import java.io.Serializable;

import io.prasad.ipldashboard.batch.data.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequestVO implements Serializable{
    
    private Team team;
    
    
}
