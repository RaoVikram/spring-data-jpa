package hello.transact;

import hello.entity.Greeting;
import hello.repo.GreetingRepository;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

/*
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Greeting> greetingCriteriaQuery = criteriaBuilder.createQuery(Greeting.class);
        Root<Greeting> root = greetingCriteriaQuery.from(Greeting.class);
        TypedQuery<Greeting> greetingTypedQuery = entityManager.createQuery(greetingCriteriaQuery);

        System.out.println("######" + greetingTypedQuery.unwrap(Query.class).getQueryString());*/

    }

}