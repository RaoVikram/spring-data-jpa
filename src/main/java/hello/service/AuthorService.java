package hello.service;

import hello.entity.Author;
import hello.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<hello.model.Author> getPersonById(Long id) {
        List personList = new ArrayList<>();
        return mapPerson(personList, authorRepository.findAuthorById(id));
    }

    public List<hello.model.Author> getPersonByFirstName(String firstName) {
        return mapPerson(new ArrayList<>(), authorRepository.findAuthorByFirstName(firstName));
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
