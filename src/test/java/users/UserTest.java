package users;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import users.POJO.Users;
import utils.AssertionsUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class UserTest extends UsersAPI {
    // (+) POSITIVE, (-) NEGATIVE, (*) EDGE TEST CASES

    private String id; // for PUT and DELETE purposes

    @Test(priority = 1, testName = "(+) TC01 - Retrieve all Users")
    public void getListOfUsersTest(){
        Response resp = getListOfUsers("1"); // 1 = POSITIVE , 0 = NEGATIVE
        resp.then().assertThat().statusCode(200);
    }

    @Test(priority = 2, testName = "(+) TC02 - Retrieve User by ID")
    public void getUserByIdTest(){
        Response resp = getSingleUser(1);
        resp.then()
            .assertThat()
            .statusCode(200)
            .body("email", equalTo("john@mail.com"))
            .body("name", equalTo("Jhon"))
            .body("password", equalTo("changeme"));
    }

    @Test(priority = 3, testName = "(-) TC03 - Invalid endpoint")
    public void getInvalidEndPointTest(){
        Response resp = getListOfUsers("0"); // 1 = POSITIVE , 0 = NEGATIVE
        resp.then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Not Found"))
                .body("message", equalTo("Cannot GET /api/v1/user"));
    }

    @Test(priority = 4, testName = "(-) TC04 - Get User by Invalid ID (Negative value)")
    public void getUserByInvalidIdTest(){
        Response resp = getSingleUser(-1);
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("EntityNotFoundError"));
    }

    @Test(priority = 5, testName = "(*) TC05 - Get User with ID 0")
    public void getUserBy0IdTest(){
        Response resp = getSingleUser(0);
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("EntityNotFoundError"));
    }

    @Test(priority = 6, testName = "(+) TC06 - Create a new User with valid data.")
    public void creteNewUserTest() throws FileNotFoundException {
        Map<String, Object> payLoad = Payload.getCreateUserPayload(
                "mahendraqa27@gmail.com",
                "test123",
                "Mahendra",
                "admin",
                "https://picsum.photos/2708"
        );
        Response resp = createUser(payLoad);
        resp.then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("Mahendra"))
                .body("role", equalTo("admin"))
                .body("email", equalTo("mahendraqa27@gmail.com"));

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, payLoad);
    }

    @Test(priority = 7, testName = "(+) TC07 - Create a new User with random data.")
    public void creteNewUserWithRandomDataTest() throws FileNotFoundException {
        Users request = Payload.getCreateUserPayload();
        Map<String, Object> expectedValueMap = new HashMap<>();
        expectedValueMap.put("email", request.getEmail());
        expectedValueMap.put("password", request.getPassword());
        expectedValueMap.put("name", request.getName());
        expectedValueMap.put("role", request.getRole());
        expectedValueMap.put("avatar", request.getAvatar());
        Response resp = createUser(expectedValueMap);
        String stringResp = resp.then()
                                .assertThat()
                                .statusCode(201).extract().response().asString(); // Take id

        JsonPath js = new JsonPath(stringResp);
        id = js.getString("id");
        String name = js.getString("name");
        System.out.println("Created user with random data ID is: " + id);
        System.out.println("Created user with random data name is: " + name);

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);
    }

    @Test(priority = 8, testName = "(-) TC08 - Create a User with an invalid email format")
    public void creteNewUserWithInvalidEmailFormatTest() throws FileNotFoundException {
        Map<String, Object> expectedValueMap = Payload.getCreateUserPayload(
                "<invalid-email>",
                "securePassword123",
                "John",
                "customer",
                "https://picsum.photos/2708"
        );
        Response resp = createUser(expectedValueMap);
        resp.then()
                .assertThat()
                .statusCode(400);

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);
    }

    @Test(priority = 9, testName = "(*) TC09 - Create a User with an empty JSON object")
    public void creteNewUserWithEmptyJSONObjectTest() throws FileNotFoundException {
        Map<String, Object> expectedValueMap = Payload.getCreateUserPayload(
                "",
                "",
                "",
                "",
                ""
        );
        Response resp = createUser(expectedValueMap);
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Bad Request"));

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);
    }

    @Test(priority = 10, testName = "(+) TC10 - Update Users' Information")
    public void updateUserInformationTest(){
        Map<String, Object> expectedValueMap = Payload.getUpdateUserPayload(
                "memphiscue12@mail.com",
                "MemphisChild update"
        );
        Response resp = updateUser(Integer.parseInt(id), expectedValueMap);
        resp.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("MemphisChild update"))
                .body("email", equalTo("memphiscue12@mail.com"));

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);
    }

    @Test(priority = 11, testName = "(-) TC11 - Update User's information with a non-existent ID.")
    public void updateUserInformationWithNonExistentIdTest(){
        Map<String, Object> expectedValueMap = Payload.getUpdateUserPayload(
                "non.existent@example.com",
                "EntityNotFoundError"
        );
        Response resp = updateUser(-1, expectedValueMap);
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("EntityNotFoundError"))
                .body("path", equalTo("/api/v1/users/-1"));

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);

    }

    @Test(priority = 12, testName = "(*) TC12 - Updata User information with empty fields.")
    public void updateUserInformationWithEmptyFieldTest(){
        Map<String, Object> expectedValueMap = Payload.getUpdateUserPayload(
                "",
                ""
        );
        Response resp = updateUser(Integer.parseInt(id) - 1, expectedValueMap); // take previous created user data id
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("Bad Request"));

        AssertionsUtils.assertExpectedValuesWithJsonPath(resp, expectedValueMap);
    }

    @Test(priority = 13, testName = "(+) TC13 - Delete an existing user with a valid ID.")
    public void deleteExistingUserTest(){
        Response resp = deleteCreatedUser(Integer.parseInt(id));
        resp.then()
                .assertThat()
                .statusCode(200);
    }

    @Test(priority = 14, testName = "(-) TC14 - Delete a User with a negative ID.")
    public void deleteUserWithNegativeIdTest(){
        Response resp = deleteCreatedUser(-1);
        resp.then()
                .assertThat()
                .statusCode(400)
                .body("name", equalTo("EntityNotFoundError"))
                .body("path", equalTo("/api/v1/users/-1"));
    }

    @Test(priority = 15, testName = "(*) TC15 - Unauthorized Deletion")
    public void unauthorizedDeletionTest(){
        Response resp = deleteCreatedUser(1);
        resp.then()
                .assertThat()
                .statusCode(401)
                .body("error", equalTo("Unauthorized"))
                .body("message", equalTo("This user is not available for deleting; instead, create your own user to delete."));
    }


}
