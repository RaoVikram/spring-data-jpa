package hello.transact;

import hello.entity.Greeting;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
public class Transact {

    @PostPersist
    protected void doTransact(final Greeting greeting) {
        System.out.println("#####");
        System.out.println(greeting.getGreetingid());
        System.out.println("#####");
        System.out.println(greeting.getMessage());
        System.out.println("#####");
        System.out.println(greeting.getMessageauthor());

    }

}