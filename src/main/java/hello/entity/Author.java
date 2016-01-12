package hello.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private boolean isAlive;

    protected Author(){

    }

    public Author(String firstName,String lastName,boolean isAlive){
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAlive = isAlive;
    }

}
