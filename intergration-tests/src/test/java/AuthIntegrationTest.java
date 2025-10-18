import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setUp(){
        RestAssured.baseURI="http://localhost:4004/";
    }

    @Test
    public void shouldReturnOKWithValidToken(){
        //1. Arrange
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "password123"
                }
                """;
        //2. Act
        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
        //3. Assert
                .statusCode(200)
                .body("token",notNullValue())
                .extract()
                .response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInValidLogin(){
        //1. Arrange
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "wrongPassword"
                }
                """;
        //2. Act
       given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                //3. Assert
                .statusCode(401);
    }

}
