package movielens.model;


import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity

@Table(name="users")

public class User {
    @Id

    Long id;

    String forename;

    String surname;

    String email;

    @OneToMany(mappedBy = "movie")
    private
    Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "ratingId.user")
    private
    Set<Rating> ratings = new HashSet<>();

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append(forename);
        b.append(" ");
        b.append(surname);
        b.append(String.format("(%s,id=%d,ratings=%d, tags=%d)",email,id,ratings.size(),tags.size()));
        return b.toString();
    }



}