package hello.service;

import hello.entity.Author;
import hello.repo.AuthorRepository;
import org.hibernate.Query;
import org.hibernate.internal.QueryImpl;
import org.hibernate.jpa.criteria.CriteriaQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.ExecutionException;

@Service
public class AuthorService {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
        this.authorRepository = authorRepository;
    }

    public List<hello.model.Author> getAuthorById(Long id) {
        List personList = new ArrayList<>();
        return mapPerson(personList, authorRepository.findAuthorById(id));
    }

    public List<hello.model.Author> getAuthorByFirstName(String firstName) {
        return mapPerson(new ArrayList<>(), authorRepository.findAuthorByFirstName(firstName));
    }

    public List<hello.model.Author> getAuthorCriteriaWay(String author) throws ExecutionException, InterruptedException {
        List<hello.model.Author> authors = new ArrayList<hello.model.Author>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(hello.entity.Author.class);
        Root<Author> authorRoot = query.from(hello.entity.Author.class);

        ((CriteriaQueryImpl) query).where
                (cb.equal(authorRoot.<String>get("firstName"), cb.parameter(String.class, "firstname")));

        TypedQuery<Author> greetingTypedQuery = entityManager.createQuery(query);
        greetingTypedQuery.setParameter("firstname", author);
        System.out.println("######" + greetingTypedQuery.unwrap(Query.class).getQueryString());
        List<hello.entity.Author> result = greetingTypedQuery.getResultList();
        mapAuthor(authors, result);
        System.out.println("######" + greetingTypedQuery.unwrap(QueryImpl.class).getQueryString());

        return authors;

    }

    private void mapAuthor(List<hello.model.Author> authors, List<hello.entity.Author> result) {
        for ( hello.entity.Author author : result) {
            hello.model.Author item = new hello.model.Author();
            item.setAlive(author.isAlive());
            item.setFirstName(author.getFirstName());
            item.setLastName(author.getLastName());
            authors.add(item);
        }
    }

    private List mapPerson(List personList, List<Author> authorByPersonid) {
        for (Author author : authorByPersonid) {
            hello.model.Author p = new hello.model.Author();
            p.setFirstName(author.getFirstName());
            p.setLastName(author.getLastName());
            p.setAlive(author.isAlive());
            personList.add(p);
        }
        return personList;
    }
}
