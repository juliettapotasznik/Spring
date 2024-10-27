package pl.edu.pjatk.PrzykladWyklad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.edu.pjatk.PrzykladWyklad.model.Capybara;
import pl.edu.pjatk.PrzykladWyklad.service.CapybaraService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
public class CapybaraController {
    private final CapybaraService service;
    private Logger logger= Logger.getLogger("CapybaraController");



    @Autowired
    public CapybaraController(CapybaraService capybaraService) {
        this.service = capybaraService;
        this.service.save(new Capybara("Julietta", 2));
        this.service.save(new Capybara("Michał", 1));
        this.service.save(new Capybara("Marcysia", 8));
        this.service.save(new Capybara("Julia", 10));
    }
//Pobiera po id
    @GetMapping("capybara/get/{id}")
    public Optional<Capybara> findById(@PathVariable("id") Long id) {
        logger.info("Endpoint called: findById");
        return this.service.findById(id);
    }


    //pobiera po nazwie
    @GetMapping("capybara/get/name/{name}")
    public Optional<Capybara> getByName(@PathVariable("name") String name) {
        logger.info("Endpoint called: getByName");
        return this.service.findByName(name);

    }
//pobiera wszystko
    @GetMapping("capybara/get/all")
    public List<Capybara> getAll() {
        logger.info("Endpoint called: getAll");
        return this.service.findAll();

    }
//dodaje
    @PostMapping ("capybara/add")
    public ResponseEntity<Capybara> add(@RequestBody Capybara body) throws URISyntaxException {
        logger.info("Endpoint called: add");
        Capybara capybara =this.service.add(body);
        return ResponseEntity.created(new URI("capybara/get/"+ capybara.getId())).body(capybara);
    }
//updejtuje
    @PutMapping("capybara/update")
    public Capybara update(@RequestBody Capybara body) {
        logger.info("Endpoint called: update");

            return this.service.updateCapybara(body);
    }
//usuwa po id
    @DeleteMapping("capybara/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        logger.info("Endpoint called: delete");
        this.service.deleteById(id);

    }
//    @GetMapping("capybara/wyszukaj")
//    public List<String> wyszukajEncje(@RequestParam String slowo) {
//        return service.findAllByFraza(slowo);
//    }

}
