package io.prasad.ipldashboard.match.service;

import java.util.List;

import io.prasad.ipldashboard.batch.data.model.Match;

public interface MatchService {
    public List<Match> getLatesgMatchesByTeamName(String teamName,int count);
}
