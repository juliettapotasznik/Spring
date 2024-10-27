package pl.edu.pjatk.PrzykladWyklad;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.edu.pjatk.PrzykladWyklad.controller.CapybaraController;
import pl.edu.pjatk.PrzykladWyklad.model.Capybara;
import pl.edu.pjatk.PrzykladWyklad.repository.CapybaraRepository;
import pl.edu.pjatk.PrzykladWyklad.service.CapybaraService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CapybaraControllerTest {
    @InjectMocks
    private CapybaraController capybaraController;
    @Mock
    private CapybaraService capybaraService;
    private AutoCloseable openMock;


    @BeforeEach
    public void init()
    {
        openMock= MockitoAnnotations.openMocks(this);
        capybaraController= new CapybaraController(capybaraService);

    }
    @AfterEach
    public void tearDown() throws Exception{
        openMock.close();
    }
    @Test
    public void testFindById() {

        Long capybaraId = 1L;
        Capybara capybara = new Capybara();


        when(capybaraService.findById(capybaraId)).thenReturn(Optional.of(capybara));


        Optional<Capybara> result = capybaraController.findById(capybaraId);


        Mockito.verify(capybaraService, times(1)).findById(capybaraId);
        assertTrue(result.isPresent());
        assertEquals(capybara, result.get());
    }
    @Test
    public void testFindByIdNotFound() {

        Long capybaraId = 2L;


        when(capybaraService.findById(capybaraId)).thenReturn(Optional.empty());

        Optional<Capybara> result = capybaraController.findById(capybaraId);


        Mockito.verify(capybaraService, times(1)).findById(capybaraId);
        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAll() {

        Capybara capybara1 = new Capybara();
        Capybara capybara2 = new Capybara();
        List<Capybara> capybaraList = Arrays.asList(capybara1, capybara2);


        when(capybaraService.findAll()).thenReturn(capybaraList);


        List<Capybara> result = capybaraController.getAll();


        Mockito.verify(capybaraService, times(1)).findAll();
        assertFalse(result.isEmpty());
        assertEquals(capybaraList, result);
    }

    @Test
    public void testGetAllEmptyList() {

        when(capybaraService.findAll()).thenReturn(Collections.emptyList());


        List<Capybara> result = capybaraController.getAll();


        Mockito.verify(capybaraService, times(1)).findAll();
        assertTrue(result.isEmpty());
    }
    @Test
    public void testGetByName() {

        String capybaraName = "Capybara1";
        Capybara capybara = new Capybara();


        when(capybaraService.findByName(capybaraName)).thenReturn(Optional.of(capybara));


        Optional<Capybara> result = capybaraController.getByName(capybaraName);

        // Assertions
        Mockito.verify(capybaraService, times(1)).findByName(capybaraName);
        assertTrue(result.isPresent());
        assertEquals(capybara, result.get());
    }

    @Test
    public void testGetByNameNotFound() {

        String capybaraName = "NonExistentCapybara";


        when(capybaraService.findByName(capybaraName)).thenReturn(Optional.empty());


        Optional<Capybara> result = capybaraController.getByName(capybaraName);


        Mockito.verify(capybaraService, times(1)).findByName(capybaraName);


        assertNotNull(result, "The result should not be null; it should be Optional.empty()");
        assertFalse(result.isPresent(), "The result should not be present for a non-existent capybara");

    }
    @Test
    public void testAdd() throws URISyntaxException {

        Capybara requestBody = new Capybara();
        Capybara addedCapybara = new Capybara();
        addedCapybara.setId(1L);


        when(capybaraService.add(requestBody)).thenReturn(addedCapybara);


        ResponseEntity<Capybara> responseEntity = capybaraController.add(requestBody);


        Mockito.verify(capybaraService, times(1)).add(requestBody);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(addedCapybara, responseEntity.getBody());


        assertTrue(responseEntity.getHeaders().containsKey("Location"));
        assertEquals(new URI("capybara/get/" + addedCapybara.getId()), responseEntity.getHeaders().getLocation());
    }
    @Test
    public void testUpdateExistingCapybara() {

        Long capybaraId = 1L;
        Capybara requestBody = new Capybara();
        requestBody.setId(capybaraId);


        Capybara updatedCapybara = new Capybara();
        when(capybaraService.updateCapybara(requestBody)).thenReturn(updatedCapybara);


        Capybara response = capybaraController.update(requestBody);


        Mockito.verify(capybaraService, times(1)).updateCapybara(requestBody);


        assertNotNull(response, "The updated capybara should not be null");
        assertEquals(updatedCapybara, response, "The updated capybara returned should match the expected capybara object");
    }

    @Test
    public void testUpdateNonExistingCapybara() {
        Long capybaraId = 2L;
        Capybara requestBody = new Capybara();
        requestBody.setId(capybaraId);

        when(capybaraService.updateCapybara(requestBody)).thenReturn(null);

        Capybara response = capybaraController.update(requestBody);

        Mockito.verify(capybaraService, times(1)).updateCapybara(requestBody);

        assertNull(response, "The response should be null for non-existing capybara");

    }
    @Test
    public void testDeleteExistingCapybara() {

        Long capybaraId = 1L;


        capybaraController.delete(capybaraId);


        Mockito.verify(capybaraService, times(1)).deleteById(capybaraId);
    }

    @Test
    public void testDeleteNonExistingCapybara() {

        Long capybaraId = 2L;

        capybaraController.delete(capybaraId);

        Mockito.verify(capybaraService, times(1)).deleteById(capybaraId);
    }

//    @Test
//    public void testWyszukajEncje()  {
//        String searchFraza = "Jul";
//        List<String> expectedResult = Arrays.asList("Julietta", "Julia");
//        when(capybaraService.findAllByFraza(searchFraza)).thenReturn(expectedResult);
//        capybaraController.wyszukajEncje(searchFraza);
//        Mockito.verify(capybaraService).findAllByFraza(searchFraza);
//
//    }
}

