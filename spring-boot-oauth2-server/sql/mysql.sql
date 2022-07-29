CREATE TABLE oauth_client_details (
  client_id   varchar(48) NOT NULL ,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope text DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information text DEFAULT NULL,
  autoapprove varchar(256) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

-- 初始化client，clientId:oauth_client, clientSecret:oauth_client_9527
Insert into oauth_client_details (CLIENT_ID,RESOURCE_IDS,CLIENT_SECRET,SCOPE,AUTHORIZED_GRANT_TYPES,WEB_SERVER_REDIRECT_URI,AUTHORITIES,ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,ADDITIONAL_INFORMATION,AUTOAPPROVE) values ('oauth_client',null,'$2a$10$BzHyuqFxz3NECG3FAcgPKO2ZLpA/E6XiM9pfMnZ1hHVeipl9N1NTu','all,read,write','refresh_token,password,authorization_code','http://www.baidu.com',null,3600,-1,'{"location":"ALL"}',null);
commit;

-- 自定义oauth2的权限表
CREATE TABLE oauth_user_authority (
  authority_id int NOT NULL,
  user_id   varchar(32) NOT NULL ,
  authority_type varchar(32) NOT NULL,
  authority text NOT NULL,
  PRIMARY KEY (authority_id)
);

-- oauth_client_details的扩展，定义信任IP、访问频率等
CREATE TABLE oauth_client_acl (
  client_id   varchar(48) NOT NULL ,
  allow_list  varchar(1024),
  block_list  varchar(1024),
  daily_max_access int,
  PRIMARY KEY (client_id)
)