package service.model;

import entity.Match;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class OngoingMatch {
    private final UUID uuid;
    private final long PlayerOneID;
    private final long PlayerTwoID;
    private final Score score;
}
