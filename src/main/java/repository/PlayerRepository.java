package repository;


import entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {
    void save(Player player);
    Optional<Player> findByName(String name);
    List<Player> findAll();

}
