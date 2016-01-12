package hello.repo;

import hello.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    List<Author> findAuthorById(Long id);
    List<Author> findAuthorByFirstName(String firstName);
}
