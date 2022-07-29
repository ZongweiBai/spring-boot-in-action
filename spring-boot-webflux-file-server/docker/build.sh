#!/bin/bash

# 删除本地的旧版本镜像
sudo docker rmi file-server:1.0.0
# 根据Dockerfile生成新的镜像
sudo docker build -t file-server:1.0.0 .
# 推送镜像到仓库
# docker push imagesregistry-test:5000/file-server:1.0
# 删除k8s旧的部署
# kubectl delete -f file-server-deployment.yaml
# 使用k8s部署
# kubectl create -f file-server-deployment.yaml --record
# Docker部署
mkdir -p /home/zongwei/app_data/file_server/data
mkdir -p /home/zongwei/app_data/file_server/log

# 停止并删除旧的部署
sudo docker stop file_server
sudo docker rm file_server

sudo docker run --name file_server --restart unless-stopped -p 8899:8080 -v /home/zongwei/app_data/file_server/data:/usr/local/modules/File-Server/data -v /home/zongwei/app_data/file_server/log:/usr/local/modules/File-Server/log -v /etc/timezone:/etc/timezone -v /etc/localtime:/etc/localtime -d file-server:1.0.0
