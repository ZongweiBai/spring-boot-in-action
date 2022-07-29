package com.github.baymin.oauth2.api;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * client管理类
 *
 * @author Zongwei
 * @date 2019/9/30 9:24
 */
@Slf4j
@RestController
public class OauthClientApi {

    @Autowired
    private JdbcClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建oauth_client
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/oauth/client")
    public BaseClientDetails createClient(@RequestBody BaseClientDetails clientDetails) {
        clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        clientDetailsService.addClientDetails(clientDetails);
        clientDetails.setClientSecret(null);
        return clientDetails;
    }

    /**
     * 删除oauth_client的secret
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/oauth/client/{clientId}")
    public void deleteOneClient(@PathVariable String clientId) {
        clientDetailsService.removeClientDetails(clientId);
    }

    /**
     * 更新oauth_client
     */
    @PutMapping(value = "/oauth/client/{clientId}")
    public BaseClientDetails updateClient(@PathVariable String clientId,
                                          @RequestBody BaseClientDetails clientDetails) {
        clientDetails.setClientId(clientId);
        clientDetailsService.updateClientDetails(clientDetails);
        clientDetails.setClientSecret(null);
        return clientDetails;
    }

    /**
     * 更新oauth_client的secret
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/oauth/client/{clientId}")
    public void patchClient(@PathVariable String clientId,
                            @RequestParam("client_secret") String clientSecret) {
        clientDetailsService.updateClientSecret(clientId, clientSecret);
    }

    /**
     * 根据ID查询client
     */
    @GetMapping(value = "/oauth/client/{clientId}")
    public ResponseEntity<Object> fetchOneClient(@PathVariable String clientId) {
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (Objects.isNull(clientDetails)) {
            Map<String, Object> resultMap = ImmutableMap.of("error", "not_found", "error_description", "未找到记录");
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }
        BaseClientDetails baseClientDetails = (BaseClientDetails) clientDetails;
        baseClientDetails.setClientSecret(null);
        return new ResponseEntity<>(baseClientDetails, HttpStatus.OK);
    }

    /**
     * 查询所有的client
     */
    @GetMapping(value = "/oauth/client")
    public ResponseEntity<Object> fetchAllClient() {
        List<ClientDetails> clientDetails = clientDetailsService.listClientDetails();
        if (CollectionUtils.isEmpty(clientDetails)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        List<BaseClientDetails> baseClientDetails = new ArrayList<>(clientDetails.size());
        for (ClientDetails clientDetail : clientDetails) {
            BaseClientDetails baseClient = (BaseClientDetails) clientDetail;
            baseClient.setClientSecret(null);
            baseClientDetails.add(baseClient);
        }
        return new ResponseEntity<>(baseClientDetails, HttpStatus.OK);
    }

}
