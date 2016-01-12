package hello.model;

import lombok.Data;

@Data
public class Author {
    private String firstName;

    private String lastName;

    private boolean isAlive;
}
