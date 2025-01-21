package service.mapper;

import entity.Match;
import entity.Player;
import service.model.OngoingMatch;

import java.util.List;

public class OngoingMatchToMatchMapper {

    public Match mapOngoingMatchToMatch(Integer winnerId, List<Player> players) {
        Match result = new Match();
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
