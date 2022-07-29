#!/bin/bash

# 删除本地的旧版本镜像
sudo docker rmi boot-admin-server:1.0.0
# 根据Dockerfile生成新的镜像
sudo docker build -t boot-admin-server:1.0.0 .
# 推送镜像到仓库
# docker push imagesregistry-test:5000/boot-admin-server:1.0
# 删除k8s旧的部署
# kubectl delete -f boot-admin-server-deployment.yaml
# 使用k8s部署
# kubectl create -f boot-admin-server-deployment.yaml --record
# Docker部署
mkdir -p /home/zongwei/app_data/boot_admin_server/data
mkdir -p /home/zongwei/app_data/boot_admin_server/log

# 停止并删除旧的部署
sudo docker stop boot_admin_server
sudo docker rm boot_admin_server

sudo docker run --name boot_admin_server --restart unless-stopped -p 10110:10110 -v /home/zongwei/app_data/boot_admin_server/data:/usr/local/modules/Boot-Admin-Server/data -v /home/zongwei/app_data/boot_admin_server/log:/usr/local/modules/Boot-Admin-Server/log -v /etc/timezone:/etc/timezone -v /etc/localtime:/etc/localtime -d boot-admin-server:1.0.0
