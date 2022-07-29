package com.github.baymin.sse.handler;

import com.github.baymin.sse.payload.NotifyInfo;
import com.github.baymin.sse.payload.UserBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserBaseHandler {

    @Autowired
    private NamedParameterJdbcTemplate memNamedJdbcTemplate;

    /**
     * 创建用户信息
     *
     * @param userId       用户ID
     * @param userBaseInfo 用户基本信息
     */
    public void createUserInfo(String userId, UserBaseInfo userBaseInfo) {
        userBaseInfo.setUserId(userId);
        String updateSql = "insert into T_USER_BASE_INFO values(:userId, :bizObjId, :userName, :location, :unitName)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("bizObjId", userBaseInfo.getBizObjId());
        paramMap.put("userName", userBaseInfo.getUserName());
        paramMap.put("location", userBaseInfo.getLocation());
        paramMap.put("unitName", userBaseInfo.getUnitName());
        memNamedJdbcTemplate.update(updateSql, paramMap);
        log.debug("执行SQL：{}", updateSql);
    }

    /**
     * 根据通知信息过滤要推送的用户
     *
     * @param notifyInfo 通知信息
     * @return 需要推送的用户ID
     */
    public List<String> fetchUserIds(NotifyInfo notifyInfo) {
        StringBuilder queryBuilder = new StringBuilder("select USER_ID from T_USER_BASE_INFO where 1=1");
        Map<String, Object> queryMap = new HashMap<>();

        if (!StringUtils.isEmpty(notifyInfo.getBizObjId())) {
            queryBuilder.append(" and BIZ_OBJ_ID = :bizObjId");
            queryMap.put("bizObjId", notifyInfo.getBizObjId());
        }

        if (!StringUtils.isEmpty(notifyInfo.getLocation())) {
            queryBuilder.append(" and LOCATION = :location");
            queryMap.put("location", notifyInfo.getLocation());
        }

        if (!StringUtils.isEmpty(notifyInfo.getUnitName())) {
            queryBuilder.append(" and UNIT_NAME = :unitName");
            queryMap.put("unitName", notifyInfo.getUnitName());
        }
        log.debug("执行SQL：{}", queryBuilder.toString());
        return memNamedJdbcTemplate.queryForList(queryBuilder.toString(), queryMap, String.class);
    }

    /**
     * 根据用户ID从数据库移除信息
     *
     * @param userId 用户ID
     */
    public void removeByUserId(String userId) {
        String updateSql = "delete from T_USER_BASE_INFO where USER_ID = :userId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        memNamedJdbcTemplate.update(updateSql, paramMap);
        log.debug("执行SQL：{}", updateSql);
    }
}
