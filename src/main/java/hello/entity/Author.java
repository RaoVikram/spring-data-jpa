package hello.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "isalive")
    private boolean isAlive;

    protected Author(){

    }

    public Author(String firstName,String lastName,boolean isAlive){
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAlive = isAlive;
    }

}
