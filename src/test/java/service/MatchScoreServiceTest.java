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
        ongoingMatch.getPlayerOneScore().setPoint(1);
        ongoingMatch.getPlayerTwoScore().setPoint(2);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(2, ongoingMatch.getPlayerScoreById(1).getPoint());
    }

    @Test
    void testAddPoint_GameReachesDeuceAtFortyForty(){
        ongoingMatch.getPlayerOneScore().setPoint(3);
        ongoingMatch.getPlayerTwoScore().setPoint(3);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertTrue(ongoingMatch.isGameDeuce());
        assertEquals(1, ongoingMatch.getPlayerScoreById(1).getPoint());
        assertEquals(0, ongoingMatch.getOpponentScoreByPlayerId(1).getPoint());
    }

    @Test
    void testAddPoint_WinnerWithAdvantageShouldWinGame(){
        ongoingMatch.getPlayerScoreById(1).setPoint(1);
        ongoingMatch.getOpponentScoreByPlayerId(1).setPoint(0);
        ongoingMatch.setGameDeuce();

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertFalse(ongoingMatch.isGameDeuce());
        assertEquals(1, ongoingMatch.getPlayerScoreById(1).getGame());
        assertEquals(0, ongoingMatch.getPlayerScoreById(1).getPoint());
    }

    @Test
    void testTieBreakStartsWhenSetScoreIsSixSix(){
        ongoingMatch.getPlayerOneScore().setPoint(3);
        ongoingMatch.getPlayerTwoScore().setPoint(0);
        ongoingMatch.getPlayerOneScore().setGame(6);
        ongoingMatch.getPlayerTwoScore().setGame(6);

        matchScoreService.addPointToPlayer(ongoingMatch, 1);

        assertTrue(ongoingMatch.isTieBreak());
        assertEquals(0, ongoingMatch.getPlayerOneScore().getSet());
        assertEquals(1,ongoingMatch.getPlayerOneScore().getGame());
        assertEquals(0,ongoingMatch.getPlayerTwoScore().getGame());

    }
    @Test
    void testSetDoesNotEndsWhenAdvantageLessThanTwo(){
        ongoingMatch.getPlayerOneScore().setPoint(3);
        ongoingMatch.getPlayerTwoScore().setPoint(0);
        ongoingMatch.getPlayerOneScore().setGame(5);
        ongoingMatch.getPlayerTwoScore().setGame(5);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(0, ongoingMatch.getPlayerOneScore().getSet());
    }

    void testSetEndsWhenAdvantageMoreThanTwo(){
        ongoingMatch.getPlayerOneScore().setPoint(3);
        ongoingMatch.getPlayerTwoScore().setPoint(0);
        ongoingMatch.getPlayerOneScore().setGame(5);
        ongoingMatch.getPlayerTwoScore().setGame(2);

        matchScoreService.addPointToPlayer(ongoingMatch,1);

        assertEquals(1, ongoingMatch.getPlayerOneScore().getSet());
    }



}
