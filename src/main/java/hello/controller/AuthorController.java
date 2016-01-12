package hello.controller;

import hello.model.Author;
import hello.service.AuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/author")
public class AuthorController {

    private AuthorService authorService;
    public AuthorController(AuthorService authorService){
     this.authorService = authorService;
    }

    @RequestMapping("/getById")
    public List<Author> getAuthorById(@RequestParam("id")Long id) {
        return authorService.getPersonById(id);
    }

    @RequestMapping("/getById")
    public List<Author> getAuthorByFirstName(@RequestParam("firstName")String firstName) {
        return authorService.getPersonByFirstName(firstName);
    }

}
