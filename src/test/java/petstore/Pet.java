package petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test (priority = 0)
    public void incluirPet_EValida_name_status_tagId() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
                .body("status", is("available"))
                .body("tags.id", contains(2021))//lista usar contains
        ;
    }

    @Test (priority = 2)
    public void consultarPet_EValida_name_status_idName(){
        String petId = "9223372000666115959";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri+"/"+petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("doggie"))
                .body("status", is("available"))
                .body("tags.id", contains(0))//lista usar contains
        ;

    }

    @Test (priority = 3)
    public void alterarPetEnviandoJson2_EValida_StatusSold() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Snoopy"))
                .body("status", is("sold"))
         ;

    }

}
