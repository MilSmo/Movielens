package movielens.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.io.IOException;

public class DbSchemaBuilder {
    static Session connectToDb(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        return sessionFactory.openSession();
    }
    public static void main(String[] args) throws IOException {
       Session ses = connectToDb();
        DBFeeder.deleteAll(ses);
        DBFeeder.feedUsers(ses);
        DBFeeder.feedMovies(ses);
        DBFeeder.feedRatings(ses);
        DBFeeder.feedTags(ses);
        //DBFeeder.check(ses);
        //DbQueries.RichardOliverTag(ses);
        //DbQueries.LeonardoDicaprioTag(ses);
        //DbQueries.ratigsPerYear(ses);
        //DbQueries.mostRatingsUsers(ses);
        DbQueries.moviesDrama(ses);

       ses.close();



    }

}