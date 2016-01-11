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


    protected Greeting() {
    }

    public Greeting(Long ID, String message, String messageAuthor) {
        this.message = message;
        this.messageauthor = messageAuthor;
    }

    @Override
    public String toString() {
        return String.format(
                "Greeting[greetingId=%d, message='%s', messageAuthor='%s']",
                greetingid, message, messageauthor);
    }


}
