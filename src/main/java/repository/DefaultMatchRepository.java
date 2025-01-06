package repository;

import entity.Match;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class DefaultMatchRepository implements MatchRepository {
    @Override
    public void saveMatch(Match match) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(match);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save match", e);
        }
    }

    @Override
    public List<Match> getMatches() {
        return List.of();
    }

    @Override
    public Optional<Match> findByID(long id) {
        return Optional.empty();
    }
}
