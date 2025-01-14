package service.model;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public class OngoingMatch {
    @Getter
    private final UUID uuid;
    private final Map<Integer,Score> playerScores;
    @Getter
    private final Integer playerOneId;
    @Getter
    private final Integer playerTwoId;

    private boolean isGameDeuce;
    private boolean isTieBreak;

    public OngoingMatch(UUID matchUUID, Integer playerOneID, Integer playerTwoID) {
        this.uuid = matchUUID;
        this.playerScores = Map.of(
                playerOneID, new Score(),
                playerTwoID, new Score()
        );
        this.playerOneId = playerOneID;
        this.playerTwoId = playerTwoID;
    }

    public Score getPlayerScoreById(Integer playerId) {
        return playerScores.get(playerId);
    }

    public Score getOpponentScoreByPlayerId(Integer playerId) {
       if(playerId.equals(playerOneId)){
           return getPlayerScoreById(playerTwoId);
       }
       return getPlayerScoreById(playerOneId);
    }

    public void setGameDeuce() {
        this.isGameDeuce = true;
    }

    public void setTieBreak() {
        this.isTieBreak = true;
    }

    public  void endGameDeuce(){
        this.isGameDeuce = false;
    }

    public void endTieBreak(){
        this.isTieBreak = false;
    }

    public boolean isGameDeuce() {
        return isGameDeuce;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }

    public Score getPlayerOneScore(){
        return playerScores.get(playerOneId);
    }

    public Score getPlayerTwoScore(){
        return playerScores.get(playerTwoId);
    }



}
