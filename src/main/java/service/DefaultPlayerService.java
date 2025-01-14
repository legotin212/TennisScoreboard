package service;

import entity.Player;
import repository.DefaultPlayerRepository;
import repository.PlayerRepository;

import java.util.Optional;

public class DefaultPlayerService implements PlayerService {
    private final PlayerRepository playerRepository = new DefaultPlayerRepository();

    public DefaultPlayerService() {
    }

    @Override
    public void createIfNotExists(String playerName) {
        Optional<Player> player = playerRepository.findByName(playerName);
        if (player.isEmpty()) {
            playerRepository.save(new Player(playerName));;
        }
    }

    @Override
    public Optional<Player> findByName(String playerName) {
        return playerRepository.findByName(playerName);
    }
}
