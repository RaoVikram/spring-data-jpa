package hello.entity;

import hello.transact.Transact;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by vikramra on 12/11/15.
 */
@Entity
@Data
@Table(name = "greeting")
@EntityListeners(Transact.class)
public class Greeting {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "greetingid")
    private Long greetingid;
    private String message;
    private String messageauthor;
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;


    protected Greeting() {
    }

    public Greeting(String message, String messageAuthor,Author author) {
        this.message = message;
        this.messageauthor = messageAuthor;
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format(
                "Greeting[greetingId=%d, message='%s', messageAuthor='%s']",
                greetingid, message, messageauthor);
    }


}
