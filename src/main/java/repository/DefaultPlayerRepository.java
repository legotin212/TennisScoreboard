package repository;


import entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultPlayerRepository implements PlayerRepository {


    @Override
    public void save(Player player) {
        Transaction transaction = null;//Вынести в переменную класса?
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save player", e);
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        Optional<Player> player;
        Transaction transaction;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            player = session.createQuery("from Players where name = :name", Player.class).setParameter("name", name).uniqueResultOptional();
            transaction.commit();
        }
        return player;

    }


    @Override
    public List<Player> findAll() {
        List<Player> players = Collections.emptyList();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {

            transaction = session.beginTransaction();
            players = session.createQuery("from Players", Player.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return players;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        Optional<Player> player;
        Transaction transaction;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            player = session.createQuery("from Players where id = :id", Player.class).setParameter("id", id).uniqueResultOptional();
            transaction.commit();
        }

        return player;
    }
}
