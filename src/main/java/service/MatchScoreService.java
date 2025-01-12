package service;

import service.model.OngoingMatch;
import service.model.Score;

import java.util.UUID;

public class MatchScoreService {

    private final MatchService matchService = DefaultMatchService.getInstance();

    private static final int POINTS_TO_DEUCE = 3;
    private static final int ZERO = 0;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int GAMES_TO_TIE_BREAK = 5;
    private static final int DIFF_TO_WIN_TIE_BREAK = 2;
    private static final int POINTS_TO_WIN_TIE_BREAK = 7 ;
    private static final int SETS_TO_WIN_MATCH = 2;

    private Score winner;
    private Score looser;

    public void addPointToPlayer(UUID matchId, Integer playerId) {
        OngoingMatch currentMatch = matchService.getOngoingMatch(matchId) ;
        initScores(currentMatch, playerId);
        addPoint(currentMatch, playerId);
    }

    private void addPoint(OngoingMatch currentMatch, Integer playerId) {

       if(currentMatch.isGameDeuce()){
             addPointToGameDeuce(currentMatch, playerId);
             return;
       }

       if(winner.getPlayerPoint() == POINTS_TO_DEUCE && looser.getPlayerPoint() == POINTS_TO_DEUCE) {
           currentMatch.setGameDeuce();
           addPointToGameDeuce(currentMatch, playerId);
           return;
       }
       
       if(winner.getPlayerPoint() == POINTS_TO_DEUCE && looser.getPlayerPoint() < POINTS_TO_DEUCE){
            addGame(currentMatch, playerId);
            resetPoints(currentMatch);
            return;
       }

        winner.increasePlayerPoint();

    }

    private void addGame(OngoingMatch currentMatch, Integer playerId){

        if(currentMatch.isTieBreak()){
            addPointToTieBreak(currentMatch, playerId);
            return;
        }

        if(winner.getPlayerGame() == GAMES_TO_WIN_SET && looser.getPlayerGame() == GAMES_TO_WIN_SET){
            currentMatch.setTieBreak();
            resetGames(currentMatch);
            addPointToTieBreak(currentMatch, playerId);
            return;
        }

        if(winner.getPlayerGame() == GAMES_TO_TIE_BREAK && looser.getPlayerGame() < GAMES_TO_TIE_BREAK  ){
            resetGames(currentMatch);
            addSet(currentMatch, playerId);
            return;
        }

        winner.increasePlayerGame();
    }

    private void addSet(OngoingMatch currentMatch, Integer playerId){
        winner.increasePlayerSet();
        if (winner.getPlayerSet() == SETS_TO_WIN_MATCH){
            matchService.endGame(currentMatch);
        }
    }
    private void addPointToGameDeuce(OngoingMatch currentMatch, Integer playerId){

        if(winner.getPlayerPoint() > looser.getPlayerPoint()){
            resetPoints(currentMatch);
            addGame(currentMatch, playerId);
            return;
        }

        if(winner.getPlayerPoint() == looser.getPlayerPoint()){
            resetPoints(currentMatch);
        }
        winner.increasePlayerPoint();

    }

    private void addPointToTieBreak(OngoingMatch currentMatch, Integer playerId){

        winner.increasePlayerGame();

        if(winner.getPlayerGame() >= POINTS_TO_WIN_TIE_BREAK && looser.getPlayerGame() <= winner.getPlayerGame() - DIFF_TO_WIN_TIE_BREAK){
            resetGames(currentMatch);
            currentMatch.endTieBreak();
            addSet(currentMatch, playerId);
        }

    }

    private void resetPoints(OngoingMatch currentMatch){
        currentMatch.getPlayerOneScore().setPlayerPoint(ZERO);
        currentMatch.getPlayerTwoScore().setPlayerPoint(ZERO);
    }

    private void resetGames(OngoingMatch currentMatch){
        currentMatch.getPlayerOneScore().setPlayerGame(ZERO);
        currentMatch.getPlayerTwoScore().setPlayerGame(ZERO);

    }

    private void initScores(OngoingMatch currentMatch, Integer playerId){
        Score winner = currentMatch.getPlayerScoreById(playerId);
        Score looser = currentMatch.getOpponentScoreByPlayerId(playerId);
    }
}
