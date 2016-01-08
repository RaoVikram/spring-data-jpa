package hello.repo;

import hello.entity.Greeting;
import hello.transact.Transact;
import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.persistence.PostPersist;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by vikramra on 12/11/15.
 */
@Repository
public interface GreetingRepository extends CrudRepository<Greeting,Long>{
    @Async
    Future<List<Greeting>> findByMessageauthor(String messageauthor);

    @Query("Select g from Greeting g where g.greetingid=:greetingid")
    Greeting findGreetingByID(@org.springframework.data.repository.query.Param("greetingid")Long greetingid);
}
