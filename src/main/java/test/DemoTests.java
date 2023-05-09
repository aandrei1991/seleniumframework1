package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.DemoUtils;

import static io.restassured.RestAssured.given;

public class DemoTests {
    private final static String requestBody = "{\n" +
            "  \"brand\": \"Mercedes\",\n" +
            "  \"model\": \"E Class\",\n" +
            "  \"engine\": \"4.0\", \n" +
            "  \"color\": \"black\" \n}";

    @Rule
    public TestName name = new TestName();
    public static SoftAssertions softly = new SoftAssertions();

    @Before
    public void initBeforeTest() {
        //driver.get("https://www.emag.ro");
        RestAssured.baseURI = "http://192.168.0.126:8080";
    }

    @After
    public void clean() {
        softly.assertAll();
        //driver.close();
    }

    @Test
    public void testSumProcess() {
        softly.assertThat(DemoUtils.sumProcess(7, 2))
                .describedAs("THE SUM RESULT IS NOT CORRECT")
                .isEqualTo(9);
    }
    @Test
    public void postRequest() {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/api/cars")
                .then()
                .extract().response();

        softly.assertThat(201)
                .describedAs("WRONG STATUS CODE")
                .isEqualTo(response.statusCode());

        System.out.println("POST REQUEST SUCCESSFULLY DONE!"
                + "\nRESPONSE CODE = " + response.statusCode()
                + "\nBODY: \n" + requestBody);

    }

    @Test
    public void getRequest() {

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/cars/3")
                .then()
                .extract().response();

        softly.assertThat(200)
                .describedAs("WRONG STATUS CODE")
                .isEqualTo(response.statusCode());

        softly.assertThat(response.jsonPath().getString("brand"))
                .describedAs("WRONG BRAND REGISTRATION")
                .isEqualTo("Mercedes");

        softly.assertThat(response.jsonPath().getString("color"))
                .describedAs("WRONG COLOR REGISTRATION")
                .isEqualTo("grey out");

        System.out.println("GET REQUEST SUCCESSFULLY DONE!"
                + "\nRESPONSE CODE = " + response.statusCode()
                + "\nBODY: \n" + response.jsonPath().get());

    }
    @Test
    public void putRequest() {
        String updateColor = "{\"color\": \"red\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(updateColor)
                .when()
                .put("/api/cars/2")
                .then()
                .extract().response();

        softly.assertThat(200)
                .describedAs("WRONG STATUS CODE")
                .isEqualTo(response.statusCode());

    }
    @Test
    public void deleteRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/cars/1")
                .then()
                .extract().response();

        softly.assertThat(204)
                .describedAs("WRONG STATUS CODE")
                .isEqualTo(response.statusCode());
    }
}
