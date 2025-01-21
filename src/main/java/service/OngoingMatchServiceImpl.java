package service;

import dto.ScoreDto;
import dto.ScoreResponseDto;
import entity.Match;
import entity.Player;
import exception.MatchNotFoundException;
import mapper.OngoingMatchToMatchMapper;
import mapper.ScoreDtoMapper;
import repository.DefaultMatchRepository;
import repository.DefaultPlayerRepository;
import repository.MatchRepository;
import repository.PlayerRepository;

import service.servicemodel.OngoingMatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchServiceImpl implements OngoingMatchService {
    private static final Map<UUID,OngoingMatch> matches = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private static OngoingMatchServiceImpl instance;

    public static OngoingMatchServiceImpl getInstance() {
        if (instance == null) {
            synchronized (OngoingMatchServiceImpl.class) {
                if (instance == null) {
                    instance = new OngoingMatchServiceImpl();
                }
            }
        }
        return instance;
    }

    private OngoingMatchServiceImpl() {
        this.playerRepository = new DefaultPlayerRepository();
        this.matchRepository = new DefaultMatchRepository();
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
        throw new MatchNotFoundException("Match with UUID " + matchId + " not found");
    }

    @Override
    public void endMatch(OngoingMatch match, Integer winnerId) {
        matches.remove(match.getUuid());
        OngoingMatchToMatchMapper mapper = new OngoingMatchToMatchMapper();
        List<Player> players = getPLayersForMatch(match);
        Match endedMatch =mapper.mapOngoingMatchToMatch(winnerId,players);
        matchRepository.save(endedMatch);
    }

    @Override
    public ScoreResponseDto getScoreResponseDto(OngoingMatch match) {
        List<Player> players = getPLayersForMatch(match);
        String playerOneName = players.get(0).getName();
        String playerTwoName = players.get(1).getName();

        Integer playerOneID = match.getPlayerOneId();
        Integer playerTwoID = match.getPlayerTwoId();
        ScoreDtoMapper mapper = new ScoreDtoMapper();
        ScoreDto score = mapper.mapScoreDto(match);

        return new ScoreResponseDto(score,playerOneName,playerTwoName,playerOneID,playerTwoID);
    }

    private List<Player> getPLayersForMatch(OngoingMatch match) {
        Optional<Player> playerOne = playerRepository.findById(match.getPlayerOneId());
        Optional<Player> playerTwo = playerRepository.findById(match.getPlayerTwoId());
        if(playerOne.isPresent() && playerTwo.isPresent()) {
            List<Player> players = new ArrayList<>();
            players.add(playerOne.get());
            players.add(playerTwo.get());
            return players;
        }
        throw new IllegalArgumentException("Player not found");
    }



 }

