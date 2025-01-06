package repository;

import entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void saveMatch(Match match);
    List<Match> getMatches();
    Optional<Match> findByID(long id);
}
