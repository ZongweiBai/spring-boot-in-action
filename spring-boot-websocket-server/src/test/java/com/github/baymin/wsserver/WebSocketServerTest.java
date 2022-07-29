package com.github.baymin.wsserver;

import org.java_websocket.enums.ReadyState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;
import java.util.concurrent.CopyOnWriteArrayList;

@ExtendWith(SpringExtension.class)
@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WsServerApplication.class)
public class WebSocketServerTest {

    @LocalServerPort
    public int randomPort;

    /**
     * test case passed in JDK1.8
     *
     * @throws InterruptedException
     */
    @Test
    public void testConnectWsServer() throws InterruptedException {
        CopyOnWriteArrayList<WebSocketClient4j> client4js = new CopyOnWriteArrayList<>();

//        CountDownLatch countDownLatch = new CountDownLatch(10000);
//
//        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
//            int finalI = i;
//            executor.submit(new Runnable() {
//                @Override
//                public void run() {
            try {
                client4js.add(createWsConnection(i + 11));
            } catch (URISyntaxException e) {
                System.out.println(i + "--连接出现错误");
            } /*finally {
                        countDownLatch.countDown();
                    }*/
//                }
//            });
        }

//        countDownLatch.await();
        System.out.println("总的连接数：" + client4js.size());
        for (WebSocketClient4j client4j : client4js) {
            client4j.close();
        }
        System.out.println("全部释放");
    }

    private WebSocketClient4j createWsConnection(int num) throws URISyntaxException {
        WebSocketClient4j client = new WebSocketClient4j("ws://127.0.0.1:8080/boot-ws/" + num);
        client.connect();
        while (!client.getReadyState().equals(ReadyState.OPEN)) {
//            System.out.println("还没有打开");
        }
//        System.out.println(num + "--建立websocket连接");
        client.send(num + "--hello world!");
        return client;
    }

}
