package service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {

    private int playerPoint;
    private int playerGame;
    private int playerSet;

    private static final int DEFAULT_PLAYER_POINT = 0;
    private static final int DEFAULT_PLAYER_GAME = 0;
    private static final int DEFAULT_PLAYER_SET = 0;


    private Score(int playerPoint, int playerGame, int playerSet) {
        this.playerPoint = playerPoint;
        this.playerGame = playerGame;
        this.playerSet = playerSet;
    }

    public Score() {
    this(
            DEFAULT_PLAYER_POINT,
            DEFAULT_PLAYER_GAME,
            DEFAULT_PLAYER_SET);
   }

   public void increasePlayerPoint() {
        playerPoint++;
   }

   public void increasePlayerGame() {
        playerGame++;
   }
   public void increasePlayerSet() {
        playerSet++;
   }


}
