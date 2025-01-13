package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.model.OngoingMatch;
import java.util.UUID;
import static junit.framework.Assert.*;

public class MatchScoreServiceTest {

    private MatchScoreService matchScoreService;
    private OngoingMatch ongoingMatch;

    @BeforeEach
    void setUp() {
        matchScoreService = new MatchScoreService();
        ongoingMatch = new OngoingMatch(UUID.randomUUID(),1,2);

    }

    @Test
    void testAddPoint_PlayerOneScores_NormalGame(){
        ongoingMatch.getPlayerOneScore().setPlayerPoint(1);
        ongoingMatch.getPlayerTwoScore().setPlayerPoint(2);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(2, ongoingMatch.getPlayerScoreById(1).getPlayerPoint());
    }

    @Test
    void testAddPoint_GameReachesDeuceAtFortyForty(){
        ongoingMatch.getPlayerOneScore().setPlayerPoint(3);
        ongoingMatch.getPlayerTwoScore().setPlayerPoint(3);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(true, ongoingMatch.isGameDeuce());
        assertEquals(1, ongoingMatch.getPlayerScoreById(1).getPlayerPoint());
        assertEquals(0, ongoingMatch.getOpponentScoreByPlayerId(1).getPlayerPoint());
    }

    @Test
    void testAddPoint_WinnerWithAdvantageShouldWinGame(){
        ongoingMatch.getPlayerScoreById(1).setPlayerPoint(1);
        ongoingMatch.getOpponentScoreByPlayerId(1).setPlayerPoint(0);
        ongoingMatch.setGameDeuce();

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(false, ongoingMatch.isGameDeuce());
        assertEquals(1, ongoingMatch.getPlayerScoreById(1).getPlayerGame());
        assertEquals(0, ongoingMatch.getPlayerScoreById(1).getPlayerPoint());
    }

    @Test
    void testAdvantageLostWhenOpponentScores(){
        ongoingMatch.getPlayerScoreById(1).setPlayerPoint(0);
        ongoingMatch.getOpponentScoreByPlayerId(1).setPlayerPoint(1);
        ongoingMatch.setGameDeuce();

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(0, ongoingMatch.getOpponentScoreByPlayerId(1).getPlayerPoint());
    }

    @Test
    void testTieBreakStartsWhenSetScoreIsSixSix(){
        ongoingMatch.getPlayerOneScore().setPlayerPoint(3);
        ongoingMatch.getPlayerTwoScore().setPlayerPoint(0);
        ongoingMatch.getPlayerOneScore().setPlayerGame(6);
        ongoingMatch.getPlayerTwoScore().setPlayerGame(6);

        matchScoreService.addPointToPlayer(ongoingMatch, 1);

        assertEquals(true, ongoingMatch.isTieBreak());
        assertEquals(0, ongoingMatch.getPlayerOneScore().getPlayerSet());
        assertEquals(1,ongoingMatch.getPlayerOneScore().getPlayerGame());
        assertEquals(0,ongoingMatch.getPlayerTwoScore().getPlayerGame());

    }

}
