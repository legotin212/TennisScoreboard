package service;

import lombok.NoArgsConstructor;
import service.factory.MatchServiceFactory;
import service.model.OngoingMatch;
import service.model.Score;

@NoArgsConstructor
public class MatchScoreService {

    private static final int POINTS_TO_DEUCE = 3;
    private static final int ZERO = 0;
    private static final int GAMES_TO_WIN_SET = 6;
    private static final int GAMES_TO_TIE_BREAK = 5;
    private static final int DIFF_TO_WIN_TIE_BREAK = 2;
    private static final int POINTS_TO_WIN_TIE_BREAK = 7 ;
    private static final int SETS_TO_WIN_MATCH = 2;

    private Score winner;
    private Score looser;

    public void addPointToPlayer(OngoingMatch currentMatch, Integer playerId) {
        initializeScores(currentMatch, playerId);
        addPoint(currentMatch, playerId);
    }

    private void addPoint(OngoingMatch currentMatch, Integer playerId) {

       if(currentMatch.isGameDeuce()){
             addPointToGameDeuce(currentMatch, playerId);
             return;
       }

       if(winner.getPoint() == POINTS_TO_DEUCE && looser.getPoint() == POINTS_TO_DEUCE) {
           currentMatch.setGameDeuce();
           addPointToGameDeuce(currentMatch, playerId);
           return;
       }
       
       if(winner.getPoint() == POINTS_TO_DEUCE && looser.getPoint() < POINTS_TO_DEUCE){
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

        if(winner.getGame() == GAMES_TO_WIN_SET && looser.getGame() == GAMES_TO_WIN_SET){
            currentMatch.setTieBreak();
            resetGames(currentMatch);
            addPointToTieBreak(currentMatch, playerId);
            return;
        }

        if(winner.getGame() == GAMES_TO_TIE_BREAK && looser.getGame() < GAMES_TO_TIE_BREAK  ){
            resetGames(currentMatch);
            addSet(currentMatch, playerId);
            return;
        }

        winner.increasePlayerGame();
    }

    private void addSet(OngoingMatch currentMatch, Integer playerId){
        winner.increasePlayerSet();
        if (winner.getSet() == SETS_TO_WIN_MATCH){
            MatchService matchService = MatchServiceFactory.getMatchService();
            matchService.endMatch(currentMatch, playerId);
        }
    }

    private void addPointToGameDeuce(OngoingMatch currentMatch, Integer playerId){

        if(winner.getPoint() > looser.getPoint()){
            resetPoints(currentMatch);
            addGame(currentMatch, playerId);
            currentMatch.endGameDeuce();
            return;
        }

        if(winner.getPoint() == looser.getPoint()){
            resetPoints(currentMatch);
        }
        winner.increasePlayerPoint();

    }

    private void addPointToTieBreak(OngoingMatch currentMatch, Integer playerId){

        winner.increasePlayerGame();

        if(winner.getGame() >= POINTS_TO_WIN_TIE_BREAK && looser.getGame() <= winner.getGame() - DIFF_TO_WIN_TIE_BREAK){
            resetGames(currentMatch);
            currentMatch.endTieBreak();
            addSet(currentMatch, playerId);
        }

    }

    private void resetPoints(OngoingMatch currentMatch){
        currentMatch.getPlayerOneScore().setPoint(ZERO);
        currentMatch.getPlayerTwoScore().setPoint(ZERO);
    }

    private void resetGames(OngoingMatch currentMatch){
        currentMatch.getPlayerOneScore().setGame(ZERO);
        currentMatch.getPlayerTwoScore().setGame(ZERO);

    }

    private void initializeScores(OngoingMatch currentMatch, Integer playerId){
         winner = currentMatch.getPlayerScoreById(playerId);
         looser = currentMatch.getOpponentScoreByPlayerId(playerId);
    }
}
