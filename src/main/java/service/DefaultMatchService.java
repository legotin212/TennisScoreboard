package service;

import entity.Player;
import repository.DefaultPlayerRepository;
import repository.PlayerRepository;
import service.model.OngoingMatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMatchService implements MatchService{
    private static final Map<UUID,OngoingMatch> matches = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private static DefaultMatchService instance;

    public synchronized static DefaultMatchService getInstance() {
        if(instance == null) {
            instance = new DefaultMatchService(new DefaultPlayerRepository());
        }
        return instance;
    }

    private DefaultMatchService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UUID createMatch(String playerOne, String playerTwo) {
        playerRepository.save(new Player(playerOne));
        playerRepository.save(new Player(playerTwo)); /// Сохраняет если игрока не было либо ничего не делает если уже был благодаря юник индексу бд

        /// теперь получаем айдишки игроков который в любом случае должны быть в бд
        Integer playerOneId = playerRepository.findByName(playerOne).get().getId();
        Integer playerTwoId = playerRepository.findByName(playerTwo).get().getId();

        OngoingMatch newMatch = new OngoingMatch(UUID.randomUUID(), playerOneId, playerTwoId);
        matches.put(newMatch.getUuid(), newMatch);
        return newMatch.getUuid();
    }

    public OngoingMatch getOngoingMatch(UUID matchId) {
        if(matches.containsKey(matchId)) {
            return matches.get(matchId);
        }
        throw new IllegalArgumentException("Match not found");
    }
}
