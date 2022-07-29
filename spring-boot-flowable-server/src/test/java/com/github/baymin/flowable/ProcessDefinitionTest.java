package com.github.baymin.flowable;

import org.apache.commons.io.FileUtils;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程定义的CRUD
 *
 * @author BaiZongwei
 * @date 2021/8/10 9:42
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessDefinitionTest {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署流程定义
     */
    @Test
    public void deployProcessDefinition() {

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署相关的Service
                .createDeployment()//创建部署对象
                .addClasspathResource("diagrams/myHelloWorld.bpmn")
                .addClasspathResource("diagrams/myHelloWorld.png")
                .deploy();//完成部署

        System.out.println("部署ID：" + deployment.getId());//部署ID:1
        System.out.println("部署时间：" + deployment.getDeploymentTime());//部署时间
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {

        String processDefinitionKey = "myMyHelloWorld";//流程定义的key,也就是bpmn中存在的ID

        ProcessInstance pi = processEngine.getRuntimeService()//管理流程实例和执行对象，也就是表示正在执行的操作
                .startProcessInstanceByKey(processDefinitionKey); //按照流程定义的key启动流程实例

        System.out.println("流程实例ID：" + pi.getId());//流程实例ID：101
        System.out.println("流程实例ID：" + pi.getProcessInstanceId());//流程实例ID：101
        System.out.println("流程实例ID:" + pi.getProcessDefinitionId());//myMyHelloWorld:1:4
    }

    /**
     * 查看当前任务办理人的个人任务
     */
    @Test
    public void findPersonnelTaskList() {
        String assignee = "users";//当前任务办理人
        List<Task> tasks = processEngine.getTaskService()//与任务相关的Service
                .createTaskQuery()//创建一个任务查询对象
//                .taskAssignee(assignee)
                .taskCandidateGroup("managers")
                .list();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("任务拥有者:" + task.getOwner());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务ID:" + task.getId());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("#####################################");
            }
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        String taskID = "32513";
        processEngine.getTaskService().complete(taskID);
        System.out.println("完成任务：" + taskID);
    }

    /**
     * 查询流程定义
     * 流程定义和部署对象相关的Service都是RepositoryService，创建流程定义查询对象，可以在
     * ProcessDefinitionQuery上设置查询的相关参数，调用ProcessDefinitionQuery对象的list方法，执行查询，获得符
     * 合条件的流程定义列表。
     */
    @Test
    public void findProcessDifinitionList() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                // 查询条件
                .processDefinitionKey("application-upcart-flow")// 按照流程定义的key
                // .processDefinitionId("helloworld")//按照流程定义的ID
                .orderByProcessDefinitionVersion().desc()// 排序
                // 返回结果
                // .singleResult()//返回惟一结果集
                // .count()//返回结果集数量
                // .listPage(firstResult, maxResults)
                .list();// 多个结果集

        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义的ID：" + pd.getId());
                System.out.println("流程定义的名称：" + pd.getName());
                System.out.println("流程定义的Key：" + pd.getKey());
                System.out.println("流程定义的部署ID：" + pd.getDeploymentId());
                System.out.println("流程定义的资源名称：" + pd.getResourceName());
                System.out.println("流程定义的版本：" + pd.getVersion());
                System.out.println("########################################################");
            }
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDifinition() {
        List<String> deploymentIds = List.of("30001", "27501", "25001", "22501", "20001", "17501", "15001");
        for (String deploymentId : deploymentIds) {
            processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                    .deleteDeployment(deploymentId, true);

            System.out.println("删除成功~~~");//使用部署ID删除流程定义,true表示级联删除
        }
    }

    /**
     * 查看流程定义的资源文件
     */
    @Test
    public void viewPng() throws IOException {
        //部署ID
        String deploymentId = "32501";
        //获取的资源名称
        List<String> list = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);
        //获得资源名称后缀.png
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                if (name.indexOf(".png") >= 0) {//返回包含该字符串的第一个字母的索引位置
                    resourceName = name;
                }
            }
        }

        //获取输入流，输入流中存放.PNG的文件
        InputStream in = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, resourceName);

        //将获取到的文件保存到本地
        FileUtils.copyInputStreamToFile(in, new File("D:/" + resourceName));

        System.out.println("文件保存成功！");
    }
}
