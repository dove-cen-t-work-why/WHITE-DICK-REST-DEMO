package facades;

import dtos.RenameMeDTO;
import entities.Employee;
import entities.Movie;
import entities.RenameMe;
import java.util.List;
import javax.persistence.*;

import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    //Private Constructor to ensure Singleton
    private MovieFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Movie getMovieById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Movie> query = em.createQuery("Select m FROM Movie m WHERE m.id = :id", Movie.class);
            query.setParameter("id", id);
            Movie movie = query.getSingleResult();
            em.getTransaction().commit();

            return movie;

        } finally {
            em.close();
        }
    }

    public List<String> getAllTitles() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Query query = em.createQuery("Select e.title FROM Movie e");
            List<String> result4 = query.getResultList();
            em.getTransaction().commit();
            return result4;

        } finally {
            em.close();
        }
    }

    public void getTestData() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Movie(1972, "Olsenbanden på spanden", new String[]{"Benny", "Egon", "Yvonne"}));
            em.persist(new Movie(1971, "Huset på Christianshavn", new String[]{"Ove Sprogø", "Ghitta Nørby"}));
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public long howManyMovies()
    {
        EntityManager em = emf.createEntityManager();
        try{
           // em.getTransaction().begin();
            return (long) em.createQuery("SELECT count(m.id) From Movie m").getSingleResult();

           // long count = query.getSingleResult();

           // return count;

        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

        MovieFacade test = new MovieFacade();
//        test.getTestData();
        System.out.println(test.getMovieById(1));
//        System.out.println(test.getAllTitles());
        System.out.println(test.howManyMovies());
    }
}
