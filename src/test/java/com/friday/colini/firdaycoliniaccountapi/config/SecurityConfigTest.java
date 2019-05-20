package com.friday.colini.firdaycoliniaccountapi.config;

import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityConfigTest {

    @Autowired
    AppProperties appProperties;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    @TestDescription("login_page_이동한다")
    public void login_page() throws Exception {
        given()
                .when()
                .get("/session")
                .then()
                .statusCode(200)
                .contentType("text/html")
                .body(containsString("Login page"));
    }

    @Test
    public void google로그인_시도하면_OAuth인증창_등장한다() throws Exception {
        given()
                .when()
                .redirects().follow(false) // 리다이렉트 방지
                .get("/oauth2/authorize/google")
                .then()
                .statusCode(302)
                .log().all()
                .header("Location", containsString("https://accounts.google.com/o/oauth2/v2/auth"));
    }

    @Test
    @TestDescription("외부 리소스에서 토큰 요청")
    public void when_another_resources_request_token() {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", appProperties.getUserId());
        params.put("password", appProperties.getUserPassword());

        given()
                .auth()
                .preemptive()
                .basic(appProperties.getClientId(), appProperties.getClientSecret())
                .and()
                .with().params(params)
                .when()
                .post("http://localhost:8080/oauth/token")
                .then()
                .log().all()

        ;
    }


}