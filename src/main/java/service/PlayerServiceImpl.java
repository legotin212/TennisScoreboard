package service;

import entity.Player;
import repository.DefaultPlayerRepository;
import repository.PlayerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository = new DefaultPlayerRepository();

    public PlayerServiceImpl() {
    }

    @Override
    @Transactional
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
