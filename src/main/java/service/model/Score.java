package service.model;

import lombok.Setter;


@Setter
public class Score {

    private int point;
    private int game;
    private int set;

    private static final int DEFAULT_PLAYER_POINT = 0;
    private static final int DEFAULT_PLAYER_GAME = 0;
    private static final int DEFAULT_PLAYER_SET = 0;


    private Score(int point, int game, int set) {
        this.point = point;
        this.game = game;
        this.set = set;
    }

    public Score() {
    this(
            DEFAULT_PLAYER_POINT,
            DEFAULT_PLAYER_GAME,
            DEFAULT_PLAYER_SET);
   }

   public void increasePlayerPoint() {
        point++;
   }

   public void increasePlayerGame() {
        game++;
   }
   public void increasePlayerSet() {
        set++;
   }

    public int getSet() {
        return set;
    }

    public int getGame() {
        return game;
    }

    public int getPoint() {
        return point;
    }
}
