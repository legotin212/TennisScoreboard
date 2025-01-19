package service.mapper;

import dto.MatchResponseDto;
import entity.Match;

public class MatchResponseDtoMapper {
    public MatchResponseDto mapMatchToMatchResponseDTO(Match match) {

        String playerOne = match.getPlayer1().getName();
        String playerTwo = match.getPlayer2().getName();
        String winner = match.getWinner().getName();

        return new MatchResponseDto(playerOne, playerTwo, winner);

    }
}
