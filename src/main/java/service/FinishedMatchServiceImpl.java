package service;

import dto.MatchResponseDto;
import entity.Match;
import repository.DefaultMatchRepository;
import repository.MatchRepository;
import service.mapper.MatchResponseDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchServiceImpl implements FinishedMatchService {
    private final MatchRepository matchRepository = new DefaultMatchRepository();

    @Override
    public List<MatchResponseDto> getAll() {
        MatchResponseDtoMapper mapper = new MatchResponseDtoMapper();
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();

        List<Match> matches = matchRepository.findAll();

        matches.forEach(m -> matchResponseDTO.add(mapper.mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
    }

    @Override
    public List<MatchResponseDto> findMatchesByPlayerName(String playerName) {
        List<MatchResponseDto> matchResponseDTO = new ArrayList<>();
        MatchResponseDtoMapper mapper = new MatchResponseDtoMapper();

        List<Match> matches =  matchRepository.findByPlayerName(playerName);

        matches.forEach(m -> matchResponseDTO.add(mapper.mapMatchToMatchResponseDTO(m)));
        return matchResponseDTO;
    }
}
