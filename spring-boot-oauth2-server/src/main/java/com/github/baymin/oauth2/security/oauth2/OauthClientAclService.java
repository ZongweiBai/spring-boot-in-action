package com.github.baymin.oauth2.security.oauth2;

import com.github.baymin.oauth2.entity.OauthClientAcl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.oauth2.common.util.DefaultJdbcListFactory;
import org.springframework.security.oauth2.common.util.JdbcListFactory;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * oauth_client_acl表的业务类
 */
@Slf4j
public class OauthClientAclService {

    private static final String DEFAULT_FIND_STATEMENT = "select client_id, allow_list, block_list, daily_max_access from oauth_client_acl where client_id = :client_id";
    private RowMapper<OauthClientAcl> rowMapper = new OauthClientAclService.UserClientAclRowMapper();
    private JdbcListFactory listFactory;
    private JdbcTemplate jdbcTemplate;
    private String updateClientAclSql;
    private String insertClientAclSql;
    private String deleteClientAclSql = "delete from oauth_client_acl where client_id = ?";

    private OauthClientAclService() {
    }

    public OauthClientAclService(DataSource dataSource) {
        this.updateClientAclSql = "update oauth_client_acl set " + "daily_max_access, allow_list, block_list".replaceAll(", ", "=?, ") + "=? where client_id = ?";
        this.insertClientAclSql = "insert into oauth_client_acl (client_id, daily_max_access, allow_list, block_list) values (?,?,?,?)";
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    public List<OauthClientAcl> queryClientAcl(String clientId) {
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("client_id", clientId);
        return this.listFactory.getList(DEFAULT_FIND_STATEMENT, queryMap, this.rowMapper);
    }

    public void addClientAcl(OauthClientAcl oauthClientAcl) {
        try {
            this.jdbcTemplate.update(this.insertClientAclSql, this.getFields(oauthClientAcl));
        } catch (DuplicateKeyException e) {
            throw new ClientAlreadyExistsException("Client Acl already exists: " + oauthClientAcl.getClientId(), e);
        }
    }

    public void removeClientAcl(String clientId) {
        this.jdbcTemplate.update(this.deleteClientAclSql, clientId);
    }

    public void updateClientAcl(OauthClientAcl oauthClientAcl) throws NoSuchClientException {
        int count = this.jdbcTemplate.update(this.updateClientAclSql, this.getFieldsForUpdate(oauthClientAcl));
        if (count != 1) {
            throw new NoSuchClientException("No Client Acl found with id = " + oauthClientAcl.getClientId());
        }
    }

    private Object[] getFields(OauthClientAcl oauthClientAcl) {
        return new Object[]{oauthClientAcl.getClientId(), oauthClientAcl.getDailyMaxAccess(),
                oauthClientAcl.getAllowList() != null ? StringUtils.collectionToCommaDelimitedString(oauthClientAcl.getAllowList()) : null,
                oauthClientAcl.getBlockList() != null ? StringUtils.collectionToCommaDelimitedString(oauthClientAcl.getBlockList()) : null};
    }

    private Object[] getFieldsForUpdate(OauthClientAcl oauthClientAcl) {
        return new Object[]{oauthClientAcl.getDailyMaxAccess(),
                oauthClientAcl.getAllowList() != null ? StringUtils.collectionToCommaDelimitedString(oauthClientAcl.getAllowList()) : null,
                oauthClientAcl.getBlockList() != null ? StringUtils.collectionToCommaDelimitedString(oauthClientAcl.getBlockList()) : null,
                oauthClientAcl.getClientId()};
    }

    @Slf4j
    private static class UserClientAclRowMapper implements RowMapper<OauthClientAcl> {

        @Override
        public OauthClientAcl mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            OauthClientAcl clientAcl = new OauthClientAcl();
            clientAcl.setClientId(resultSet.getString("client_id"));
            clientAcl.setDailyMaxAccess(resultSet.getInt("daily_max_access"));
            String allowList = resultSet.getString("allow_list");
            if (!StringUtils.isEmpty(allowList)) {
                clientAcl.setAllowList(StringUtils.commaDelimitedListToSet(allowList));
            } else {
                clientAcl.setAllowList(new HashSet<>());
            }
            String blockList = resultSet.getString("block_list");
            if (!StringUtils.isEmpty(blockList)) {
                clientAcl.setBlockList(StringUtils.commaDelimitedListToSet(blockList));
            } else {
                clientAcl.setAllowList(new HashSet<>());
            }
            return clientAcl;
        }
    }

}
