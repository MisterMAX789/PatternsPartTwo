import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLoginPositive() {
        var registeredUser = DataGenerator.RegistrationDto.Registration.registerUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $(".heading").shouldBe(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldShowErrorNegative() {
        var notRegisteredUser = DataGenerator.RegistrationDto.Registration.generateUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка" +
                        " Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowErrorLogin() {
        var registeredUser = DataGenerator.RegistrationDto.Registration.registerUser("active");
        var wrongLogin = DataGenerator.RegistrationDto.generateLogin();
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка" +
                        " Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowErrorPassword() {
        var registeredUser = DataGenerator.RegistrationDto.Registration.registerUser("active");
        var wrongPassword = DataGenerator.RegistrationDto.generatePassword();
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка" +
                        " Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldShowBlockNegative() {
        var blockedUser = DataGenerator.RegistrationDto.Registration.registerUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка" +
                        " Ошибка! Пользователь заблокирован"));
    }
}