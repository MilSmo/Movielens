package movielens.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ToString
@Data
@Entity
@Table(name="movies")
public class Movie {
    @Id
    private
    Long id;
    private String title;

    @OneToMany(mappedBy = "movieGenreId.movie", cascade= CascadeType.ALL, fetch=FetchType.LAZY)
    @Getter
    @Setter
    private
    Set<MovieGenre> genreList = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    @Getter
    @Setter
    private
    Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "ratingId.movie")
    @Getter
    @Setter
    private
    Set<Rating> ratings = new HashSet<>();

}











