package repository;

import entity.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void save(Match match);
    List<Match> findAll();
    Optional<Match> findByID(long id);
    List<Match> findByPlayerName(String playerName);
}
