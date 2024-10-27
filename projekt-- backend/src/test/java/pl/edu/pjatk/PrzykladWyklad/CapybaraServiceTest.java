package pl.edu.pjatk.PrzykladWyklad;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjatk.PrzykladWyklad.controller.CapybaraController;
import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraAlreadyExistException;
import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraNotFoundException;
import pl.edu.pjatk.PrzykladWyklad.exception.CapybaraWrongAgeException;
import pl.edu.pjatk.PrzykladWyklad.model.Capybara;
import pl.edu.pjatk.PrzykladWyklad.repository.CapybaraRepository;
import pl.edu.pjatk.PrzykladWyklad.service.CapybaraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@SpringBootTest
public class CapybaraServiceTest {
    @Mock
    private CapybaraRepository capybaraRepository;
    private AutoCloseable openMock;
    @InjectMocks
    private CapybaraService capybaraService;
    @BeforeEach
    public void init()
    {
        openMock= MockitoAnnotations.openMocks(this);
        capybaraService=new CapybaraService(capybaraRepository);

    }
    @AfterEach
    public void tearDown() throws Exception{
        openMock.close();
    }
    @Test
    public void testFindByName()
    {
        String name = "Julietta";
        Capybara expectedCapybara = new Capybara(name, 2);

        Mockito.when(capybaraRepository.findByName(name)).thenReturn(expectedCapybara);

        Optional<Capybara> result = capybaraService.findByName(name);

        assertEquals(Optional.of(expectedCapybara), result);
    }
    @Test
    public void testFindAll()
    {
        Capybara capybara1=new Capybara("Julietta",2);
        Capybara capybara2=new Capybara("Marcysia",8);
        List<Capybara> expectedCapybaras = List.of(new Capybara[]{capybara2, capybara1});

        Mockito.when(capybaraRepository.findAll()).thenReturn(expectedCapybaras);

        List<Capybara> result = capybaraService.findAll();

        assertEquals(expectedCapybaras, result);

    }
    @Test
    public void testFindById() {
        Long id = 1L;
        Capybara expectedCapybara = new Capybara("Test Capybara", 3);

        Mockito.when(capybaraRepository.findById(id)).thenReturn(Optional.of(expectedCapybara));

        Optional<Capybara> result = capybaraService.findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedCapybara, result.get());
    }
    @Test
    public void testDeleteById() {
        Long id = 1L;

        Mockito.when(capybaraRepository.existsById(id)).thenReturn(true);

        capybaraService.deleteById(id);

        Mockito.verify(capybaraRepository).deleteById(id);
    }
    @Test
    public void testDeleteById_EntityNotFound() {
        Long id = 1L;

        Mockito.when(capybaraRepository.existsById(id)).thenReturn(false);

        assertThrows(CapybaraNotFoundException.class, () -> capybaraService.deleteById(id));

        Mockito.verify(capybaraRepository, never()).deleteById(id);
    }

    @Test
    public void testIfExistById_Exists() {
        Long id = 1L;
        Mockito.when(capybaraRepository.existsById(id)).thenReturn(true);
        boolean exists = capybaraService.ifExistById(id);
        assertTrue(exists);
    }
    @Test
    public void testIfExistById_NotExists() {

        Long id = 1L;
        Mockito.when(capybaraRepository.existsById(id)).thenReturn(false);

        boolean exists = capybaraService.ifExistById(id);

        assertFalse(exists);
    }
    @Test
    public void testAddNewCapybara() {

        Capybara capybara = new Capybara();
        capybara.setId(1L);
        capybara.setAge(5);

        Mockito.when(capybaraRepository.findById(capybara.getId())).thenReturn(Optional.empty());
        Mockito.when(capybaraRepository.save(capybara)).thenReturn(capybara);

        Capybara addedCapybara = capybaraService.add(capybara);

        Mockito.verify(capybaraRepository, times(1)).findById(capybara.getId());
        Mockito.verify(capybaraRepository, times(1)).save(capybara);

        assertNotNull(addedCapybara, "The added capybara should not be null");
        assertEquals(capybara, addedCapybara, "The added capybara should match the expected capybara object");
    }

    @Test
    public void testAddExistingCapybara() {

        Capybara capybara = new Capybara();
        capybara.setId(1L);


        Mockito.when(capybaraRepository.findById(capybara.getId())).thenReturn(Optional.of(capybara));


        assertThrows(CapybaraAlreadyExistException.class, () -> capybaraService.add(capybara));


        Mockito.verify(capybaraRepository, times(1)).findById(capybara.getId());


        Mockito.verify(capybaraRepository, never()).save(capybara);
    }
    @Test
    public void testUpdateCapybaraWrongAge() {

        Capybara existingCapybara = new Capybara();
        existingCapybara.setId(1L);
        existingCapybara.setAge(4);

        Capybara updatedCapybara = new Capybara();
        updatedCapybara.setId(1L);
        updatedCapybara.setAge(-1);

        Mockito.when(capybaraRepository.findById(existingCapybara.getId())).thenReturn(Optional.of(existingCapybara));

        assertThrows(CapybaraWrongAgeException.class, () -> capybaraService.updateCapybara(updatedCapybara));

        Mockito.verify(capybaraRepository, times(1)).findById(existingCapybara.getId());
        Mockito.verify(capybaraRepository, never()).save(updatedCapybara);
    }

    @Test
    public void testUpdateCapybaraNotFound() {

        Capybara updatedCapybara = new Capybara();
        updatedCapybara.setId(1L);
        updatedCapybara.setAge(4);


        Mockito.when(capybaraRepository.findById(updatedCapybara.getId())).thenReturn(Optional.empty());


        assertThrows(CapybaraNotFoundException.class, () -> capybaraService.updateCapybara(updatedCapybara));


        Mockito.verify(capybaraRepository, times(1)).findById(updatedCapybara.getId());


        Mockito.verify(capybaraRepository, never()).save(updatedCapybara);
    }

    @Test
    public void testSave() {
        Capybara capybaraToSave = new Capybara("New Capybara", 4);
        capybaraService.save(capybaraToSave);
        Mockito.verify(capybaraRepository).save(capybaraToSave);
    }
//    @Test
//    public void testWyszukajEncje(){
//        String fraza="Jul";
//        List<String> expectedCapybaras = List.of("Julietta","Julia");
//
//        Mockito.when(capybaraRepository.findAll()).thenReturn(expectedCapybaras);
//
//        List<String> result = capybaraService.findAllByFraza(fraza);
//
//        assertEquals(expectedCapybaras, result);
//
//    }
}


