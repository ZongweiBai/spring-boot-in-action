--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.3 (Ubuntu 13.3-1.pgdg20.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: flowable; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA flowable;


ALTER SCHEMA flowable OWNER TO postgres;

--
-- Name: SCHEMA flowable; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA flowable IS 'standard flowable schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: act_evt_log; Type: TABLE; Schema: flowable; Owner: postgres
--

CREATE TABLE flowable.act_evt_log (
    log_nr_ integer NOT NULL,
    type_ character varying(64),
    proc_def_id_ character varying(64),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    time_stamp_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    data_ bytea,
    lock_owner_ character varying(255),
    lock_time_ timestamp without time zone,
    is_processed_ smallint DEFAULT 0
);


ALTER TABLE flowable.act_evt_log OWNER TO postgres;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE; Schema: flowable. Owner: postgres
--

CREATE SEQUENCE flowable.act_evt_log_log_nr__seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE flowable.act_evt_log_log_nr__seq OWNER TO postgres;

--
-- Name: act_evt_log_log_nr__seq; Type: SEQUENCE OWNED BY; Schema: flowable. Owner: postgres
--

ALTER SEQUENCE flowable.act_evt_log_log_nr__seq OWNED BY flowable.act_evt_log.log_nr_;


--
-- Name: act_ge_bytearray; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ge_bytearray (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    deployment_id_ character varying(64),
    bytes_ bytea,
    generated_ boolean
);


ALTER TABLE flowable.act_ge_bytearray OWNER TO postgres;

--
-- Name: act_ge_property; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ge_property (
    name_ character varying(64) NOT NULL,
    value_ character varying(300),
    rev_ integer
);


ALTER TABLE flowable.act_ge_property OWNER TO postgres;

--
-- Name: act_hi_actinst; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_actinst (
    id_ character varying(64) NOT NULL,
    rev_ integer DEFAULT 1,
    proc_def_id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    execution_id_ character varying(64) NOT NULL,
    act_id_ character varying(255) NOT NULL,
    task_id_ character varying(64),
    call_proc_inst_id_ character varying(64),
    act_name_ character varying(255),
    act_type_ character varying(255) NOT NULL,
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    transaction_order_ integer,
    duration_ bigint,
    delete_reason_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_hi_actinst OWNER TO postgres;

--
-- Name: act_hi_attachment; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_attachment (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(255),
    name_ character varying(255),
    description_ character varying(4000),
    type_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    url_ character varying(4000),
    content_id_ character varying(64),
    time_ timestamp without time zone
);


ALTER TABLE flowable.act_hi_attachment OWNER TO postgres;

--
-- Name: act_hi_comment; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_comment (
    id_ character varying(64) NOT NULL,
    type_ character varying(255),
    time_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    action_ character varying(255),
    message_ character varying(4000),
    full_msg_ bytea
);


ALTER TABLE flowable.act_hi_comment OWNER TO postgres;

--
-- Name: act_hi_detail; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_detail (
    id_ character varying(64) NOT NULL,
    type_ character varying(255) NOT NULL,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    act_inst_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(64),
    rev_ integer,
    time_ timestamp without time zone NOT NULL,
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE flowable.act_hi_detail OWNER TO postgres;

--
-- Name: act_hi_entitylink; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_entitylink (
    id_ character varying(64) NOT NULL,
    link_type_ character varying(255),
    create_time_ timestamp without time zone,
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    parent_element_id_ character varying(255),
    ref_scope_id_ character varying(255),
    ref_scope_type_ character varying(255),
    ref_scope_definition_id_ character varying(255),
    root_scope_id_ character varying(255),
    root_scope_type_ character varying(255),
    hierarchy_type_ character varying(255)
);


ALTER TABLE flowable.act_hi_entitylink OWNER TO postgres;

--
-- Name: act_hi_identitylink; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_identitylink (
    id_ character varying(64) NOT NULL,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    create_time_ timestamp without time zone,
    proc_inst_id_ character varying(64),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255)
);


ALTER TABLE flowable.act_hi_identitylink OWNER TO postgres;

--
-- Name: act_hi_procinst; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_procinst (
    id_ character varying(64) NOT NULL,
    rev_ integer DEFAULT 1,
    proc_inst_id_ character varying(64) NOT NULL,
    business_key_ character varying(255),
    proc_def_id_ character varying(64) NOT NULL,
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    start_user_id_ character varying(255),
    start_act_id_ character varying(255),
    end_act_id_ character varying(255),
    super_process_instance_id_ character varying(64),
    delete_reason_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255),
    callback_id_ character varying(255),
    callback_type_ character varying(255),
    reference_id_ character varying(255),
    reference_type_ character varying(255)
);


ALTER TABLE flowable.act_hi_procinst OWNER TO postgres;

--
-- Name: act_hi_taskinst; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_taskinst (
    id_ character varying(64) NOT NULL,
    rev_ integer DEFAULT 1,
    proc_def_id_ character varying(64),
    task_def_id_ character varying(64),
    task_def_key_ character varying(255),
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    propagated_stage_inst_id_ character varying(255),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    owner_ character varying(255),
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    claim_time_ timestamp without time zone,
    end_time_ timestamp without time zone,
    duration_ bigint,
    delete_reason_ character varying(4000),
    priority_ integer,
    due_date_ timestamp without time zone,
    form_key_ character varying(255),
    category_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    last_updated_time_ timestamp without time zone
);


ALTER TABLE flowable.act_hi_taskinst OWNER TO postgres;

--
-- Name: act_hi_tsk_log; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_tsk_log (
    id_ integer NOT NULL,
    type_ character varying(64),
    task_id_ character varying(64) NOT NULL,
    time_stamp_ timestamp without time zone NOT NULL,
    user_id_ character varying(255),
    data_ character varying(4000),
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64),
    scope_id_ character varying(255),
    scope_definition_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_hi_tsk_log OWNER TO postgres;

--
-- Name: act_hi_tsk_log_id__seq; Type: SEQUENCE; Schema: flowable. Owner: postgres
--

CREATE SEQUENCE flowable.act_hi_tsk_log_id__seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE flowable.act_hi_tsk_log_id__seq OWNER TO postgres;

--
-- Name: act_hi_tsk_log_id__seq; Type: SEQUENCE OWNED BY; Schema: flowable. Owner: postgres
--

ALTER SEQUENCE flowable.act_hi_tsk_log_id__seq OWNED BY flowable.act_hi_tsk_log.id_;


--
-- Name: act_hi_varinst; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_hi_varinst (
    id_ character varying(64) NOT NULL,
    rev_ integer DEFAULT 1,
    proc_inst_id_ character varying(64),
    execution_id_ character varying(64),
    task_id_ character varying(64),
    name_ character varying(255) NOT NULL,
    var_type_ character varying(100),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000),
    create_time_ timestamp without time zone,
    last_updated_time_ timestamp without time zone
);


ALTER TABLE flowable.act_hi_varinst OWNER TO postgres;

--
-- Name: act_id_bytearray; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_bytearray (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    bytes_ bytea
);


ALTER TABLE flowable.act_id_bytearray OWNER TO postgres;

--
-- Name: act_id_group; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_group (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    type_ character varying(255)
);


ALTER TABLE flowable.act_id_group OWNER TO postgres;

--
-- Name: act_id_info; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_info (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    user_id_ character varying(64),
    type_ character varying(64),
    key_ character varying(255),
    value_ character varying(255),
    password_ bytea,
    parent_id_ character varying(255)
);


ALTER TABLE flowable.act_id_info OWNER TO postgres;

--
-- Name: act_id_membership; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_membership (
    user_id_ character varying(64) NOT NULL,
    group_id_ character varying(64) NOT NULL
);


ALTER TABLE flowable.act_id_membership OWNER TO postgres;

--
-- Name: act_id_priv; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_priv (
    id_ character varying(64) NOT NULL,
    name_ character varying(255) NOT NULL
);


ALTER TABLE flowable.act_id_priv OWNER TO postgres;

--
-- Name: act_id_priv_mapping; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_priv_mapping (
    id_ character varying(64) NOT NULL,
    priv_id_ character varying(64) NOT NULL,
    user_id_ character varying(255),
    group_id_ character varying(255)
);


ALTER TABLE flowable.act_id_priv_mapping OWNER TO postgres;

--
-- Name: act_id_property; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_property (
    name_ character varying(64) NOT NULL,
    value_ character varying(300),
    rev_ integer
);


ALTER TABLE flowable.act_id_property OWNER TO postgres;

--
-- Name: act_id_token; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_token (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    token_value_ character varying(255),
    token_date_ timestamp without time zone,
    ip_address_ character varying(255),
    user_agent_ character varying(255),
    user_id_ character varying(255),
    token_data_ character varying(2000)
);


ALTER TABLE flowable.act_id_token OWNER TO postgres;

--
-- Name: act_id_user; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_id_user (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    first_ character varying(255),
    last_ character varying(255),
    display_name_ character varying(255),
    email_ character varying(255),
    pwd_ character varying(255),
    picture_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_id_user OWNER TO postgres;

--
-- Name: act_procdef_info; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_procdef_info (
    id_ character varying(64) NOT NULL,
    proc_def_id_ character varying(64) NOT NULL,
    rev_ integer,
    info_json_id_ character varying(64)
);


ALTER TABLE flowable.act_procdef_info OWNER TO postgres;

--
-- Name: act_re_deployment; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_re_deployment (
    id_ character varying(64) NOT NULL,
    name_ character varying(255),
    category_ character varying(255),
    key_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    deploy_time_ timestamp without time zone,
    derived_from_ character varying(64),
    derived_from_root_ character varying(64),
    parent_deployment_id_ character varying(255),
    engine_version_ character varying(255)
);


ALTER TABLE flowable.act_re_deployment OWNER TO postgres;

--
-- Name: act_re_model; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_re_model (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    name_ character varying(255),
    key_ character varying(255),
    category_ character varying(255),
    create_time_ timestamp without time zone,
    last_update_time_ timestamp without time zone,
    version_ integer,
    meta_info_ character varying(4000),
    deployment_id_ character varying(64),
    editor_source_value_id_ character varying(64),
    editor_source_extra_value_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_re_model OWNER TO postgres;

--
-- Name: act_re_procdef; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_re_procdef (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    name_ character varying(255),
    key_ character varying(255) NOT NULL,
    version_ integer NOT NULL,
    deployment_id_ character varying(64),
    resource_name_ character varying(4000),
    dgrm_resource_name_ character varying(4000),
    description_ character varying(4000),
    has_start_form_key_ boolean,
    has_graphical_notation_ boolean,
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    derived_from_ character varying(64),
    derived_from_root_ character varying(64),
    derived_version_ integer DEFAULT 0 NOT NULL,
    engine_version_ character varying(255)
);


ALTER TABLE flowable.act_re_procdef OWNER TO postgres;

--
-- Name: act_ru_actinst; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_actinst (
    id_ character varying(64) NOT NULL,
    rev_ integer DEFAULT 1,
    proc_def_id_ character varying(64) NOT NULL,
    proc_inst_id_ character varying(64) NOT NULL,
    execution_id_ character varying(64) NOT NULL,
    act_id_ character varying(255) NOT NULL,
    task_id_ character varying(64),
    call_proc_inst_id_ character varying(64),
    act_name_ character varying(255),
    act_type_ character varying(255) NOT NULL,
    assignee_ character varying(255),
    start_time_ timestamp without time zone NOT NULL,
    end_time_ timestamp without time zone,
    duration_ bigint,
    transaction_order_ integer,
    delete_reason_ character varying(4000),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_actinst OWNER TO postgres;

--
-- Name: act_ru_deadletter_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_deadletter_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    type_ character varying(255) NOT NULL,
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    element_id_ character varying(255),
    element_name_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    correlation_id_ character varying(255),
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    create_time_ timestamp without time zone,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_deadletter_job OWNER TO postgres;

--
-- Name: act_ru_entitylink; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_entitylink (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    create_time_ timestamp without time zone,
    link_type_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    parent_element_id_ character varying(255),
    ref_scope_id_ character varying(255),
    ref_scope_type_ character varying(255),
    ref_scope_definition_id_ character varying(255),
    root_scope_id_ character varying(255),
    root_scope_type_ character varying(255),
    hierarchy_type_ character varying(255)
);


ALTER TABLE flowable.act_ru_entitylink OWNER TO postgres;

--
-- Name: act_ru_event_subscr; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_event_subscr (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    event_type_ character varying(255) NOT NULL,
    event_name_ character varying(255),
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    activity_id_ character varying(64),
    configuration_ character varying(255),
    created_ timestamp without time zone NOT NULL,
    proc_def_id_ character varying(64),
    sub_scope_id_ character varying(64),
    scope_id_ character varying(64),
    scope_definition_id_ character varying(64),
    scope_type_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_event_subscr OWNER TO postgres;

--
-- Name: act_ru_execution; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_execution (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    proc_inst_id_ character varying(64),
    business_key_ character varying(255),
    parent_id_ character varying(64),
    proc_def_id_ character varying(64),
    super_exec_ character varying(64),
    root_proc_inst_id_ character varying(64),
    act_id_ character varying(255),
    is_active_ boolean,
    is_concurrent_ boolean,
    is_scope_ boolean,
    is_event_scope_ boolean,
    is_mi_root_ boolean,
    suspension_state_ integer,
    cached_ent_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    name_ character varying(255),
    start_act_id_ character varying(255),
    start_time_ timestamp without time zone,
    start_user_id_ character varying(255),
    lock_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    is_count_enabled_ boolean,
    evt_subscr_count_ integer,
    task_count_ integer,
    job_count_ integer,
    timer_job_count_ integer,
    susp_job_count_ integer,
    deadletter_job_count_ integer,
    external_worker_job_count_ integer,
    var_count_ integer,
    id_link_count_ integer,
    callback_id_ character varying(255),
    callback_type_ character varying(255),
    reference_id_ character varying(255),
    reference_type_ character varying(255),
    propagated_stage_inst_id_ character varying(255)
);


ALTER TABLE flowable.act_ru_execution OWNER TO postgres;

--
-- Name: act_ru_external_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_external_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    type_ character varying(255) NOT NULL,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    element_id_ character varying(255),
    element_name_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    correlation_id_ character varying(255),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    create_time_ timestamp without time zone,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_external_job OWNER TO postgres;

--
-- Name: act_ru_history_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_history_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    adv_handler_cfg_id_ character varying(64),
    create_time_ timestamp without time zone,
    scope_type_ character varying(255),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_history_job OWNER TO postgres;

--
-- Name: act_ru_identitylink; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_identitylink (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    group_id_ character varying(255),
    type_ character varying(255),
    user_id_ character varying(255),
    task_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255)
);


ALTER TABLE flowable.act_ru_identitylink OWNER TO postgres;

--
-- Name: act_ru_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    type_ character varying(255) NOT NULL,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    element_id_ character varying(255),
    element_name_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    correlation_id_ character varying(255),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    create_time_ timestamp without time zone,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_job OWNER TO postgres;

--
-- Name: act_ru_suspended_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_suspended_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    type_ character varying(255) NOT NULL,
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    element_id_ character varying(255),
    element_name_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    correlation_id_ character varying(255),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    create_time_ timestamp without time zone,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_suspended_job OWNER TO postgres;

--
-- Name: act_ru_task; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_task (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    proc_def_id_ character varying(64),
    task_def_id_ character varying(64),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    propagated_stage_inst_id_ character varying(255),
    name_ character varying(255),
    parent_task_id_ character varying(64),
    description_ character varying(4000),
    task_def_key_ character varying(255),
    owner_ character varying(255),
    assignee_ character varying(255),
    delegation_ character varying(64),
    priority_ integer,
    create_time_ timestamp without time zone,
    due_date_ timestamp without time zone,
    category_ character varying(255),
    suspension_state_ integer,
    tenant_id_ character varying(255) DEFAULT ''::character varying,
    form_key_ character varying(255),
    claim_time_ timestamp without time zone,
    is_count_enabled_ boolean,
    var_count_ integer,
    id_link_count_ integer,
    sub_task_count_ integer
);


ALTER TABLE flowable.act_ru_task OWNER TO postgres;

--
-- Name: act_ru_timer_job; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_timer_job (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    category_ character varying(255),
    type_ character varying(255) NOT NULL,
    lock_exp_time_ timestamp without time zone,
    lock_owner_ character varying(255),
    exclusive_ boolean,
    execution_id_ character varying(64),
    process_instance_id_ character varying(64),
    proc_def_id_ character varying(64),
    element_id_ character varying(255),
    element_name_ character varying(255),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    scope_definition_id_ character varying(255),
    correlation_id_ character varying(255),
    retries_ integer,
    exception_stack_id_ character varying(64),
    exception_msg_ character varying(4000),
    duedate_ timestamp without time zone,
    repeat_ character varying(255),
    handler_type_ character varying(255),
    handler_cfg_ character varying(4000),
    custom_values_id_ character varying(64),
    create_time_ timestamp without time zone,
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.act_ru_timer_job OWNER TO postgres;

--
-- Name: act_ru_variable; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.act_ru_variable (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(255) NOT NULL,
    name_ character varying(255) NOT NULL,
    execution_id_ character varying(64),
    proc_inst_id_ character varying(64),
    task_id_ character varying(64),
    scope_id_ character varying(255),
    sub_scope_id_ character varying(255),
    scope_type_ character varying(255),
    bytearray_id_ character varying(64),
    double_ double precision,
    long_ bigint,
    text_ character varying(4000),
    text2_ character varying(4000)
);


ALTER TABLE flowable.act_ru_variable OWNER TO postgres;

--
-- Name: flw_channel_definition; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_channel_definition (
    id_ character varying(255) NOT NULL,
    name_ character varying(255),
    version_ integer,
    key_ character varying(255),
    category_ character varying(255),
    deployment_id_ character varying(255),
    create_time_ timestamp(3) without time zone,
    tenant_id_ character varying(255),
    resource_name_ character varying(255),
    description_ character varying(255)
);


ALTER TABLE flowable.flw_channel_definition OWNER TO postgres;

--
-- Name: flw_ev_databasechangelog; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_ev_databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE flowable.flw_ev_databasechangelog OWNER TO postgres;

--
-- Name: flw_ev_databasechangeloglock; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_ev_databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE flowable.flw_ev_databasechangeloglock OWNER TO postgres;

--
-- Name: flw_event_definition; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_event_definition (
    id_ character varying(255) NOT NULL,
    name_ character varying(255),
    version_ integer,
    key_ character varying(255),
    category_ character varying(255),
    deployment_id_ character varying(255),
    tenant_id_ character varying(255),
    resource_name_ character varying(255),
    description_ character varying(255)
);


ALTER TABLE flowable.flw_event_definition OWNER TO postgres;

--
-- Name: flw_event_deployment; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_event_deployment (
    id_ character varying(255) NOT NULL,
    name_ character varying(255),
    category_ character varying(255),
    deploy_time_ timestamp(3) without time zone,
    tenant_id_ character varying(255),
    parent_deployment_id_ character varying(255)
);


ALTER TABLE flowable.flw_event_deployment OWNER TO postgres;

--
-- Name: flw_event_resource; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_event_resource (
    id_ character varying(255) NOT NULL,
    name_ character varying(255),
    deployment_id_ character varying(255),
    resource_bytes_ bytea
);


ALTER TABLE flowable.flw_event_resource OWNER TO postgres;

--
-- Name: flw_ru_batch; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_ru_batch (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    type_ character varying(64) NOT NULL,
    search_key_ character varying(255),
    search_key2_ character varying(255),
    create_time_ timestamp without time zone NOT NULL,
    complete_time_ timestamp without time zone,
    status_ character varying(255),
    batch_doc_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.flw_ru_batch OWNER TO postgres;

--
-- Name: flw_ru_batch_part; Type: TABLE; Schema: flowable. Owner: postgres
--

CREATE TABLE flowable.flw_ru_batch_part (
    id_ character varying(64) NOT NULL,
    rev_ integer,
    batch_id_ character varying(64),
    type_ character varying(64) NOT NULL,
    scope_id_ character varying(64),
    sub_scope_id_ character varying(64),
    scope_type_ character varying(64),
    search_key_ character varying(255),
    search_key2_ character varying(255),
    create_time_ timestamp without time zone NOT NULL,
    complete_time_ timestamp without time zone,
    status_ character varying(255),
    result_doc_id_ character varying(64),
    tenant_id_ character varying(255) DEFAULT ''::character varying
);


ALTER TABLE flowable.flw_ru_batch_part OWNER TO postgres;

--
-- Name: act_evt_log log_nr_; Type: DEFAULT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_evt_log ALTER COLUMN log_nr_ SET DEFAULT nextval('flowable.act_evt_log_log_nr__seq'::regclass);


--
-- Name: act_hi_tsk_log id_; Type: DEFAULT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_tsk_log ALTER COLUMN id_ SET DEFAULT nextval('flowable.act_hi_tsk_log_id__seq'::regclass);


--
-- Name: act_evt_log act_evt_log_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_evt_log
    ADD CONSTRAINT act_evt_log_pkey PRIMARY KEY (log_nr_);


--
-- Name: act_ge_bytearray act_ge_bytearray_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ge_bytearray
    ADD CONSTRAINT act_ge_bytearray_pkey PRIMARY KEY (id_);


--
-- Name: act_ge_property act_ge_property_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ge_property
    ADD CONSTRAINT act_ge_property_pkey PRIMARY KEY (name_);


--
-- Name: act_hi_actinst act_hi_actinst_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_actinst
    ADD CONSTRAINT act_hi_actinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_attachment act_hi_attachment_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_attachment
    ADD CONSTRAINT act_hi_attachment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_comment act_hi_comment_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_comment
    ADD CONSTRAINT act_hi_comment_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_detail act_hi_detail_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_detail
    ADD CONSTRAINT act_hi_detail_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_entitylink act_hi_entitylink_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_entitylink
    ADD CONSTRAINT act_hi_entitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_identitylink act_hi_identitylink_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_identitylink
    ADD CONSTRAINT act_hi_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_procinst act_hi_procinst_proc_inst_id__key; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_procinst
    ADD CONSTRAINT act_hi_procinst_proc_inst_id__key UNIQUE (proc_inst_id_);


--
-- Name: act_hi_taskinst act_hi_taskinst_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_taskinst
    ADD CONSTRAINT act_hi_taskinst_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_tsk_log act_hi_tsk_log_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_tsk_log
    ADD CONSTRAINT act_hi_tsk_log_pkey PRIMARY KEY (id_);


--
-- Name: act_hi_varinst act_hi_varinst_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_hi_varinst
    ADD CONSTRAINT act_hi_varinst_pkey PRIMARY KEY (id_);


--
-- Name: act_id_bytearray act_id_bytearray_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_bytearray
    ADD CONSTRAINT act_id_bytearray_pkey PRIMARY KEY (id_);


--
-- Name: act_id_group act_id_group_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_group
    ADD CONSTRAINT act_id_group_pkey PRIMARY KEY (id_);


--
-- Name: act_id_info act_id_info_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_info
    ADD CONSTRAINT act_id_info_pkey PRIMARY KEY (id_);


--
-- Name: act_id_membership act_id_membership_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_membership
    ADD CONSTRAINT act_id_membership_pkey PRIMARY KEY (user_id_, group_id_);


--
-- Name: act_id_priv_mapping act_id_priv_mapping_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_priv_mapping
    ADD CONSTRAINT act_id_priv_mapping_pkey PRIMARY KEY (id_);


--
-- Name: act_id_priv act_id_priv_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_priv
    ADD CONSTRAINT act_id_priv_pkey PRIMARY KEY (id_);


--
-- Name: act_id_property act_id_property_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_property
    ADD CONSTRAINT act_id_property_pkey PRIMARY KEY (name_);


--
-- Name: act_id_token act_id_token_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_token
    ADD CONSTRAINT act_id_token_pkey PRIMARY KEY (id_);


--
-- Name: act_id_user act_id_user_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_user
    ADD CONSTRAINT act_id_user_pkey PRIMARY KEY (id_);


--
-- Name: act_procdef_info act_procdef_info_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_procdef_info
    ADD CONSTRAINT act_procdef_info_pkey PRIMARY KEY (id_);


--
-- Name: act_re_deployment act_re_deployment_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_deployment
    ADD CONSTRAINT act_re_deployment_pkey PRIMARY KEY (id_);


--
-- Name: act_re_model act_re_model_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_model
    ADD CONSTRAINT act_re_model_pkey PRIMARY KEY (id_);


--
-- Name: act_re_procdef act_re_procdef_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_procdef
    ADD CONSTRAINT act_re_procdef_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_actinst act_ru_actinst_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_actinst
    ADD CONSTRAINT act_ru_actinst_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_deadletter_job act_ru_deadletter_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_ru_deadletter_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_entitylink act_ru_entitylink_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_entitylink
    ADD CONSTRAINT act_ru_entitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_event_subscr act_ru_event_subscr_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_event_subscr
    ADD CONSTRAINT act_ru_event_subscr_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_execution act_ru_execution_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_execution
    ADD CONSTRAINT act_ru_execution_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_external_job act_ru_external_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_external_job
    ADD CONSTRAINT act_ru_external_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_history_job act_ru_history_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_history_job
    ADD CONSTRAINT act_ru_history_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_identitylink act_ru_identitylink_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_identitylink
    ADD CONSTRAINT act_ru_identitylink_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_job act_ru_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_ru_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_suspended_job act_ru_suspended_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_ru_suspended_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_task act_ru_task_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_task
    ADD CONSTRAINT act_ru_task_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_timer_job act_ru_timer_job_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_ru_timer_job_pkey PRIMARY KEY (id_);


--
-- Name: act_ru_variable act_ru_variable_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_variable
    ADD CONSTRAINT act_ru_variable_pkey PRIMARY KEY (id_);


--
-- Name: act_procdef_info act_uniq_info_procdef; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_procdef_info
    ADD CONSTRAINT act_uniq_info_procdef UNIQUE (proc_def_id_);


--
-- Name: act_id_priv act_uniq_priv_name; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_priv
    ADD CONSTRAINT act_uniq_priv_name UNIQUE (name_);


--
-- Name: act_re_procdef act_uniq_procdef; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_procdef
    ADD CONSTRAINT act_uniq_procdef UNIQUE (key_, version_, derived_version_, tenant_id_);


--
-- Name: flw_channel_definition flw_channel_definition_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_channel_definition
    ADD CONSTRAINT flw_channel_definition_pkey PRIMARY KEY (id_);


--
-- Name: flw_ev_databasechangeloglock flw_ev_databasechangeloglock_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_ev_databasechangeloglock
    ADD CONSTRAINT flw_ev_databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: flw_event_definition flw_event_definition_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_event_definition
    ADD CONSTRAINT flw_event_definition_pkey PRIMARY KEY (id_);


--
-- Name: flw_event_deployment flw_event_deployment_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_event_deployment
    ADD CONSTRAINT flw_event_deployment_pkey PRIMARY KEY (id_);


--
-- Name: flw_event_resource flw_event_resource_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_event_resource
    ADD CONSTRAINT flw_event_resource_pkey PRIMARY KEY (id_);


--
-- Name: flw_ru_batch_part flw_ru_batch_part_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_ru_batch_part
    ADD CONSTRAINT flw_ru_batch_part_pkey PRIMARY KEY (id_);


--
-- Name: flw_ru_batch flw_ru_batch_pkey; Type: CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_ru_batch
    ADD CONSTRAINT flw_ru_batch_pkey PRIMARY KEY (id_);


--
-- Name: act_idx_athrz_procedef; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_athrz_procedef ON flowable.act_ru_identitylink USING btree (proc_def_id_);


--
-- Name: act_idx_bytear_depl; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_bytear_depl ON flowable.act_ge_bytearray USING btree (deployment_id_);


--
-- Name: act_idx_channel_def_uniq; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE UNIQUE INDEX act_idx_channel_def_uniq ON flowable.flw_channel_definition USING btree (key_, version_, tenant_id_);


--
-- Name: act_idx_deadletter_job_correlation_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_correlation_id ON flowable.act_ru_deadletter_job USING btree (correlation_id_);


--
-- Name: act_idx_deadletter_job_custom_values_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_custom_values_id ON flowable.act_ru_deadletter_job USING btree (custom_values_id_);


--
-- Name: act_idx_deadletter_job_exception_stack_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_exception_stack_id ON flowable.act_ru_deadletter_job USING btree (exception_stack_id_);


--
-- Name: act_idx_deadletter_job_execution_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_execution_id ON flowable.act_ru_deadletter_job USING btree (execution_id_);


--
-- Name: act_idx_deadletter_job_proc_def_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_proc_def_id ON flowable.act_ru_deadletter_job USING btree (proc_def_id_);


--
-- Name: act_idx_deadletter_job_process_instance_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_deadletter_job_process_instance_id ON flowable.act_ru_deadletter_job USING btree (process_instance_id_);


--
-- Name: act_idx_djob_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_djob_scope ON flowable.act_ru_deadletter_job USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_djob_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_djob_scope_def ON flowable.act_ru_deadletter_job USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_djob_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_djob_sub_scope ON flowable.act_ru_deadletter_job USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_ejob_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ejob_scope ON flowable.act_ru_external_job USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_ejob_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ejob_scope_def ON flowable.act_ru_external_job USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_ejob_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ejob_sub_scope ON flowable.act_ru_external_job USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_ent_lnk_root_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ent_lnk_root_scope ON flowable.act_ru_entitylink USING btree (root_scope_id_, root_scope_type_, link_type_);


--
-- Name: act_idx_ent_lnk_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ent_lnk_scope ON flowable.act_ru_entitylink USING btree (scope_id_, scope_type_, link_type_);


--
-- Name: act_idx_ent_lnk_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ent_lnk_scope_def ON flowable.act_ru_entitylink USING btree (scope_definition_id_, scope_type_, link_type_);


--
-- Name: act_idx_event_def_uniq; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE UNIQUE INDEX act_idx_event_def_uniq ON flowable.flw_event_definition USING btree (key_, version_, tenant_id_);


--
-- Name: act_idx_event_subscr; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_event_subscr ON flowable.act_ru_event_subscr USING btree (execution_id_);


--
-- Name: act_idx_event_subscr_config_; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_event_subscr_config_ ON flowable.act_ru_event_subscr USING btree (configuration_);


--
-- Name: act_idx_exe_parent; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exe_parent ON flowable.act_ru_execution USING btree (parent_id_);


--
-- Name: act_idx_exe_procdef; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exe_procdef ON flowable.act_ru_execution USING btree (proc_def_id_);


--
-- Name: act_idx_exe_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exe_procinst ON flowable.act_ru_execution USING btree (proc_inst_id_);


--
-- Name: act_idx_exe_root; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exe_root ON flowable.act_ru_execution USING btree (root_proc_inst_id_);


--
-- Name: act_idx_exe_super; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exe_super ON flowable.act_ru_execution USING btree (super_exec_);


--
-- Name: act_idx_exec_buskey; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_exec_buskey ON flowable.act_ru_execution USING btree (business_key_);


--
-- Name: act_idx_external_job_correlation_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_external_job_correlation_id ON flowable.act_ru_external_job USING btree (correlation_id_);


--
-- Name: act_idx_external_job_custom_values_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_external_job_custom_values_id ON flowable.act_ru_external_job USING btree (custom_values_id_);


--
-- Name: act_idx_external_job_exception_stack_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_external_job_exception_stack_id ON flowable.act_ru_external_job USING btree (exception_stack_id_);


--
-- Name: act_idx_hi_act_inst_end; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_end ON flowable.act_hi_actinst USING btree (end_time_);


--
-- Name: act_idx_hi_act_inst_exec; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_exec ON flowable.act_hi_actinst USING btree (execution_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_procinst ON flowable.act_hi_actinst USING btree (proc_inst_id_, act_id_);


--
-- Name: act_idx_hi_act_inst_start; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_act_inst_start ON flowable.act_hi_actinst USING btree (start_time_);


--
-- Name: act_idx_hi_detail_act_inst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_detail_act_inst ON flowable.act_hi_detail USING btree (act_inst_id_);


--
-- Name: act_idx_hi_detail_name; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_detail_name ON flowable.act_hi_detail USING btree (name_);


--
-- Name: act_idx_hi_detail_proc_inst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_detail_proc_inst ON flowable.act_hi_detail USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_detail_task_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_detail_task_id ON flowable.act_hi_detail USING btree (task_id_);


--
-- Name: act_idx_hi_detail_time; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_detail_time ON flowable.act_hi_detail USING btree (time_);


--
-- Name: act_idx_hi_ent_lnk_root_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ent_lnk_root_scope ON flowable.act_hi_entitylink USING btree (root_scope_id_, root_scope_type_, link_type_);


--
-- Name: act_idx_hi_ent_lnk_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ent_lnk_scope ON flowable.act_hi_entitylink USING btree (scope_id_, scope_type_, link_type_);


--
-- Name: act_idx_hi_ent_lnk_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ent_lnk_scope_def ON flowable.act_hi_entitylink USING btree (scope_definition_id_, scope_type_, link_type_);


--
-- Name: act_idx_hi_ident_lnk_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_procinst ON flowable.act_hi_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_ident_lnk_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_scope ON flowable.act_hi_identitylink USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_hi_ident_lnk_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_scope_def ON flowable.act_hi_identitylink USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_hi_ident_lnk_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_sub_scope ON flowable.act_hi_identitylink USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_hi_ident_lnk_task; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_task ON flowable.act_hi_identitylink USING btree (task_id_);


--
-- Name: act_idx_hi_ident_lnk_user; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_ident_lnk_user ON flowable.act_hi_identitylink USING btree (user_id_);


--
-- Name: act_idx_hi_pro_i_buskey; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_pro_i_buskey ON flowable.act_hi_procinst USING btree (business_key_);


--
-- Name: act_idx_hi_pro_inst_end; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_pro_inst_end ON flowable.act_hi_procinst USING btree (end_time_);


--
-- Name: act_idx_hi_procvar_exe; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_exe ON flowable.act_hi_varinst USING btree (execution_id_);


--
-- Name: act_idx_hi_procvar_name_type; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_name_type ON flowable.act_hi_varinst USING btree (name_, var_type_);


--
-- Name: act_idx_hi_procvar_proc_inst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_proc_inst ON flowable.act_hi_varinst USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_procvar_task_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_procvar_task_id ON flowable.act_hi_varinst USING btree (task_id_);


--
-- Name: act_idx_hi_task_inst_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_task_inst_procinst ON flowable.act_hi_taskinst USING btree (proc_inst_id_);


--
-- Name: act_idx_hi_task_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_task_scope ON flowable.act_hi_taskinst USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_hi_task_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_task_scope_def ON flowable.act_hi_taskinst USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_hi_task_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_task_sub_scope ON flowable.act_hi_taskinst USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_hi_var_scope_id_type; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_var_scope_id_type ON flowable.act_hi_varinst USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_hi_var_sub_id_type; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_hi_var_sub_id_type ON flowable.act_hi_varinst USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_ident_lnk_group; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_group ON flowable.act_ru_identitylink USING btree (group_id_);


--
-- Name: act_idx_ident_lnk_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_scope ON flowable.act_ru_identitylink USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_ident_lnk_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_scope_def ON flowable.act_ru_identitylink USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_ident_lnk_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_sub_scope ON flowable.act_ru_identitylink USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_ident_lnk_user; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ident_lnk_user ON flowable.act_ru_identitylink USING btree (user_id_);


--
-- Name: act_idx_idl_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_idl_procinst ON flowable.act_ru_identitylink USING btree (proc_inst_id_);


--
-- Name: act_idx_job_correlation_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_correlation_id ON flowable.act_ru_job USING btree (correlation_id_);


--
-- Name: act_idx_job_custom_values_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_custom_values_id ON flowable.act_ru_job USING btree (custom_values_id_);


--
-- Name: act_idx_job_exception_stack_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_exception_stack_id ON flowable.act_ru_job USING btree (exception_stack_id_);


--
-- Name: act_idx_job_execution_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_execution_id ON flowable.act_ru_job USING btree (execution_id_);


--
-- Name: act_idx_job_proc_def_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_proc_def_id ON flowable.act_ru_job USING btree (proc_def_id_);


--
-- Name: act_idx_job_process_instance_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_process_instance_id ON flowable.act_ru_job USING btree (process_instance_id_);


--
-- Name: act_idx_job_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_scope ON flowable.act_ru_job USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_job_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_scope_def ON flowable.act_ru_job USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_job_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_job_sub_scope ON flowable.act_ru_job USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_memb_group; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_memb_group ON flowable.act_id_membership USING btree (group_id_);


--
-- Name: act_idx_memb_user; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_memb_user ON flowable.act_id_membership USING btree (user_id_);


--
-- Name: act_idx_model_deployment; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_model_deployment ON flowable.act_re_model USING btree (deployment_id_);


--
-- Name: act_idx_model_source; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_model_source ON flowable.act_re_model USING btree (editor_source_value_id_);


--
-- Name: act_idx_model_source_extra; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_model_source_extra ON flowable.act_re_model USING btree (editor_source_extra_value_id_);


--
-- Name: act_idx_priv_group; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_priv_group ON flowable.act_id_priv_mapping USING btree (group_id_);


--
-- Name: act_idx_priv_mapping; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_priv_mapping ON flowable.act_id_priv_mapping USING btree (priv_id_);


--
-- Name: act_idx_priv_user; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_priv_user ON flowable.act_id_priv_mapping USING btree (user_id_);


--
-- Name: act_idx_procdef_info_json; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_procdef_info_json ON flowable.act_procdef_info USING btree (info_json_id_);


--
-- Name: act_idx_procdef_info_proc; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_procdef_info_proc ON flowable.act_procdef_info USING btree (proc_def_id_);


--
-- Name: act_idx_ru_acti_end; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_end ON flowable.act_ru_actinst USING btree (end_time_);


--
-- Name: act_idx_ru_acti_exec; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_exec ON flowable.act_ru_actinst USING btree (execution_id_);


--
-- Name: act_idx_ru_acti_exec_act; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_exec_act ON flowable.act_ru_actinst USING btree (execution_id_, act_id_);


--
-- Name: act_idx_ru_acti_proc; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_proc ON flowable.act_ru_actinst USING btree (proc_inst_id_);


--
-- Name: act_idx_ru_acti_proc_act; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_proc_act ON flowable.act_ru_actinst USING btree (proc_inst_id_, act_id_);


--
-- Name: act_idx_ru_acti_start; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_acti_start ON flowable.act_ru_actinst USING btree (start_time_);


--
-- Name: act_idx_ru_var_scope_id_type; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_var_scope_id_type ON flowable.act_ru_variable USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_ru_var_sub_id_type; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_ru_var_sub_id_type ON flowable.act_ru_variable USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_sjob_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_sjob_scope ON flowable.act_ru_suspended_job USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_sjob_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_sjob_scope_def ON flowable.act_ru_suspended_job USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_sjob_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_sjob_sub_scope ON flowable.act_ru_suspended_job USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_suspended_job_correlation_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_correlation_id ON flowable.act_ru_suspended_job USING btree (correlation_id_);


--
-- Name: act_idx_suspended_job_custom_values_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_custom_values_id ON flowable.act_ru_suspended_job USING btree (custom_values_id_);


--
-- Name: act_idx_suspended_job_exception_stack_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_exception_stack_id ON flowable.act_ru_suspended_job USING btree (exception_stack_id_);


--
-- Name: act_idx_suspended_job_execution_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_execution_id ON flowable.act_ru_suspended_job USING btree (execution_id_);


--
-- Name: act_idx_suspended_job_proc_def_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_proc_def_id ON flowable.act_ru_suspended_job USING btree (proc_def_id_);


--
-- Name: act_idx_suspended_job_process_instance_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_suspended_job_process_instance_id ON flowable.act_ru_suspended_job USING btree (process_instance_id_);


--
-- Name: act_idx_task_create; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_create ON flowable.act_ru_task USING btree (create_time_);


--
-- Name: act_idx_task_exec; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_exec ON flowable.act_ru_task USING btree (execution_id_);


--
-- Name: act_idx_task_procdef; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_procdef ON flowable.act_ru_task USING btree (proc_def_id_);


--
-- Name: act_idx_task_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_procinst ON flowable.act_ru_task USING btree (proc_inst_id_);


--
-- Name: act_idx_task_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_scope ON flowable.act_ru_task USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_task_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_scope_def ON flowable.act_ru_task USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_task_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_task_sub_scope ON flowable.act_ru_task USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_timer_job_correlation_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_correlation_id ON flowable.act_ru_timer_job USING btree (correlation_id_);


--
-- Name: act_idx_timer_job_custom_values_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_custom_values_id ON flowable.act_ru_timer_job USING btree (custom_values_id_);


--
-- Name: act_idx_timer_job_exception_stack_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_exception_stack_id ON flowable.act_ru_timer_job USING btree (exception_stack_id_);


--
-- Name: act_idx_timer_job_execution_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_execution_id ON flowable.act_ru_timer_job USING btree (execution_id_);


--
-- Name: act_idx_timer_job_proc_def_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_proc_def_id ON flowable.act_ru_timer_job USING btree (proc_def_id_);


--
-- Name: act_idx_timer_job_process_instance_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_timer_job_process_instance_id ON flowable.act_ru_timer_job USING btree (process_instance_id_);


--
-- Name: act_idx_tjob_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_tjob_scope ON flowable.act_ru_timer_job USING btree (scope_id_, scope_type_);


--
-- Name: act_idx_tjob_scope_def; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_tjob_scope_def ON flowable.act_ru_timer_job USING btree (scope_definition_id_, scope_type_);


--
-- Name: act_idx_tjob_sub_scope; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_tjob_sub_scope ON flowable.act_ru_timer_job USING btree (sub_scope_id_, scope_type_);


--
-- Name: act_idx_tskass_task; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_tskass_task ON flowable.act_ru_identitylink USING btree (task_id_);


--
-- Name: act_idx_var_bytearray; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_var_bytearray ON flowable.act_ru_variable USING btree (bytearray_id_);


--
-- Name: act_idx_var_exe; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_var_exe ON flowable.act_ru_variable USING btree (execution_id_);


--
-- Name: act_idx_var_procinst; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_var_procinst ON flowable.act_ru_variable USING btree (proc_inst_id_);


--
-- Name: act_idx_variable_task_id; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX act_idx_variable_task_id ON flowable.act_ru_variable USING btree (task_id_);


--
-- Name: flw_idx_batch_part; Type: INDEX; Schema: flowable. Owner: postgres
--

CREATE INDEX flw_idx_batch_part ON flowable.flw_ru_batch_part USING btree (batch_id_);


--
-- Name: act_ru_identitylink act_fk_athrz_procedef; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_identitylink
    ADD CONSTRAINT act_fk_athrz_procedef FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ge_bytearray act_fk_bytearr_depl; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ge_bytearray
    ADD CONSTRAINT act_fk_bytearr_depl FOREIGN KEY (deployment_id_) REFERENCES flowable.act_re_deployment(id_);


--
-- Name: act_ru_deadletter_job act_fk_deadletter_job_custom_values; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_fk_deadletter_job_custom_values FOREIGN KEY (custom_values_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_deadletter_job act_fk_deadletter_job_exception; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_fk_deadletter_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_deadletter_job act_fk_deadletter_job_execution; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_fk_deadletter_job_execution FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_deadletter_job act_fk_deadletter_job_proc_def; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_fk_deadletter_job_proc_def FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_deadletter_job act_fk_deadletter_job_process_instance; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_deadletter_job
    ADD CONSTRAINT act_fk_deadletter_job_process_instance FOREIGN KEY (process_instance_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_event_subscr act_fk_event_exec; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_event_subscr
    ADD CONSTRAINT act_fk_event_exec FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_parent; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_execution
    ADD CONSTRAINT act_fk_exe_parent FOREIGN KEY (parent_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_procdef; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_execution
    ADD CONSTRAINT act_fk_exe_procdef FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_execution act_fk_exe_procinst; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_execution
    ADD CONSTRAINT act_fk_exe_procinst FOREIGN KEY (proc_inst_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_execution act_fk_exe_super; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_execution
    ADD CONSTRAINT act_fk_exe_super FOREIGN KEY (super_exec_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_external_job act_fk_external_job_custom_values; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_external_job
    ADD CONSTRAINT act_fk_external_job_custom_values FOREIGN KEY (custom_values_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_external_job act_fk_external_job_exception; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_external_job
    ADD CONSTRAINT act_fk_external_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_identitylink act_fk_idl_procinst; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_identitylink
    ADD CONSTRAINT act_fk_idl_procinst FOREIGN KEY (proc_inst_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_procdef_info act_fk_info_json_ba; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_procdef_info
    ADD CONSTRAINT act_fk_info_json_ba FOREIGN KEY (info_json_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_procdef_info act_fk_info_procdef; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_procdef_info
    ADD CONSTRAINT act_fk_info_procdef FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_job act_fk_job_custom_values; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_fk_job_custom_values FOREIGN KEY (custom_values_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_job act_fk_job_exception; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_fk_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_job act_fk_job_execution; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_fk_job_execution FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_job act_fk_job_proc_def; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_fk_job_proc_def FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_job act_fk_job_process_instance; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_job
    ADD CONSTRAINT act_fk_job_process_instance FOREIGN KEY (process_instance_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_id_membership act_fk_memb_group; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_membership
    ADD CONSTRAINT act_fk_memb_group FOREIGN KEY (group_id_) REFERENCES flowable.act_id_group(id_);


--
-- Name: act_id_membership act_fk_memb_user; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_membership
    ADD CONSTRAINT act_fk_memb_user FOREIGN KEY (user_id_) REFERENCES flowable.act_id_user(id_);


--
-- Name: act_re_model act_fk_model_deployment; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_model
    ADD CONSTRAINT act_fk_model_deployment FOREIGN KEY (deployment_id_) REFERENCES flowable.act_re_deployment(id_);


--
-- Name: act_re_model act_fk_model_source; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_model
    ADD CONSTRAINT act_fk_model_source FOREIGN KEY (editor_source_value_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_re_model act_fk_model_source_extra; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_re_model
    ADD CONSTRAINT act_fk_model_source_extra FOREIGN KEY (editor_source_extra_value_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_id_priv_mapping act_fk_priv_mapping; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_id_priv_mapping
    ADD CONSTRAINT act_fk_priv_mapping FOREIGN KEY (priv_id_) REFERENCES flowable.act_id_priv(id_);


--
-- Name: act_ru_suspended_job act_fk_suspended_job_custom_values; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_fk_suspended_job_custom_values FOREIGN KEY (custom_values_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_suspended_job act_fk_suspended_job_exception; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_fk_suspended_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_suspended_job act_fk_suspended_job_execution; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_fk_suspended_job_execution FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_suspended_job act_fk_suspended_job_proc_def; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_fk_suspended_job_proc_def FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_suspended_job act_fk_suspended_job_process_instance; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_suspended_job
    ADD CONSTRAINT act_fk_suspended_job_process_instance FOREIGN KEY (process_instance_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_task act_fk_task_exe; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_task
    ADD CONSTRAINT act_fk_task_exe FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_task act_fk_task_procdef; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_task
    ADD CONSTRAINT act_fk_task_procdef FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_task act_fk_task_procinst; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_task
    ADD CONSTRAINT act_fk_task_procinst FOREIGN KEY (proc_inst_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_timer_job act_fk_timer_job_custom_values; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_fk_timer_job_custom_values FOREIGN KEY (custom_values_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_timer_job act_fk_timer_job_exception; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_fk_timer_job_exception FOREIGN KEY (exception_stack_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_timer_job act_fk_timer_job_execution; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_fk_timer_job_execution FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_timer_job act_fk_timer_job_proc_def; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_fk_timer_job_proc_def FOREIGN KEY (proc_def_id_) REFERENCES flowable.act_re_procdef(id_);


--
-- Name: act_ru_timer_job act_fk_timer_job_process_instance; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_timer_job
    ADD CONSTRAINT act_fk_timer_job_process_instance FOREIGN KEY (process_instance_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_identitylink act_fk_tskass_task; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_identitylink
    ADD CONSTRAINT act_fk_tskass_task FOREIGN KEY (task_id_) REFERENCES flowable.act_ru_task(id_);


--
-- Name: act_ru_variable act_fk_var_bytearray; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_variable
    ADD CONSTRAINT act_fk_var_bytearray FOREIGN KEY (bytearray_id_) REFERENCES flowable.act_ge_bytearray(id_);


--
-- Name: act_ru_variable act_fk_var_exe; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_variable
    ADD CONSTRAINT act_fk_var_exe FOREIGN KEY (execution_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: act_ru_variable act_fk_var_procinst; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.act_ru_variable
    ADD CONSTRAINT act_fk_var_procinst FOREIGN KEY (proc_inst_id_) REFERENCES flowable.act_ru_execution(id_);


--
-- Name: flw_ru_batch_part flw_fk_batch_part_parent; Type: FK CONSTRAINT; Schema: flowable. Owner: postgres
--

ALTER TABLE ONLY flowable.flw_ru_batch_part
    ADD CONSTRAINT flw_fk_batch_part_parent FOREIGN KEY (batch_id_) REFERENCES flowable.flw_ru_batch(id_);


--
-- PostgreSQL database dump complete
--

