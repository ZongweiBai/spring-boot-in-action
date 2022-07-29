package com.github.baymin.oauth2.api;

import com.github.baymin.oauth2.entity.OauthClientAcl;
import com.github.baymin.oauth2.security.oauth2.OauthClientAclService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * client acl管理类
 */
@Slf4j
@RestController
public class OauthClientAclApi {

    @Autowired
    private OauthClientAclService clientAclService;

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    /**
     * 创建acl
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/oauth/acl")
    public ResponseEntity<Object> createAcl(@RequestBody OauthClientAcl clientAcl) {
        if (Objects.isNull(clientDetailsService.loadClientByClientId(clientAcl.getClientId()))) {
            Map<String, Object> resultMap = ImmutableMap.of("error", "not_found", "error_description", "未找到client_id的记录");
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }

        if (CollectionUtils.isEmpty(clientAclService.queryClientAcl(clientAcl.getClientId()))) {
            clientAclService.addClientAcl(clientAcl);
        } else {
            this.updateClient(clientAcl.getClientId(), clientAcl);
        }
        return new ResponseEntity<>(clientAcl, HttpStatus.OK);
    }

    /**
     * 删除acl
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/oauth/acl/{clientId}")
    public void deleteOneAcl(@PathVariable String clientId) {
        clientAclService.removeClientAcl(clientId);
    }

    /**
     * 更新oauth_client
     */
    @PutMapping(value = "/oauth/acl/{clientId}")
    public OauthClientAcl updateClient(@PathVariable String clientId,
                                       @RequestBody OauthClientAcl clientAcl) {
        clientAcl.setClientId(clientId);
        clientAclService.updateClientAcl(clientAcl);
        return clientAcl;
    }

    /**
     * 根据ID查询client
     */
    @GetMapping(value = "/oauth/acl/{clientId}")
    public ResponseEntity<Object> fetchOneAcl(@PathVariable String clientId) {
        List<OauthClientAcl> clientAcls = clientAclService.queryClientAcl(clientId);
        if (Objects.isNull(clientAcls)) {
            Map<String, Object> resultMap = ImmutableMap.of("error", "not_found", "error_description", "未找到记录");
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientAcls.get(0), HttpStatus.OK);
    }

}
