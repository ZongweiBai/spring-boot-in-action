package com.github.baymin.flowable;

import com.github.baymin.flowable.model.Person;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

/**
 * 流程变量的设置和获取
 *
 * @author BaiZongwei
 * @date 2021/8/10 10:07
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessVariablesTest {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 设置流程变量
     * 流程变量的设置方式有两种，一是通过基本类型设置，第二种是通过JavaBean类型设置。
     * 对应数据库表：act_ru_variable
     */
    @Test
    public void setProcessVariables() {
        String processInstanceId = "22505";//流程实例ID
        String assignee = "managers";//任务办理人
        TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量

        //查询当前办理人的任务ID
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)//使用流程实例ID
//                .taskAssignee(assignee)//任务办理人
                .singleResult();

        //设置流程变量【基本类型】
        taskService.setVariable(task.getId(), "请假人", assignee);
        taskService.setVariableLocal(task.getId(), "请假天数", 3);
        taskService.setVariable(task.getId(), "请假日期", new Date());

    }

    /**
     * 设置流程变量？去哪个表里查
     * <p>
     * 数据库对应表：act_ru_variable，细心的你可以看到，通过JavaBean设置的流程变量，
     * 在act_ru_variable中存储的类型为serializable，变量真正存储的地方在act_ge_bytearray中。
     */
    @Test
    public void setProcessVariablesWithJavaBean() {
        String processInstanceId = "22505";//流程实例ID
        String assignee = "张三";//任务办理人
        TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量

        //查询当前办理人的任务ID
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)//使用流程实例ID
//                .taskAssignee(assignee)//任务办理人
                .singleResult();

        //设置流程变量【javabean类型】
        Person p = new Person();
        p.setId(1);
        p.setName("周江霄");
        taskService.setVariable(task.getId(), "人员信息", p);
        System.out.println("流程变量设置成功~~");
    }

    /**
     * 获取流程变量
     */
    @Test
    public void getProcessVariables() {
        String processInstanceId = "22505";//流程实例ID
        String assignee = "张三";//任务办理人
        TaskService taskService = processEngine.getTaskService();
        //获取当前办理人的任务ID
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
//                .taskAssignee(assignee)
                .singleResult();

        //获取流程变量【基本类型】
        String person = (String) taskService.getVariable(task.getId(), "请假人");
        Integer day = (Integer) taskService.getVariableLocal(task.getId(), "请假天数");
        Date date = (Date) taskService.getVariable(task.getId(), "请假日期");
        System.out.println(person + "  " + day + "   " + date);

        //获取流程变量【javaBean类型】
        Person p = (Person) taskService.getVariable(task.getId(), "人员信息");
        System.out.println(p.getId() + "  " + p.getName());
        System.out.println("获取成功~~");
    }

    /**
     * 查询历史的流程变量
     * 对应数据库表：act_ru_execution
     */
    @Test
    public void getHistoryProcessVariables() {
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询
                .variableName("请假天数")
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hiv : list) {
                System.out.println(hiv.getTaskId() + "  " + hiv.getVariableName() + "		" + hiv.getValue() + "		" + hiv.getVariableTypeName());
            }
        }
    }

}
