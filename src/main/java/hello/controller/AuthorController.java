package hello.controller;

import hello.model.Author;
import hello.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("author")
@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping("/getById")
    public List<Author> getAuthorById(@RequestParam("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @RequestMapping("/getByFirstName")
    public List<Author> getAuthorByFirstName(@RequestParam("firstName") String firstName) {
        return authorService.getAuthorByFirstName(firstName);
    }

    @RequestMapping(value = "/getAuthors", method = RequestMethod.GET)
    public List<Author> getAuthorCriteriaWay(@RequestParam("author") String author) throws ExecutionException, InterruptedException {
        return authorService.getAuthorCriteriaWay(author);
    }

}
