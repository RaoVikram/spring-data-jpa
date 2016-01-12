package hello.model;

import lombok.Data;

@Data
public class Greeting {
    private String message;
    private String messageAuthor;
    private Author author;
}
