package movielens.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;

public class DbQueries {
//    static void sampleQueryWithJoin(Session ses) {
//
//        long start = System.nanoTime();
//        Query q = ses.createQuery("from Movie m inner join Rating r on r.ratingId.movie=m where r.rating = 5", Movie.class);
//        q.setMaxResults(300);
//        List<Movie> movies = q.getResultList();
//        long end = System.nanoTime();
//        System.out.println("Czas DB:" + (end - start) / 1e6);
//        System.out.println(movies.size());
//        for (var m : movies) {
//            System.out.println(m);
//        }
//
//    }
//    static void sampleQueryWithJoin2() {
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session ses = sessionFactory.openSession();
//        long start = System.nanoTime();
//        Query q = ses.createQuery("from Movie m inner join Rating r on r.ratingId.movie=m where r.rating = 5",Object[].class);
//        q.setMaxResults(300);
//        List<Object[]> moviesRatings = q.getResultList();
//        long end = System.nanoTime();
//        System.out.println("Czas DB:" + (end - start) / 1e6);
//        System.out.println(moviesRatings.size());
//        for (var m : moviesRatings) {
//            System.out.println(m[0]);
//            System.out.println(((Rating)m[1]).getRating());
//        }
//        ses.close();
//    }



    static void moviesDrama(Session ses) {

        long start = System.nanoTime();
        Query q = ses.createQuery("from Movie m join m.genreList g where g.movieGenreId.genre='Drama'");
        q.setMaxResults(100);
        List<Object[]> movies = q.getResultList();
        long end = System.nanoTime();
        System.out.println("Czas DB:" + (end - start) / 1e6);
        System.out.println(movies.size());
        for (var m : movies) {
            System.out.println(m[0]);
        }
    }

    static void mostRatingsUsers(Session ses) {

        long start = System.nanoTime();
        Query q = ses.createQuery("from User u order by size(u.ratings) desc");
        List<User> users = q.getResultList();
        q.setMaxResults(10);
        long end = System.nanoTime();
        System.out.println("Czas DB:" + (end - start) / 1e6);
        System.out.println(users.size());
        for(int i=0;i<10;i++){
            System.out.println(users.get(i));

        }
    }

    static void RichardOliverTag(Session ses){
        long start = System.nanoTime();
        Query q = ses.createQuery("from Movie m join m.tags tags join tags.user u where u.surname='Oliver' and u.forename='Richard'");
        List<Object[]> movies= q.getResultList();
        long end = System.nanoTime();
        System.out.println("Czas DB:" + (end - start) / 1e6);
        System.out.println(movies.size());
        for (var m : movies) {
            System.out.println(m[0]);
        }
    }
    static void LeonardoDicaprioTag(Session ses){
        long start = System.nanoTime();
        Query q = ses.createQuery("from Movie m join m.tags t where t.tag='Leonardo DiCaprio'");
        List<Object[]> movies= q.getResultList();
        long end = System.nanoTime();
        System.out.println("Czas DB:" + (end - start) / 1e6);
        System.out.println(movies.size());
        for (var m : movies) {
            System.out.println(m[0]);
        }
    }

    static void ratigsPerYear(Session ses){
        long start = System.nanoTime();
        Query q = ses.createQuery("select year(r.date),count(r) from Rating r group by year(r.date) order by year(r.date)");
        List<Object[]> count= q.getResultList();
        long end = System.nanoTime();
        System.out.println("Czas DB:" + (end - start) / 1e6);
        System.out.println(count.size());
        for (var c : count) {
            System.out.println(c[0]+" "+c[1]);
        }

    }

}