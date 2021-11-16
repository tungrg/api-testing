package ExperimentTest.ReqresAPI;


import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class Reqres4Test extends ReqresBaseTest {
    @Test
    public void getSingleUserSuccessful(){
        String endpoint = "https://reqres.in/api/users/"+ Integer.toString(new Faker().number().numberBetween(1,12));
        ValidatableResponse response = given()
                .when()
                .get(endpoint)
                .then();
        response.assertThat().statusCode(200);
    }
    @Test
    public void getSingleUserSuccessfulID(){
        int idInt = new Faker().number().numberBetween(1,12);
        String endpoint = "https://reqres.in/api/users/" + Integer.toString(idInt);
        ValidatableResponse response = given().when().get(endpoint).then();
        response.assertThat().statusCode(200).log().all().body("data.id",equalTo(new Integer (idInt)));
    }
    @Test
    public void getSingleUserNoneExist(){
        String endpoint = "https://reqres.in/api/users/"+ Integer.toString(new Faker().number().numberBetween(13,23));
        ValidatableResponse response = given().when().get(endpoint).then();
        response.assertThat().statusCode(404);
    }
    @Test
    public void getListUser(){

        String endpoint = "https://reqres.in/api/users?page=2";
        ValidatableResponse response = given().when().get(endpoint).then();
        response.assertThat();
        response.assertThat().statusCode(200)
                .log().all()
                .body("page", equalTo(2))
                .body("per_page", equalTo(6));
    }
    @Test
    public void createUser(){
        String endpoint = "https://reqres.in/api/users";
        String body =
                """
                {
                        "name": "morpheus",
                        "job": "leader"
                }
                """;
        ValidatableResponse response =
                given().body(body).when()
                .post(endpoint).then();
        response.log().all().assertThat().statusCode(201);
    }
    @Test
    public void updateUser(){
        String endpoint = "https://reqres.in/api/users/2";
        String body =
                """
                {
                        "name": "morpheus",
                        "job": "zion resident"
                }
                """;
        ValidatableResponse response =
                given()
                        .header("Content-Type","application/json")
                        .body(body).when()
                        .put(endpoint).then();
        response.log().all().assertThat().statusCode(200).body("name",equalTo("morpheus"));
//        RequestSpecBuilder spec = new RequestSpecBuilder();
//        spec
//                .setBaseUri(endpoint)
//                .addParam("name", "morpheus")
//                .addParam("job", "zion resident");
//        Response rp = RestAssured.given(spec.build()).put();
//        rp.then().log().all();
    }

}
