package pl.edu.pjatk.PrzykladWyklad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraAlreadyExistException;
import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraNotFoundException;
import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraWrongAgeException;
import pl.edu.pjatk.PrzykladWyklad.model.Capybara;
import pl.edu.pjatk.PrzykladWyklad.repository.CapybaraRepository;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CapybaraService {
    CapybaraRepository repository;

    @Autowired
    public CapybaraService(CapybaraRepository repository) {
        this.repository = repository;

    }

    public Optional<Capybara> findByName(String name) {
        Optional<Capybara> repoCapybara = Optional.ofNullable(this.repository.findByName(name));
        if (repoCapybara.isPresent()) {
            return repoCapybara;
        } else {
            throw new CapybaraNotFoundException();
        }

    }

    public List<Capybara> findAll() {
        return (List<Capybara>) this.repository.findAll();
    }

    public Optional<Capybara> findById(Long id) {
        Optional<Capybara> repoCapybara = this.repository.findById(id);
        if (repoCapybara.isPresent()) {
            return repoCapybara;
        } else {
            throw new CapybaraNotFoundException();
        }
    }

    public void deleteById(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        } else {
            throw new CapybaraNotFoundException();
        }
    }

    public boolean ifExistById(Long id) {
        return this.repository.existsById(id);
    }

    public Capybara add(Capybara capybara) {

        if (this.repository.findById(capybara.getId()).isPresent()) {
            throw new CapybaraAlreadyExistException();
        }


        if (capybara.getAge() <= 0) {
            throw new CapybaraWrongAgeException();
        }


        return this.repository.save(capybara);
    }


    public void save(Capybara capybara) {
        this.repository.save(capybara);

    }
    public Capybara updateCapybara(Capybara capybara) {
        Optional<Capybara> repoCapybara=this.repository.findById(capybara.getId());
        if(repoCapybara.isPresent())
        {
            if(capybara.getAge()>0)
            {
                return this.repository.save(capybara);

            }
            else {
                throw new CapybaraWrongAgeException();
            }
        }
        else {
            throw new CapybaraNotFoundException();
        }

    }
//    public List<String> findAllByFraza (String fraza )
//    {
//        return findAll()
//                .stream()
//                .map(Capybara::getName)
//                .filter(name -> name.contains(fraza))
//                .collect(Collectors.toList());
//    }

}

