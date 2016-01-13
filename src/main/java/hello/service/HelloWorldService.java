package hello.service;

import hello.entity.Greeting;
import hello.model.Author;
import hello.repo.AuthorRepository;
import hello.repo.GreetingRepository;
import org.hibernate.Query;
import org.hibernate.internal.QueryImpl;
import org.hibernate.internal.SQLQueryImpl;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spec.GreetingSpecification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class HelloWorldService {

    private GreetingRepository greetingRepository;
    private AuthorRepository authorRepository;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    public HelloWorldService(GreetingRepository greetingRepository, AuthorRepository authorRepository) {
        this.greetingRepository = greetingRepository;
        this.authorRepository = authorRepository;
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addGreeting(String message, String messageAuthor, Author author) {
        greetingRepository.save(new Greeting(message, messageAuthor, new hello.entity.Author(author.getFirstName(), author.getLastName(), author.isAlive())));

    }

    public void addAGreeting(String message, String messageAuthor, Author author) {
        Greeting greeting = new Greeting(message, messageAuthor, new hello.entity.Author(author.getFirstName(), author.getLastName(), author.isAlive()));
        Long lastGreetingId = getLastGreetingId();
        Long lastAuthorId = getLastAuthorId();
        javax.persistence.Query greetingQuery = entityManager.
                createNativeQuery("Insert into greeting VALUES (" + lastGreetingId + ",'" + greeting.getMessage() + "','"
                        + greeting.getMessageauthor() + "',"+ lastAuthorId+ ")");
        javax.persistence.Query authorQuery = entityManager.
                createNativeQuery("Insert into author VALUES (" + lastAuthorId + ", '" + author.getFirstName() + "',"
                        + author.isAlive() + ",'" + author.getLastName() + "')");
        TypedQuery<Greeting> typedQuery = (TypedQuery<Greeting>) greetingQuery;
        System.out.println("######" + typedQuery.unwrap(SQLQueryImpl.class).getQueryString());

        entityManager.getTransaction().begin();
        authorQuery.executeUpdate();
        greetingQuery.executeUpdate();
        entityManager.getTransaction().commit();
        System.out.println("######" + typedQuery.unwrap(SQLQueryImpl.class).getQueryString());
    }

    public List<hello.model.Greeting> getGreetingUsingSpecs(String messageAuthor,String message) throws ExecutionException, InterruptedException {
       List<hello.model.Greeting> greetings = new ArrayList<>();
        Map map= new HashMap<>();
        if(message!=null)
            map.put("message",message);
        if(messageAuthor!=null)
            map.put("messageauthor",messageAuthor);
        GreetingSpecification greetingSpecification = new GreetingSpecification(map);
        mapGreetings(greetings,greetingRepository.findAll(greetingSpecification));
        return greetings;
    }


    private Long getLastAuthorId() {
        Long id = 1L;
        for (hello.entity.Author author : authorRepository.findAll()) {
            id = id <= author.getId() ? id + 1 : id;
        }
        return id;
    }

    private Long getLastGreetingId() {
        Long id = 1L;
        for (Greeting greeting : greetingRepository.findAll()) {
            id = id <= greeting.getGreetingid() ? id + 1 : id;
        }
        return id;
    }

    public List<hello.model.Greeting> getGreetings() throws ExecutionException, InterruptedException {
        List<hello.model.Greeting> greetings = new ArrayList<hello.model.Greeting>();
        mapGreetings(greetings, greetingRepository.findAll());
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

    public List<hello.model.Author> getGreeting(String author) throws ExecutionException, InterruptedException {
        //List<Author> authors = new ArrayList<Author>();

        //mapGreeting(greetings, greetingRepository.findByMessageauthor(author).get());
        //getGreetingUsingSpecs(author,null);
        return  getGreetingCriteriaWay(author);
    }


    public hello.model.Greeting getGreetingById(String Id) {
        Greeting greeting = greetingRepository.findGreetingByID(Long.valueOf(Id));
        hello.model.Greeting item = new hello.model.Greeting();
        item.setMessage(greeting.getMessage());
        item.setMessageAuthor(greeting.getMessageauthor());
        return item;
    }

    public List<Author> getGreetingCriteriaWay(String author) throws ExecutionException, InterruptedException {
        List<Author> authors = new ArrayList<Author>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<hello.entity.Author> query = cb.createQuery(hello.entity.Author.class);
        Root<hello.entity.Author> authorRoot = query.from(hello.entity.Author.class);

        ((CriteriaQueryImpl) query).where
                (cb.equal(authorRoot.<String>get("firstName"), cb.parameter(String.class, "firstname")));

        TypedQuery<hello.entity.Author> greetingTypedQuery = entityManager.createQuery(query);
        greetingTypedQuery.setParameter("firstname", author);
        System.out.println("######" + greetingTypedQuery.unwrap(Query.class).getQueryString());
        List<hello.entity.Author> result = greetingTypedQuery.getResultList();
        mapAuthor(authors, result);
        System.out.println("######" + greetingTypedQuery.unwrap(QueryImpl.class).getQueryString());

        return authors;

    }

    private void mapAuthor(List<Author> authors, List<hello.entity.Author> result) {
        for ( hello.entity.Author author : result) {
            Author item = new Author();
            item.setAlive(author.isAlive());
            item.setFirstName(author.getFirstName());
            item.setLastName(author.getLastName());
            authors.add(item);
        }
    }
}

