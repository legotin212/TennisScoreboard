package service;

import entity.Match;
import entity.Player;
import repository.DefaultMatchRepository;
import repository.DefaultPlayerRepository;
import repository.MatchRepository;
import repository.PlayerRepository;
import service.model.OngoingMatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMatchService implements MatchService{
    private static final Map<UUID,OngoingMatch> matches = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private static DefaultMatchService instance;

    public synchronized static DefaultMatchService getInstance() {
        if(instance == null) {
            instance = new DefaultMatchService(new DefaultPlayerRepository(), new DefaultMatchRepository());
        }
        return instance;
    }

    public DefaultMatchService(PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public UUID createMatch(Integer playerOneId, Integer playerTwoId) {
        OngoingMatch newMatch = new OngoingMatch(UUID.randomUUID(), playerOneId, playerTwoId);
        matches.put(newMatch.getUuid(), newMatch);
        return newMatch.getUuid();
    }

    @Override
    public OngoingMatch getOngoingMatch(UUID matchId) {
        if(matches.containsKey(matchId)) {
            return matches.get(matchId);
        }
        throw new IllegalArgumentException("Match not found");
    }

    @Override
    public void endMatch(OngoingMatch endedMatch, Integer winnerId) {
        matches.remove(endedMatch.getUuid());
        Match match = mapOngoingMatchToMatch(endedMatch, winnerId);
        matchRepository.saveMatch(match);
    }

    private Match mapOngoingMatchToMatch(OngoingMatch match, Integer winnerId) {
        Optional<Player> playerOne = playerRepository.findById(match.getPlayerOneId());
        Optional<Player> playerTwo = playerRepository.findById(match.getPlayerTwoId());
        Optional<Player> winner = playerRepository.findById(winnerId);
        Match result = new Match();

        if (playerOne.isPresent() && playerTwo.isPresent() && winner.isPresent()) {
            result.setPlayer1(playerOne.get());
            result.setPlayer2(playerOne.get());
            result.setWinner(playerOne.get());
            return result;
        }
        throw new IllegalArgumentException("Player not found");
        /// Исправить

    }
}
