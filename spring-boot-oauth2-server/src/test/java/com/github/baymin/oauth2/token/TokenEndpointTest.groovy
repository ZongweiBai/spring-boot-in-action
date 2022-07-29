package com.github.baymin.oauth2.token

import groovy.util.logging.Slf4j
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

/**
 * 测试token的获取、刷新和撤销
 * @author Zongwei* @date 2020/5/14 17:16
 */
@Slf4j
class TokenEndpointTest extends OAuth2ServerAbstractTest {

    /**
     * 密码模式获取token
     */
    @Test
    void testGetTokenWithPassword() {
        def queryParams = ['grant_type': 'password', 'username': '张三', 'password': '123456', 'scope': 'all']

        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.notNullValue())
                .body('token_type', Matchers.equalTo('bearer'))
    }

    /**
     * 客户端模式获取token
     */
    @Test
    void testGetTokenWithClientCredentials() {
        def queryParams = ['grant_type': 'client_credentials', 'scope': 'all']

        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.nullValue())
                .body('token_type', Matchers.equalTo('bearer'))
    }

    /**
     * 校验token
     */
    @Test
    void testGetTokenMoreThanOnce() {
        def queryParams = ['grant_type': 'client_credentials', 'scope': 'all']

        def accessToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.nullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('access_token')

        log.debug("获取到的accessToken是：{}，再次开始获取token", accessToken)

        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('access_token', Matchers.equalTo(accessToken))
                .body('refresh_token', Matchers.nullValue())
                .body('token_type', Matchers.equalTo('bearer'))
    }

    /**
     * 校验token
     */
    @Test
    void testCheckToken() {
        def queryParams = ['grant_type': 'client_credentials', 'scope': 'all']

        def accessToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.nullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('access_token')

        log.debug("获取到的accessToken是：{}，开始校验token", accessToken)

        queryParams = ['token': accessToken]
        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/check_token')
                .then().log().all()
                .statusCode(200)
                .body('active', Matchers.equalTo(true))
                .body('client_id', Matchers.equalTo('oauth_client'))
    }

    /**
     * 刷新token
     */
    @Test
    void testRefreshToken() {
        def queryParams = ['grant_type': 'password', 'username': '张三', 'password': '123456', 'scope': 'all']

        def refreshToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.notNullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('refresh_token')

        log.debug("获取到的refreshToken是：{}，开始刷新token", refreshToken)

        queryParams = ['grant_type': 'refresh_token', 'refresh_token': refreshToken, 'scope': 'all']
        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.equalTo(refreshToken))
                .body('token_type', Matchers.equalTo('bearer'))
    }

    /**
     * 撤销token
     */
    @Test
    void testRevokeToken() {
        def queryParams = ['grant_type': 'client_credentials', 'scope': 'all']

        def accessToken = RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/token')
                .then().log().all()
                .statusCode(200)
                .body('access_token', Matchers.notNullValue())
                .body('refresh_token', Matchers.nullValue())
                .body('token_type', Matchers.equalTo('bearer'))
                .extract().path('access_token')

        log.debug("获取到的accessToken是：{}，开始撤销token", accessToken)

        queryParams = ['token': accessToken, 'token_type': 'accessToken']
        RestAssured.given().contentType('application/json')
                .auth().oauth2(accessToken.toString())
                .queryParams(queryParams).log().all()
                .delete('/oauth/token/revoke')
                .then().log().all()
                .statusCode(204)

        log.debug("撤销token成功，校验token是否已被撤销")
        queryParams = ['token': accessToken]
        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .post('/oauth/check_token')
                .then().log().all()
                .statusCode(400)
                .body('error', Matchers.equalTo('invalid_token'))
    }

    /**
     * 刷新token
     */
    @Test
    void testGetTokenKey() {
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

        log.debug("获取到的accessToken是：{}，开始获取token key", accessToken)

        queryParams = ['token': accessToken]
        RestAssured.given().contentType('application/json')
                .auth().basic('oauth_client', 'oauth_client_9527')
                .queryParams(queryParams).log().all()
                .get('/oauth/token_key')
                .then().log().all()
                .statusCode(200)
                .body('alg', Matchers.equalTo('HMACSHA256'))
    }

}
