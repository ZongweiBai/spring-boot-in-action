package com.github.baymin.oauth2.token

import groovy.sql.Sql
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import javax.sql.DataSource

/**
 * OAuth2 Client的测试
 * @author Zongwei* @date 2020/5/14 18:45
 */
class OAuthClientEndpointTest extends OAuth2ServerAbstractTest {

    @Autowired
    @Qualifier('oauth2DataSource')
    DataSource dataSource;

    /**
     * 获取所有的client信息
     */
    @Test
    void testGetAllClientInfo() {
        def queryParams = ['grant_type': 'password', 'username': '张三', 'password': '123456', 'scope': 'all']

        def accessToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.notNullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('access_token')

        RestAssured.given().contentType('application/json')
                .auth().oauth2(accessToken)
                .queryParams(queryParams).log().all()
                .get('/oauth/client/')
                .then().log().all()
                .statusCode(200)
                .body(Matchers.notNullValue())
    }

    /**
     * 新增client信息
     */
    @Test
    void testCreateClientInfo() {
        def queryParams = ['grant_type': 'password', 'username': '张三', 'password': '123456', 'scope': 'all']

        def accessToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.notNullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('access_token')


        // 先删除可能存在的测试数据
        def sql = new Sql(dataSource)
        sql.execute("delete from OAUTH_CLIENT_DETAILS where CLIENT_ID='client_test'")

        // 通过接口进行新增
        def bodyContent = '''
        {
            "scope": [
                "read",
                "write",
                "all"
            ],
            "client_id": "client_test",
            "client_secret": "client_test_9527",
            "authorized_grant_types": [
                "refresh_token",
                "password",
                "authorization_code",
                "client_credentials"
            ],
            "redirect_uri": [
                "http://www.baidu.com"
            ],
            "access_token_validity": 3600,
            "refresh_token_validity": -1
        }
        '''

        RestAssured.given().contentType('application/json')
                .auth().oauth2(accessToken)
                .body(bodyContent).log().all()
                .post('/oauth/client/')
                .then().log().all()
                .statusCode(201)

        // 验证是否新增成功
        assert sql.firstRow("select count(*) from OAUTH_CLIENT_DETAILS where CLIENT_ID='client_test'").size() >= 1
        sql.execute("delete from OAUTH_CLIENT_DETAILS where CLIENT_ID='client_test'")
        sql.close()
    }

}
