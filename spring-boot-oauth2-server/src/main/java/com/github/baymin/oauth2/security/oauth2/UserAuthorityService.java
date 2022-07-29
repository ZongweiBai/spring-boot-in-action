package com.github.baymin.oauth2.security.oauth2;

import com.github.baymin.oauth2.entity.UserAuthority;
import com.github.baymin.oauth2.security.UserGrantedAuthority;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

/**
 * oauth_user_authority表的业务类
 *
 * @author Zongwei
 * @date 2019/9/30 14:01
 */
@Slf4j
public class UserAuthorityService {

    private static final String DEFAULT_FIND_STATEMENT = "select authority_id, user_id, authority_type, authority from oauth_user_authority where user_id = :user_id";
    private RowMapper<UserAuthority> rowMapper = new UserAuthorityService.UserAuthorityRowMapper();
    private JdbcListFactory listFactory;
    private final JdbcTemplate jdbcTemplate;
    private String updateUserAuthoritySql;
    private String insertUserAuthoritySql;
    private String deleteUserAuthoritySql = "delete from oauth_client_details where user_id = ?";
    private String deleteUserAuthorityWithTypeSql = "delete from oauth_client_details where user_id = ? and authority_type = ?";

    public UserAuthorityService(DataSource dataSource) {
        this.updateUserAuthoritySql = "update oauth_client_details set " + "user_id, authority_type, authority".replaceAll(", ", "=?, ") + "=? where authority_id = ?";
        this.insertUserAuthoritySql = "insert into oauth_user_authority (authority_id, user_id, authority_type, authority) values (?,?,?,?)";
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.listFactory = new DefaultJdbcListFactory(new NamedParameterJdbcTemplate(jdbcTemplate));
    }

    public List<UserAuthority> queryUserAuthority(String userId) {
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("user_id", userId);
        return this.listFactory.getList(DEFAULT_FIND_STATEMENT, queryMap, this.rowMapper);
    }

    public Set<UserGrantedAuthority> getUserGrantedAuthorities(String userId) {
        List<UserAuthority> userAuthorities = this.queryUserAuthority(userId);

        Set<UserGrantedAuthority> grantedAuthorities = new HashSet<>();
        if (CollectionUtils.isNotEmpty(userAuthorities)) {
            for (UserAuthority userAuthority : userAuthorities) {
                for (String authority : userAuthority.getAuthority()) {
                    UserGrantedAuthority grantedAuthority = new UserGrantedAuthority(authority);
                    grantedAuthorities.add(grantedAuthority);
                }
            }
        } else {
            // 如果用户未配置权限，设置默认权限
            grantedAuthorities.add(new UserGrantedAuthority("ROLE_USER"));
            grantedAuthorities.add(new UserGrantedAuthority("API_ALL"));
            grantedAuthorities.add(new UserGrantedAuthority("TABLE_ALL"));
        }
        return grantedAuthorities;
    }

    public void addUserAuthority(UserAuthority userAuthority) {
        try {
            this.jdbcTemplate.update(this.insertUserAuthoritySql, this.getFields(userAuthority));
        } catch (DuplicateKeyException e) {
            throw new ClientAlreadyExistsException("Authority already exists: " + userAuthority.getUserId(), e);
        }
    }

    public void removeUserAuthority(String userId, String authorityType) {
        if (StringUtils.isEmpty(authorityType)) {
            this.jdbcTemplate.update(this.deleteUserAuthoritySql, new Object[]{userId});
        } else {
            this.jdbcTemplate.update(this.deleteUserAuthorityWithTypeSql, new Object[]{userId, authorityType});
        }
    }

    public void updateUserAuthority(UserAuthority userAuthority) throws NoSuchClientException {
        int count = this.jdbcTemplate.update(this.updateUserAuthoritySql, this.getFieldsForUpdate(userAuthority));
        if (count != 1) {
            throw new NoSuchClientException("No user auhtority found with id = " + userAuthority.getAuthorityId());
        }
    }

    private Object[] getFields(UserAuthority userAuthority) {
        return new Object[]{userAuthority.getAuthorityId(), userAuthority.getUserId(), userAuthority.getAuthorityType(), userAuthority.getAuthority() != null ? StringUtils.collectionToCommaDelimitedString(userAuthority.getAuthority()) : null};
    }

    private Object[] getFieldsForUpdate(UserAuthority userAuthority) {
        return new Object[]{userAuthority.getUserId(), userAuthority.getAuthorityType(), userAuthority.getAuthority() != null ? StringUtils.collectionToCommaDelimitedString(userAuthority.getAuthority()) : null, userAuthority.getAuthorityId()};
    }

    @Slf4j
    private static class UserAuthorityRowMapper implements RowMapper<UserAuthority> {

        @Override
        public UserAuthority mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UserAuthority details = new UserAuthority();
            details.setAuthorityId(resultSet.getLong("authority_id"));
            details.setUserId(resultSet.getString("user_id"));
            details.setAuthorityType(resultSet.getString("authority_type"));
            String authority = resultSet.getString("authority");
            if (!StringUtils.isEmpty(authority)) {
                details.setAuthority(StringUtils.commaDelimitedListToSet(authority));
            }
            return details;
        }
    }

}
