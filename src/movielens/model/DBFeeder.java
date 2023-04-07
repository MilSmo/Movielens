package movielens.model;

import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import javax.persistence.Query;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class DBFeeder {

static void feedUsers(Session ses) throws IOException {

    Transaction t = ses.beginTransaction();

    CSVReader reader = new CSVReader("src\\movielens\\model\\users.csv", ",", true);
    while (reader.next()) {
        User user = new User();
        user.setId(reader.getLong("userId"));

        user.setForename(reader.get("foreName"));
        user.setSurname(reader.get("surName"));
        user.setEmail(reader.get("email"));

        ses.save(user);

    }
    t.commit();

}

static void feedMovies(Session ses) throws IOException {
    Transaction t = ses.beginTransaction();

    CSVReader reader = new CSVReader("src\\movielens\\model\\movies.csv", ",", true);
    while (reader.next()) {
        Movie movie = new Movie();
        movie.setId(reader.getLong("movieId"));
        movie.setTitle(reader.get("title"));
        Set<MovieGenre> movieGenreSet = new HashSet<>();
        if (reader.get("genres").contains("|")) {
            for (String genre : reader.get("genres").split("\\|")) {
                if (genre.contains("\"")) {
                    continue;
                }
                MovieGenre movieGenre = new MovieGenre();
                movieGenre.setMovie(movie);
                movieGenre.setGenre(genre);
                movieGenreSet.add(movieGenre);
            }
        }
        else {

            MovieGenre movieGenre = new MovieGenre();
            movieGenre.setMovie(movie);
            movieGenre.setGenre(reader.get("genres"));
            movieGenreSet.add(movieGenre);
        }
        movie.setGenreList(movieGenreSet);
        ses.save(movie);
    }
    t.commit();


}




    static void feedTags(Session ses) throws IOException {

        Transaction t = ses.beginTransaction();

        CSVReader reader = new CSVReader("src\\movielens\\model\\tags.csv", ",", true);
        while (reader.next()) {
            Tag tag = new Tag();
            tag.setTag(reader.get("tag"));
            tag.setDate(new java.sql.Date(reader.getLong("timestamp")*1000));
            tag.setUser(ses.get(User.class, reader.getLong("userId")));
            tag.setMovie(ses.get(Movie.class, reader.getLong("movieId")));
            ses.merge(tag);
        }
        t.commit();

    }

    static void feedRatings(Session ses) throws IOException {

        Transaction t = ses.beginTransaction();

        CSVReader reader = new CSVReader("src\\movielens\\model\\ratings.csv", ",", true);
        while (reader.next()) {
            Rating rating = new Rating();
            String ratingValue = reader.get("rating").split("\\.")[0];
            rating.setRating(Integer.parseInt(ratingValue));
            rating.setDate(new java.sql.Date(reader.getLong("timestamp")*1000));
            rating.setUser(ses.get(User.class, reader.getLong("userId")));
            rating.setMovie(ses.get(Movie.class, reader.getLong("movieId")));
            ses.merge(rating);
        }
        t.commit();

    }
    static void check(Session ses){

        for(var cls: Arrays.asList("User","Movie","MovieGenre","Tag","Rating")){
            Query query = ses.createQuery("select count(*) from "+cls);
            Long count = (Long) ((org.hibernate.query.Query<?>) query).uniqueResult();
            System.out.println(String.format("%s:%d",cls,count));
        }


    }
    static void delete_movies(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session ses = sessionFactory.openSession();
        Transaction t = ses.beginTransaction();
        Query query = ses.createQuery("delete from Movie");
        query.executeUpdate();
        t.commit();
        ses.close();
    }
    static void deleteAll(Session ses){

        Transaction t = ses.beginTransaction();
        for(var cls: Arrays.asList("User","Movie","MovieGenre","Tag")){
            Query query = ses.createQuery("delete from "+cls);
            query.executeUpdate();
        }
        t.commit();

    }
}