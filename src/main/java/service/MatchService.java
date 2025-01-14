package service;

import service.model.OngoingMatch;

import java.util.UUID;

public interface MatchService {
    UUID createMatch(String player1, String player2);
    OngoingMatch getOngoingMatch(UUID matchId);
    void endMatch(OngoingMatch match, Integer winnerId);
}
