package service;

import dto.MatchResponseDto;
import dto.ScoreDto;
import dto.ScoreResponseDto;
import entity.Match;
import entity.Player;
import repository.DefaultMatchRepository;
import repository.DefaultPlayerRepository;
import repository.MatchRepository;
import repository.PlayerRepository;
import service.mapper.MatchResponseDtoMapper;
import service.mapper.OngoingMatchToMatchMapper;
import service.mapper.ScoreDtoMapper;
import service.model.OngoingMatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultOngoingMatchService implements OngoingMatchService {
    private static final Map<UUID,OngoingMatch> matches = new ConcurrentHashMap<>();
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private static DefaultOngoingMatchService instance;

    public synchronized static DefaultOngoingMatchService getInstance() {
        if(instance == null) {
            instance = new DefaultOngoingMatchService(new DefaultPlayerRepository(), new DefaultMatchRepository());
        }
        return instance;
    }

    public DefaultOngoingMatchService(PlayerRepository playerRepository, MatchRepository matchRepository) {
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

