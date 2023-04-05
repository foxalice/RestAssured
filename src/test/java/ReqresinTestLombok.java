import models.CreateUserResponseModel;
import models.UpdateUserResponseModel;
import models.UserBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;

import static org.hamcrest.Matchers.*;
import static specs.Endpoints.*;
import static specs.Specs.*;

public class ReqresinTestLombok {

    @Tag("api")
    @DisplayName("Checking token and auth")
    @Test
    void loginTest() {
        UserBodyModel data = new UserBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");

        step("Verify token is not empty and auth success", () -> {
        given(baseRequestSpec)
                .body(data)
                .when()
                .post(LOGIN)
                .then()
                .spec(baseResponseSpecCode200)
                .body("token", not(empty()));
        });
    }

    @Tag("api")
    @DisplayName("Checking pagination per page")
    @Test
    void listTestPagination() {
        step("Verify total users per page", () -> {
            given(baseRequestSpec)
                    .when()
                    .get(LIST_USERS)
                    .then()
                    .spec(baseResponseSpecCode200)
                    .body("total", is(12));
        });
    }

    @Tag("api")
    @DisplayName("Checking user in list")
    @Test
    void listTestCheckUserInList() {
        step("Verify user in list", () -> {
        given(baseRequestSpec)
                .when()
                .get(LIST_USERS)
                .then()
                .spec(baseResponseSpecCode200)
                .body("data.id", hasItems(10, 12));
        });
    }

    @Tag("api")
    @DisplayName("Create new user and check name and job")
    @Test
    void createUser() {
        CreateUserResponseModel data = new CreateUserResponseModel();
        data.setName("morpheus");
        data.setJob("leader");
        CreateUserResponseModel response = step("Data entry", () ->
                given(baseRequestSpec)
                .body(data)
                .when()
                .post(USERS)
                .then()
                .spec(baseResponseSpecCode201)
                .extract().as(CreateUserResponseModel.class));

        step("Checking the place of work and name", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("leader");
    });
    }

    @Tag("api")
    @DisplayName("Delete user ")
    @Test
    void deleteUser() {
        step("Deleting a user", () -> {
            given(baseRequestSpec)
                .when()
                .delete(SINGLE_USER)
                .then()
                .spec(baseResponseSpecCode204);
        });
    }

    @Tag("api")
    @DisplayName("Update userdata")
    @Test
    void updateUserdata() {
        UpdateUserResponseModel data = new UpdateUserResponseModel();
        data.setName("morpheus");
        data.setJob("ext director");

        UpdateUserResponseModel response = step("Data entry", () ->
                given(baseRequestSpec)
                .body(data)
                .when()
                .put(SINGLE_USER)
                .then()
                .spec(baseResponseSpecCode200)
                .extract().as(UpdateUserResponseModel.class));

        step("Checking the name and job", () -> {
            assertThat(response.getName()).isEqualTo("morpheus");
            assertThat(response.getJob()).isEqualTo("ext director");
        });

    }
}
