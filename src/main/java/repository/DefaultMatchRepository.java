package repository;

import entity.Match;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultMatchRepository implements MatchRepository {
    @Override
    public void save(Match match) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(match);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Database error!");
        }
    }

    @Override
    public List<Match> findAll() {
        String hql = "From Match";

        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(hql, Match.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException("Database error!");
        }
    }

    @Override
    public Optional<Match> findByID(long id) {
        return Optional.empty();
    }
    @Override
    public List<Match> findByPlayerName(String playerName) {
        Transaction transaction = null;
        List<Match> matches = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            String hql = """
            SELECT m 
            FROM Match m
            JOIN Player p1 ON m.player1 = p1.id
            JOIN Player p2 ON m.player2 = p2.id
            WHERE p1.name LIKE :playerName OR p2.name LIKE :playerName
        """;
            matches = session.createQuery(hql, Match.class)
                    .setParameter("playerName", playerName + "%") // Поиск имен, начинающихся с 'playerName'
                    .getResultList();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return matches;
    }


}
