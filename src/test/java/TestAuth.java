import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestAuth {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginPositive() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").val(registeredUser.getLogin());
        $("[data-test-id='password'] input").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(text("Личный кабинет"));

    }

    @Test
    void shouldShowErrorLoginNegative() {
        var notRegisteredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").val(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").val(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowBlockNegative() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").val(blockedUser.getLogin());
        $("[data-test-id='password'] input").val(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldShowWrongLoginNegative() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] input").val(wrongLogin);
        $("[data-test-id='password'] input").val(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowWrongPasswordNegative() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] input").val(registeredUser.getLogin());
        $("[data-test-id='password'] input").val(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }
}