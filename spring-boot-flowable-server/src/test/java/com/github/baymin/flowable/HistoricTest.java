package com.github.baymin.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * 由于数据库中保存着历史信息以及正在运行的流程实例信息，在实际项目中对已完成任务的查看频率远不及对待办和运行中的任务的查看，
 * 所以activiti采用分开管理，把正在运行的交给RuntimeService管理，而历史数据交给HistoryService来管理。
 *
 * @author BaiZongwei
 * @date 2021/8/9 19:15
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HistoricTest {

    @Autowired
    private ProcessEngine processEngine;

    /**
     * 查询历史流程实例
     * 查询历史流程实例，就是 查找按照某个流程定义的规则一共执行了多少次流程， 对应的数据库表：act_hi_procinst
     */
    @Test
    public void findHisProcessInstance() {
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processDefinitionId("application-upcart-flow:4:15004")//流程定义ID
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricProcessInstance hi : list) {
                System.out.println(hi.getId() + "	  " + hi.getStartTime() + "   " + hi.getEndTime());
            }
        }
    }

    /**
     * 查询历史活动
     * 问题：HistoricActivityInstance对应哪个表
     * 问题：HistoricActivityInstance和HistoricTaskInstance有什么区别
     * 查询历史活动，就是查询某一次 流程的执行一共经历了多少个活动，这里我们使用流程定义ID来查询，对应的数据库表为:act_hi_actinst
     */
    @Test
    public void findHisActivitiList() {
        String processInstanceId = "15005";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hai : list) {
                System.out.println(hai.getId() + "  " + hai.getActivityName());
            }
        }
    }

    /**
     * 查询历史任务
     * 问题：HistoricTaskInstance对应哪个表
     * 查询历史任务，就是查询摸一次流程的执行一共经历了多少个任务，对应表：act_hi_taskinst
     */
    @Test
    public void findHisTaskList() {
        String processInstanceId = "15005";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                System.out.println(hti.getId() + "    " + hti.getName() + "   " + hti.getClaimTime());
            }
        }
    }

    /**
     * 查询历史流程变量
     * 查询历史流程变量，就是查询 某一次流程的执行一共设置的流程变量，对应表：act_hi_varinst
     */
    @Test
    public void findHisVariablesList() {
        String processInstanceId = "15005";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId() + "    " + hvi.getVariableName() + "	" + hvi.getValue());
            }
        }
    }

}
