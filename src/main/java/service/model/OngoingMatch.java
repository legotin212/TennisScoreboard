package service.model;

import entity.Match;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class OngoingMatch {
    private final UUID uuid;
    private final long PlayerOneID;
    private final long PlayerTwoID;
    private final Score score;
}
