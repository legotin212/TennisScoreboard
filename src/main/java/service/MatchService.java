package service;

import dto.MatchResponseDto;
import dto.ScoreResponseDto;
import service.model.OngoingMatch;

import java.util.List;
import java.util.UUID;

public interface MatchService {
    UUID createMatch(Integer playerOneId, Integer playerTwoId);
    OngoingMatch getOngoingMatch(UUID matchId);
    void endMatch(OngoingMatch match, Integer winnerId);
    List<MatchResponseDto> getAll();
    List<MatchResponseDto> findMatchesByPlayerName(String playerName);
    ScoreResponseDto getScoreResponseDto(OngoingMatch match);
}
