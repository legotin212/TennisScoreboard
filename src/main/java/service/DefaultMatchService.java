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
import service.mapper.ScoreDtoMapper;
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
        matchRepository.save(match);
    }

    @Override
    public List<MatchResponseDto> getAll() {
        MatchResponseDtoMapper mapper = new MatchResponseDtoMapper();
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();

        List<Match> matches = matchRepository.findAll();

        matches.forEach(m -> matchResponseDTO.add(mapper.mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
    }

    @Override
    public List<MatchResponseDto> findMatchesByPlayerName(String playerName) {
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();
        MatchResponseDtoMapper mapper = new MatchResponseDtoMapper();

        List<Match> matches =  matchRepository.findByPlayerName(playerName);

        matches.forEach(m -> matchResponseDTO.add(mapper.mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
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


    private Match mapOngoingMatchToMatch(OngoingMatch match, Integer winnerId) {
        Match result = new Match();
        List<Player> players = getPLayersForMatch(match);
        Player playerOne = players.get(0);
        Player playerTwo = players.get(1);

        if(playerOne.getId().equals(winnerId)) {
            result.setWinner(playerOne);
        }

        if (playerTwo.getId().equals(winnerId) ) {
            result.setWinner(playerTwo);
        }

        result.setPlayer1(playerOne);
        result.setPlayer2(playerTwo);

        return result;

    }
 }

