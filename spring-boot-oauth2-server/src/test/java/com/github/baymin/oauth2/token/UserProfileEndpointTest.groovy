package com.github.baymin.oauth2.token

import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

/**
 * 测试用户基本信息的获取
 * @author Zongwei* @date 2020/5/14 18:39
 */
class UserProfileEndpointTest extends OAuth2ServerAbstractTest {

    /**
     * 根据access_token获取用户的信息
     */
    @Test
    void testGetUserProfile() {
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
                .get('/user/profile')
                .then().log().all()
                .statusCode(200)
                .body('user_name', Matchers.equalTo('张三'))
    }

}
