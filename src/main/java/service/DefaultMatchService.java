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
import service.model.OngoingMatch;
import service.model.Score;

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
        List<Match> matches = matchRepository.findAll();
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();
        matches.forEach(m -> matchResponseDTO.add(mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
    }

    @Override
    public List<MatchResponseDto> findMatchesByPlayerName(String playerName) {
        List<Match> matches =  matchRepository.findByPlayerName(playerName);
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();
        matches.forEach(m -> matchResponseDTO.add(mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
    }

    @Override
    public ScoreResponseDto getScoreResponseDto(OngoingMatch match) {
        List<Player> players = getPLayersForMatch(match);
        String playerOneName = players.get(0).getName();
        String playerTwoName = players.get(1).getName();

        Integer playerOneID = match.getPlayerOneId();
        Integer playerTwoID = match.getPlayerTwoId();

        ScoreDto score = mapScoreDto(match);

        return new ScoreResponseDto(score,playerOneName,playerTwoName,playerOneID,playerTwoID);
    }

    private ScoreDto mapScoreDto(OngoingMatch match) {
        Score playerOneScore = match.getPlayerOneScore();
        Score playerTwoScore = match.getPlayerTwoScore();


        String playerOnePoint = String.valueOf(playerOneScore.getPoint());
        String playerOneGame  = String.valueOf(playerOneScore.getGame());
        String playerOneSet = String.valueOf(playerOneScore.getSet());

        String playerTwoPoint = String.valueOf(playerTwoScore.getPoint());
        String playerTwoGame = String.valueOf(playerTwoScore.getGame());
        String playerTwoSet = String.valueOf(playerTwoScore.getSet());

        if(!match.isGameDeuce()){
            playerOnePoint = getPoint(playerOneScore.getPoint());
            playerTwoPoint = getPoint(playerTwoScore.getPoint());
        }
        if(match.isGameDeuce()){
            playerOnePoint = getDeuceValue(playerOneScore.getPoint(),playerTwoScore.getPoint());
            playerTwoPoint = getDeuceValue(playerTwoScore.getPoint(), playerOneScore.getPoint());
        }

        return new ScoreDto(playerOneSet,playerOneGame,playerOnePoint, playerTwoSet,playerTwoGame,playerTwoPoint);

    }

    private String getPoint(int point) {
        return switch (point) {
            case 1 -> "15";
            case 2 -> "30";
            case 3 -> "40";
            default -> "0";
        };
    }

    private String getDeuceValue(int point, int opponentPoint ) {
        if(point == opponentPoint){
            return "Равно";
        }
        if (point > opponentPoint) {
            return "Больше";
        }
            return "Меньше";
    }

    private MatchResponseDto mapMatchToMatchResponseDTO(Match match) {

        String playerOne = match.getPlayer1().getName();
        String playerTwo = match.getPlayer2().getName();
        String winner = match.getWinner().getName();

        return new MatchResponseDto(playerOne, playerTwo, winner);

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
        ///  убрать обращение к репозиторию из маппера получать в аргументы
    }
}
