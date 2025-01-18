package repository;


import entity.Player;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultPlayerRepository implements PlayerRepository {


    @Override
    public void save(Player player) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(player);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException("Database error!");
        }
    }

    @Override
    public Optional<Player> findByName(String name) {
        String hql = "From Player where name = :name";

        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(hql, Player.class).setParameter("name", name).uniqueResultOptional();
        } catch (HibernateException e) {
            throw new RuntimeException("Database error!");
        }
    }


    @Override
    public List<Player> findAll() {
        List<Player> players = Collections.emptyList();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {

            transaction = session.beginTransaction();
            players = session.createQuery("from Player", Player.class).list();
            transaction.commit();
        }
        catch (Exception e) {

        }
        return players;
    }

    @Override
    public Optional<Player> findById(Integer id) {
        Optional<Player> player;
        Transaction transaction;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            player = session.createQuery("from Player where id = :id", Player.class).setParameter("id", id).uniqueResultOptional();
            transaction.commit();
        }

        return player;
    }
}
