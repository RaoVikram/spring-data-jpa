package hello.controller;

import hello.model.Greeting;
import hello.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("hello")
public class HelloController {

    private HelloWorldService helloWorldService;

    @Autowired
    public HelloController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @RequestMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping(value = "/addGreeting", method = RequestMethod.POST, consumes = "application/json")
    public void addPatient(@RequestBody Greeting greeting) {
        helloWorldService.addGreeting(greeting.getMessage(), greeting.getMessageAuthor(), greeting.getAuthor());
    }

    @RequestMapping(value = "/addAGreeting", method = RequestMethod.POST, consumes = "application/json")
    public void addAPatient(@RequestBody Greeting greeting) {
        helloWorldService.addAGreeting(greeting.getMessage(), greeting.getMessageAuthor(), greeting.getAuthor());
    }

    @RequestMapping(value = "/getGreetings", method = RequestMethod.GET)
    public List<Greeting> getGreetings() throws ExecutionException, InterruptedException {
        return helloWorldService.getGreetings();
    }

    @RequestMapping(value = "/getGreetingSpec", method = RequestMethod.GET)
    public List<Greeting> getGreetingSpec(@RequestParam("author") String author, @RequestParam("message") String messsage) throws ExecutionException, InterruptedException {
        return helloWorldService.getGreetingUsingSpecs(author, messsage);
    }

    @RequestMapping(value = "/getGreetingById", method = RequestMethod.GET)
    public Greeting getGreetingById(@RequestParam("id") String id) {
        return helloWorldService.getGreetingById(id);
    }

     @RequestMapping(value="/getGreetingByJoin", method =RequestMethod.GET)
    public List<Greeting> getGreetingByJoin(@RequestParam("author") String author, @RequestParam("message") String messsage) throws ExecutionException, InterruptedException {
         return helloWorldService.getGreetingsJoins(author, messsage);
     }
}
