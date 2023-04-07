package movielens.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name="tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ToString.Exclude
    @ManyToOne(optional = false)
    User user;

    @ToString.Exclude
    @ManyToOne(optional = false)
    Movie movie;


    String tag;

    Date date;





}