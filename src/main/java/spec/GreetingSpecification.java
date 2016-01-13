package spec;


import hello.entity.Greeting;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GreetingSpecification implements Specification<Greeting> {

    private final Map example;

    public GreetingSpecification(Map example) {
        this.example = example;
    }

    @Override
    public Predicate toPredicate(Root<Greeting> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (example.containsKey("message")) {
            predicates.add(cb.like(cb.lower(root.get("message")), example.get("message").toString().toLowerCase() + "%"));
        }

        if (example.containsKey("messageauthor")) {
            predicates.add(cb.like(cb.lower(root.get("messageauthor")), example.get("messageauthor").toString().toLowerCase() + "%"));
        }

        if (example.containsKey("authorId")) {
            predicates.add(cb.equal(root.get("author_id"), example.get("authorId")));
        }

        return andTogether(predicates, cb);
    }

    private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

