package com.github.baymin.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 部署流程资源
 *
 * @author BaiZongwei
 * @date 2021/8/10 10:52
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeploymentProcessTest {

    /**
     * 数据库表变更
     * ACT_RE_DEPLOYMENT（部署对象表）   存放流程定义的显示名和部署时间，每部署一次增加一条记录
     * ACT_RE_PROCDEF（流程定义表）      存放流程定义的属性信息，部署每个新的流程定义都会在这张表中增加一条记录。
     * ACT_GE_BYTEARRAY（资源文件表）    存储流程定义相关的部署信息。即流程定义文档的存放地。每部署一次就会增加两条记录，
     *                                 一条是关于bpmn规则文件的，一条是图片的（如果部署时只指定了bpmn一个文件，
     *                                 activiti会在部署时解析bpmn文件内容自动生成流程图）。两个文件不是很大，都是以二进制形式存储在数据库中。
     * ACT_GE_PROPERTY（主键生成表）     将生成下次流程部署的主键ID。
     */

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 部署流程定义  classpath方式
     *
     * @author BaiZongwei
     * @date 2021/8/10 10:58
     */
    @Test
    public void deployementProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("helloworld演示")//声明流程的名称
                .addClasspathResource("diagrams/helloworld.bpmn")//加载资源文件，一次只能加载一个文件
                .addClasspathResource("diagrams/helloworld.png")//
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
    }

    /**
     * Inputstream方式
     * 使用InputStream方式部署流程资源需要传入一个输入流及资源的名称，输入流的来源不限，可以从classpath读取，也可以从一个绝对路径文件读取，也可以是从网络上读取。
     */
    @Test
    public void deployementProcessDefinitionByInputStream() throws FileNotFoundException {
        //获取资源相对路径
        String bpmnPath = "diagrams/helloworld.bpmn";
        String pngPath = "diagrams/helloworld.png";

        //读取资源作为一个输入流
        FileInputStream bpmnfileInputStream = new FileInputStream(bpmnPath);
        FileInputStream pngfileInputStream = new FileInputStream(pngPath);

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addInputStream("helloworld.bpmn", bpmnfileInputStream)
                .addInputStream("helloworld.png", pngfileInputStream)
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
    }

    /**
     * 字符串方式
     * 利用字符串方式可以直接传入纯文本作为资源的来源，和前两种方式类似，字符串方式的实现原理是把一组字符串的内容转化为字节流后再部署。
     */
    @Test
    public void deployementProcessDefinitionByString() throws FileNotFoundException {
        //字符串
        String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><definitions>...</definitions>";

        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addString("helloworld.bpmn", text)
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
    }

    /**
     * 部署流程定义（zip）
     * 以上3种部署方式一次只能部署一个资源，除非执行多次deployment.addXxx()方法，
     * 如何一次部署多个资源呢？很简单，是我们可以使用zip/bar格式压缩包方式。
     * 将资源文件手动或使用Ant脚本，打包文件扩展名可以是Activiti官方推荐的bar或普通的zip。
     */
    @Test
    public void deployementProcessDefinitionByzip() {
        //从classpath路径下读取资源文件
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .addZipInputStream(zipInputStream)//使用zip方式部署，将helloworld.bpmn和helloworld.png压缩成zip格式的文件
                .deploy();//完成部署
        System.out.println("部署ID：" + deployment.getId());//1
        System.out.println("部署时间：" + deployment.getDeploymentTime());
    }
}
