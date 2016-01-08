package hello.service;

import hello.entity.Greeting;
import hello.repo.GreetingRepository;
import org.hibernate.Query;
import org.hibernate.internal.QueryImpl;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Created by vikramra on 12/11/15.
 */
@Service
public class HelloWorldService {

    private GreetingRepository greetingRepository;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public HelloWorldService(GreetingRepository greetingRepository ) {
        this.greetingRepository = greetingRepository;

        entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addGreeting(String message, String messageAuthor) {

        this.greetingRepository.save(new Greeting(UUID.randomUUID().getMostSignificantBits(), message, messageAuthor));
    }

    public List<hello.model.Greeting> getGreetings() throws ExecutionException, InterruptedException {
        List<hello.model.Greeting> greetings = new ArrayList<hello.model.Greeting>();

        mapGreetings(greetings, this.greetingRepository.findAll());
        return greetings;
    }

    private void mapGreetings(List<hello.model.Greeting> greetings, Iterable<Greeting> greetingList) throws InterruptedException, ExecutionException {
        for (Greeting greeting : greetingList) {
            hello.model.Greeting item = new hello.model.Greeting();
            item.setMessage(greeting.getMessage());
            item.setMessageAuthor(greeting.getMessageauthor());
            greetings.add(item);
        }
    }

    private void mapGreeting(List<hello.model.Greeting> greetings, List<Greeting> greetingList) throws InterruptedException, ExecutionException {
        for (Greeting greeting : greetingList) {
            hello.model.Greeting item = new hello.model.Greeting();
            item.setMessage(greeting.getMessage());
            item.setMessageAuthor(greeting.getMessageauthor());
            greetings.add(item);
        }
    }

    public List<hello.model.Greeting> getGreeting(String author) throws ExecutionException, InterruptedException {
        List<hello.model.Greeting> greetings = new ArrayList<hello.model.Greeting>();
        getGreetingCreteriaWay(author);

        mapGreeting(greetings, this.greetingRepository.findByMessageauthor(author).get());
        return greetings;
    }

    public hello.model.Greeting getGreetingById(String Id) {
        Greeting greeting = this.greetingRepository.findGreetingByID(Long.valueOf(Id));
        hello.model.Greeting item = new hello.model.Greeting();
        item.setMessage(greeting.getMessage());
        item.setMessageAuthor(greeting.getMessageauthor());
        return item;
    }

    public List<hello.model.Greeting> getGreetingCreteriaWay(String author) throws ExecutionException, InterruptedException {
        List<hello.model.Greeting> greetings = new ArrayList<hello.model.Greeting>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Greeting> greetingCriteriaQuery = cb.createQuery(Greeting.class);
        Root<Greeting> greeting = greetingCriteriaQuery.from(Greeting.class);

        ((CriteriaQueryImpl) greetingCriteriaQuery).where
                (cb.equal(greeting.<String>get("messageauthor"), cb.parameter(String.class, "messageauthorparam")));
        TypedQuery<Greeting> greetingTypedQuery = entityManager.createQuery(greetingCriteriaQuery);
        greetingTypedQuery.setParameter("messageauthorparam", author);


        System.out.println("######" + greetingTypedQuery.unwrap(Query.class).getQueryString());

        List<Greeting> result = greetingTypedQuery.getResultList();

        mapGreeting(greetings, result);
        System.out.println("######" + greetingTypedQuery.unwrap(QueryImpl.class).getQueryString());

        return greetings;

    }

}
