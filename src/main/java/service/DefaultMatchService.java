package service;

import entity.Match;
import entity.Player;
import service.model.OngoingMatch;
import service.model.Score;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMatchService implements MatchService{
    Map<UUID,Score> matches = new ConcurrentHashMap<>();
    @Override
    public UUID createMatch(String playerOne, String playerTwo) {
        playerRepository.save(new Player(playerOne));
        playerRepository.save(new Player(playerTwo)); /// Сохраняет если игрока не было либо ничего не делает если уже был благодаря юник индексу бд

        /// теперь получаем айдишки игроков который в любом случае должны быть в бд
        long playerOneId = playerRepository.getByName(playerOne).getID;
        long playerTwoId = playerRepository.getByName(playerTwo).getID;

        ///  тут нужно создать объект класса который содержит айди игроков, и счет
        /// счет тоже реализовать как класс???
        OngoingMatch newMatch = new OngoingMatch(UUID.randomUUID(), playerOneId, playerTwoId, new Score()); ///создавать фабрикой счет
        matches.add(newMatch);

    }
}
