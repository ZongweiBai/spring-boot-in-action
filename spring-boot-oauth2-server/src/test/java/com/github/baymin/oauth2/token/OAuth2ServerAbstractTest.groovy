package com.github.baymin.oauth2.token

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * OAuth2 Server单元测试基类
 * @author Zongwei
 * @date 2020/5/14 17:13
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class OAuth2ServerAbstractTest {

    @LocalServerPort
    int port;

    @BeforeTestClass
    static void beforeClass() throws Exception {
    }

    @BeforeEach
    void beforeMethod() {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
        RestAssured.basePath = "/oauth2-server";
    }

}
