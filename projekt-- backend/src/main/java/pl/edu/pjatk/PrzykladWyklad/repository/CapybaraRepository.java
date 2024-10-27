package pl.edu.pjatk.PrzykladWyklad.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.edu.pjatk.PrzykladWyklad.model.Capybara;

import java.util.List;

@Repository
public interface CapybaraRepository
        extends CrudRepository<Capybara, Long> {
   Capybara findByName(String name);

     void deleteById(Long id);


    boolean existsById(Long id);





}
