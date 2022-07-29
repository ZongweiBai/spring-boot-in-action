package com.github.baymin.sse.endpoint

import com.github.baymin.sse.SseServerApplicationTests
import com.github.baymin.sse.client.SseClient
import groovy.sql.Sql
import io.restassured.RestAssured
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.web.client.RestTemplate

import javax.sql.DataSource
import java.util.concurrent.atomic.AtomicInteger

/**
 * sse emitter接口测试
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SseEmitterEndpointTest extends SseServerApplicationTests {

    def REST_TEMPLATE = new RestTemplate()
    def static final userId = System.currentTimeMillis().toString()

    @Autowired
    @Qualifier("memDataSource")
    private DataSource memDataSource

    @Autowired
    private NamedParameterJdbcTemplate memNamedJdbcTemplate;

    @DisplayName('测试建立sse连接')
    @Test
    @Order(1)
    void testConnect() {
        def responseCount = new AtomicInteger(0)
        def connectUrl = "http://127.0.0.1:${port}/boot-sse/api/v1/channel/${userId}/connect"

        Thread.start('建立sse连接', {
            try {
                def client = new SseClient(new URL(connectUrl), new SseClient.EventListener() {
                    @Override
                    void message(SseClient.SseEvent evt) {
                        println("获取到的推送消息：${evt.data}")
                        responseCount.incrementAndGet()
                    }
                })
                client.connect()
            } catch (EOFException e) {
                println('the stream ended!')
            }
        })

        // 主线程等待，等待结束后判断是否有响应
        sleep(3 * 1000L)
        assert responseCount > 0
    }

    @DisplayName('测试列出所有的sse连接')
    @Test
    @Order(2)
    void listAllConnections() {
        def list = RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .log().all()
                .get("http://127.0.0.1:${port}/boot-sse/api/v1/channel/list")
                .then()
                .log().all().statusCode(HttpStatus.OK.value())
                .extract().body().as(List.class)

        assert list.size() == 1
        assert list[0] == userId
    }

    @DisplayName('测试获得sse连接总数')
    @Test
    @Order(3)
    void testGetUserCount() {
        def count = RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .log().all()
                .get("http://127.0.0.1:${port}/boot-sse/api/v1/channel/count")
                .then()
                .log().all().statusCode(HttpStatus.OK.value())
                .extract().body().as(Integer.class)

        assert count == 1
    }

    @DisplayName('测试创建用户的额外信息')
    @Test
    @Order(4)
    void createUserInfo() {
        // 删除数据以免影响测试用例运行
        def sql = new Sql(memDataSource)
        sql.execute('delete from T_USER_BASE_INFO')

        def userBaseInfo = """
        {
            "user_id": "${userId}",
            "biz_obj_id":"123456789",
            "user_name": "张三",
            "location": "gz",
            "unit_name": "广州第一事务所"
        }
        """
        RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .body(userBaseInfo)
                .log().all()
                .post("http://127.0.0.1:${port}/boot-sse/api/v1/channel/${userId}/info")
                .then()
                .log().all().statusCode(HttpStatus.CREATED.value())

        def rows = sql.rows('select * from T_USER_BASE_INFO where USER_ID = :userId', userId: userId)
        println(rows)
        assert rows.size() == 1
    }

    @DisplayName('测试关闭sse连接')
    @Test
    @Order(5)
    void testClose() {
        RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .log().all()
                .delete("http://127.0.0.1:${port}/boot-sse/api/v1/channel/${userId}/close")
                .then()
                .log().all().statusCode(HttpStatus.NO_CONTENT.value())

        sleep(2 * 1000L)

        def sql = new Sql(memDataSource)
        def rows = sql.rows('select * from T_USER_BASE_INFO')
        assert rows.size() == 0
    }
}