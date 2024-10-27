package pl.edu.pjatk.PrzykladWyklad;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.PrzykladWyklad.model.Capybara;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;


public class RestAssuredTest {

    private static final String URI="http://localhost:8080";

    @Test
    public void testGetCapybara(){

        when()
                .get(URI+"/capybara/get/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("name",equalTo("Julietta"))
                .body("age",equalTo(2))
                .log()
                .body();
    }
    @Test
    public void testGetCapybara2(){
        Capybara capybara=
                when()
                .get(URI+"/capybara/get/1")
                .then()
                .statusCode(200)
                        .extract()
                        .as(Capybara.class);
        assertEquals(1,capybara.getId());
        assertEquals("Julietta",capybara.getName());
        assertEquals(2,capybara.getAge());
    }
    @Test
    public void testGetAllCapybaras(){
        List<Capybara> capybaras=
                when()
                .get(URI+"/capybara/get/all")
                .then()
                .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("$", Capybara.class);
        assertTrue(capybaras.size() == 4); //czy sa 4 obiekty




    }
    @Test
    public void testPostCapybara()
    {
        System.out.println("Rozpoczynam testPostCapybara");
        with()
                .body(new Capybara("Cap",3))
                .contentType("application/json")
                .when()
                .post(URI+"/capybara/add")
                .then()
                .statusCode(201)
                .body("id",greaterThan(4))
                .body("name",equalTo("Cap"))
                .body("age",equalTo(3))
                .log()
                .body();
        System.out.println("Zakończono testPostCapybara");
    }
    @Test
    public void testPutCapybara()
    {
        String requestBody = "{\"id\": 1,\"name\": \"Julietta\", \"age\": 2}";
        Response response= RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/capybara/update");
        response.then()
                .statusCode(200);



    }
    @Test
    public void testDeleteCapybara()
    {
      when()
               .delete(URI+"/capybara/delete/1")
               .then()
              .statusCode(200);

    }


}
