package service;

import dto.ScoreResponseDto;
import service.servicemodel.OngoingMatch;

import java.util.UUID;

public interface OngoingMatchService {
    UUID createMatch(Integer playerOneId, Integer playerTwoId);
    OngoingMatch getOngoingMatch(UUID matchId);
    void endMatch(OngoingMatch match, Integer winnerId);
    ScoreResponseDto getScoreResponseDto(OngoingMatch match);
}
