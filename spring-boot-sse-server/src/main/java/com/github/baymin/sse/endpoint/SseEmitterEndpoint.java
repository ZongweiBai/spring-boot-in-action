package com.github.baymin.sse.endpoint;

import com.github.baymin.sse.handler.SseEmitterHandler;
import com.github.baymin.sse.handler.UserBaseHandler;
import com.github.baymin.sse.payload.UserBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * SseEmitter接口服务，建立连接，推送消息
 *
 * @author Zongwei
 */
@RestController
@RequestMapping(path = "/api/v1/channel")
public class SseEmitterEndpoint {

    @Autowired
    private UserBaseHandler userBaseHandler;

    @Autowired
    private SseEmitterHandler sseEmitterHandler;

    /**
     * 创建连接
     *
     * @param userId 用户ID
     */
    @GetMapping("/{userId}/connect")
    public SseEmitter connect(@PathVariable String userId) {
        return sseEmitterHandler.connect(userId);
    }

    /**
     * 主动关闭连接
     *
     * @param userId 用户ID
     */
    @DeleteMapping("/{userId}/close")
    public ResponseEntity<HttpStatus> close(@PathVariable String userId) {
        sseEmitterHandler.close(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 查看当前服务器建立的连接
     */
    @GetMapping("/list")
    public ResponseEntity<List<String>> listAllConnections() {
        return ResponseEntity.ok(sseEmitterHandler.getIds());
    }

    /**
     * 统计当前服务器建立的连接总数
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getUserCount() {
        return ResponseEntity.ok(sseEmitterHandler.getUserCount());
    }

    /**
     * 补充UserId的信息，如归属地市等
     *
     * @param userBaseInfo 用户信息
     */
    @PostMapping("/{userId}/info")
    public ResponseEntity<HttpStatus> createUserInfo(@PathVariable(name = "userId") String userId,
                                                     @RequestBody UserBaseInfo userBaseInfo) {
        userBaseHandler.createUserInfo(userId, userBaseInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
