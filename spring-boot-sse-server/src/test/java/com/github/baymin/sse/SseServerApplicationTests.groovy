package com.github.baymin.sse

import io.restassured.RestAssured
import lombok.extern.slf4j.Slf4j
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.event.annotation.BeforeTestClass
import org.springframework.test.context.junit.jupiter.SpringExtension

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SseServerApplication.class)
@ActiveProfiles("test")
class SseServerApplicationTests {

    @LocalServerPort
    int port

    @BeforeTestClass
    static void beforeTestClass() {

    }

    void initRestAssured(String basePath) {
        RestAssured.baseURI = "http://127.0.0.1/"
        RestAssured.port = port
        RestAssured.basePath = basePath
    }

}
