package service;

import entity.Player;

import java.util.Optional;

public interface PlayerService {
    void createIfNotExists(String playerName);
    Optional<Player> findByName(String playerName);
}
