# Spring-Boot-In-Action

A collection of samples of SpringBoot-based modules that can be run independently.    

## Modules

[spring-boot-webflux-file-server](#spring-boot-webflux-file-server)

[spring-boot-admin-server](#spring-boot-admin-server)

[spring-boot-api-gateway](#spring-boot-api-gateway)

[spring-boot-oauth2-Server](#spring-boot-oauth2-Server)

# spring-boot-webflux-file-server

> File server created based on springboot / spring webflux / MongoDB

# spring-boot-admin-server

> Admin UI for Spring boot Applications.

# spring-boot-api-gateway
> API gateway DEMO Project created based on spring-cloud-gateway.

# spring-boot-oauth2-Server

> SpringBoot OAuth2 Server based on spring-cloud-oauth2 and MySQL.

## 授权模式

### 密码模式

```
POST：basic token
http://localhost:8093/oauth/token?grant_type=password&username=徐春华&password=0&scope=all
```

### 客户端模式

```
POST: basic token
http://localhost:8093/oauth/token?grant_type=client_credentials&scope=all
```

### 授权码模式

```
1、获取授权码
GET
http://localhost:8093/oauth/authorize?client_id=merryyou&response_type=code&scope=all&redirect_uri=http://www.baidu.com
2、根据授权码获取token
POST
http://localhost:8093/oauth/token?client_id=merryyou&grant_type=authorization_code&code=BVJYYF&redirect_uri=http://www.baidu.com&scope=all&client_secret=merryyou
```

### 撤销Token

```
DELETE：bearer token
http://localhost:8093/oauth/token/revoke?token_type=accessToken&token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njk1NzgxNDUsImJsb2ciOiJodHRwczovL2xvbmdmZWl6aGVuZy5naXRodWIuaW8vIiwidXNlcl9uYW1lIjoiYWRtaW4wMDAxIiwianRpIjoiMTA5OThiMGQtMTY3Ni00Y2NlLWJkNzctMTU1ODk1NGM4NWE3IiwiY2xpZW50X2lkIjoibWVycnl5b3UiLCJzY29wZSI6WyJhbGwiXX0.j1s-ZrT1YFb1y9HYe5gH3dUqUHp_vyKwWX9GB1gZA2U
```

### 校验Token

```
GET：basic token
http://localhost:8093/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiLlvpDmmKXljY4iLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNTcwNTA0OTE2LCJ0YWJsZV9hdXRob3JpdHkiOlsiVEFCTEUxIiwiVEFCTEUyIl0sImF1dGhvcml0aWVzIjpbIlRBQkxFMSIsIlRBQkxFMiJdLCJqdGkiOiJiM2FkY2IxOS00NzczLTRmYWItYmUzMC04MTAxYjgxZDZkYTYiLCJjbGllbnRfaWQiOiJtZXJyeXlvdSJ9.iEfNpLt9OB4QnX7CjoY06owXXklpnVDRhFXI2HTHUr8
```

### 刷新Token

```
POST：basic token
http://localhost:8093/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiI5OWFkbWluOSIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiJiNGQyZTYzMy0xMjMzLTQ4NDEtOTQyOS04YTA2ZjQ0YTRjNGUiLCJleHAiOjE1Njk5MzIxMzAsImJsb2ciOiJodHRwczovL2xvbmdmZWl6aGVuZy5naXRodWIuaW8vIiwianRpIjoiYmFhMDUyZTktNjE1NS00Nzk2LTllNzAtMzUwN2E2MzE3NjZiIiwiY2xpZW50X2lkIjoibWVycnl5b3UifQ.cZEFc_hqjeq4r1jbfG5jhWgUcX_7JZ0D41-uTjVf2cI&scope=all
```

## 其他API

### 获取用户信息

```
GET：bearer token
http://localhost:8093/user/profile
```

### Client管理

```
bearer token
http://localhost:8093/oauth/client/
```

### 用户权限管理

```
bearer token
http://localhost:8093/user/authority/
```

