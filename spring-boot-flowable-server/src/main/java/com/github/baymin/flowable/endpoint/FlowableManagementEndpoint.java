package com.github.baymin.flowable.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author BaiZongwei
 * @date 2021/8/9 18:07
 */
@Slf4j
@Controller
@RequestMapping(value = "/management")
public class FlowableManagementEndpoint {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping(value = "/process")
    @ResponseBody
    public List<ProcessDefinition> queryAllProcesses() {
        return repositoryService.createProcessDefinitionQuery().list();
    }

}
