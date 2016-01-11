package hello.controller;

import hello.model.Greeting;
import hello.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("hello")
public class HelloController {

    private HelloWorldService helloWorldService;

    @Autowired
    public HelloController(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }
    
    @RequestMapping("/test")
    public String index() {
        return "Greetings from Spring Boot!";
    }


    @RequestMapping(value = "/addGreeting", method = RequestMethod.POST, consumes = "application/json")
    public void addPatient(@RequestBody Greeting greeting) {
      helloWorldService.addGreeting(greeting.getMessage(),greeting.getMessageAuthor());
    }

    @RequestMapping(value="/addAGreeting", method = RequestMethod.POST, consumes = "application/json")
    public void addAPatient(@RequestBody Greeting greeting){
        helloWorldService.addAGreeting(greeting.getMessage(),greeting.getMessageAuthor());
    }

    @RequestMapping(value="/getGreetings", method = RequestMethod.GET)
    public List<Greeting> getGreetings() throws ExecutionException, InterruptedException {
       return helloWorldService.getGreetings();
    }

    @RequestMapping(value="/getGreeting", method = RequestMethod.GET)
    public List<Greeting> getGreeting(@RequestParam("author")String author) throws ExecutionException,InterruptedException {
        return helloWorldService.getGreeting(author);
    }

    @RequestMapping(value="/getGreetingById", method = RequestMethod.GET)
    public Greeting getGreetingById(@RequestParam("id") String id){
        return helloWorldService.getGreetingById(id);
    }


    
}
