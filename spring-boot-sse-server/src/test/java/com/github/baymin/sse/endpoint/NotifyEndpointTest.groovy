package com.github.baymin.sse.endpoint

import com.github.baymin.sse.SseServerApplicationTests
import com.github.baymin.sse.client.SseClient
import io.restassured.RestAssured
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

import java.util.concurrent.atomic.AtomicInteger

/**
 * 通知的接口测试
 */
class NotifyEndpointTest extends SseServerApplicationTests {

    def static final userId = System.currentTimeMillis().toString()

    @DisplayName('测试消息通知')
    @Test
    void testMessageNotify() {
        def responseCount = new AtomicInteger(0)
        def connectUrl = "http://127.0.0.1:${port}/boot-sse/api/v1/channel/${userId}/connect"

        Thread.start('建立sse连接', {
            try {
                def client = new SseClient(new URL(connectUrl), new SseClient.EventListener() {
                    @Override
                    void message(SseClient.SseEvent evt) {
                        println("获取到的推送消息：${evt.data}")
                        if (!evt.data.contains('ping')) {
                            responseCount.incrementAndGet()
                        }
                    }
                })
                client.connect()
            } catch (EOFException e) {
                println('the stream ended!')
            }
        })

        // 主线程等待
        sleep(500L)

        // 存储用户额外信息
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

        // 发送消息
        def messageInfo = """
        {
            "biz_obj_id":"123456789",
            "location": "gz",
            "unit_name": "广州第一事务所",
            "target_type":"测试推送",
            "change_type":"测试"
        }
        """
        RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .body(messageInfo)
                .log().all()
                .post("http://127.0.0.1:${port}/boot-sse/api/v1/notify")
                .then()
                .log().all().statusCode(HttpStatus.NO_CONTENT.value())

        // 主线程等待
        sleep(3 * 1000L)
        assert responseCount.get() == 1

        // 断开连接，避免影响其他的单元测试
        RestAssured.given()
                .contentType(ContentType.APPLICATION_JSON.toString())
                .log().all()
                .delete("http://127.0.0.1:${port}/boot-sse/api/v1/channel/${userId}/close")
                .then()
                .log().all().statusCode(HttpStatus.NO_CONTENT.value())

        sleep(2 * 1000L)
    }
}