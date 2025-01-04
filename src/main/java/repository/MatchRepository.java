package repository;

import entity.Match;

import java.util.List;

public interface MatchRepository {
    void saveMatch(Match match);
    List<Match> getMatches();
    Match getMatch(int id);
}
