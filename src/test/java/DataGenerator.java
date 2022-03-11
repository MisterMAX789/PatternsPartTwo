import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;

        private static void sendRequest(RegistrationDto user) {
            //Запрос
            given() //"дано"
                    .spec(requestSpec)
                    .body(new RegistrationDto(
                            user.getLogin(),
                            user.getPassword(),
                            user.getStatus()))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String generateLogin() {
            return faker.name().firstName();
        }

        public static String generatePassword() {
            return faker.internet().password();
        }

        public static class Registration {
            private Registration() {
            }

            public static RegistrationDto generateUser(String status) {
                return new RegistrationDto(generateLogin(), generatePassword(), status);
            }

            public static RegistrationDto registerUser(String status) {
                RegistrationDto registerUser = generateUser(status);
                sendRequest(registerUser);
                return registerUser;
            }
        }
    }
}