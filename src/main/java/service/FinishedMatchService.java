package service;

import dto.MatchResponseDto;

import java.util.List;

public interface FinishedMatchService {
    List<MatchResponseDto> getAll();
    List<MatchResponseDto> findMatchesByPlayerName(String playerName);
}
