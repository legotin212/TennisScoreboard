package mapper;

import dto.ScoreDto;
import service.servicemodel.OngoingMatch;
import service.servicemodel.Score;

public class ScoreDtoMapper {


    public ScoreDto mapScoreDto(OngoingMatch match) {
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
}
