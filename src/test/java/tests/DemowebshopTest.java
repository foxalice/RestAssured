package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import models.RegisterUserRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class DemowebshopTest {

    String getCookieAuth() {
        RegisterUserRequestModel data = new RegisterUserRequestModel();
        data.setEmail("qw@hhh.com");
        data.setPassword("111111");
        data.setRememberMe("true");
        return given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("https://demowebshop.tricentis.com/login")
                .then()
                .extract().cookie("NOPCOMMERCE.AUTH").toString();
    }


    @Tag("api")
    @Test
    @DisplayName("Проверка количества в заказе по UI")
    void checkCountByUI() {
        open("https://demowebshop.tricentis.com/books");
        Cookie ck = new Cookie("NOPCOMMERCE.AUTH", getCookieAuth());
        WebDriverRunner.getWebDriver().manage().addCookie(ck);
        Selenide.refresh();
        $("#cart-qty").shouldHave(text("(5)"));

    }


}
