package service;

import java.util.UUID;

public interface MatchService {
    UUID createMatch(String player1, String player2);
}
