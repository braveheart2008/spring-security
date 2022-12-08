create table person (
    id BIGINT NOT NULL primary key COMMENT '主键ID',
    name varchar(25) NOT NULL  COMMENT '用户名称',
    password varchar(255) NOT NULL  COMMENT '用户密码',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态（0：正常；1：已停用）',
    creator BIGINT  COMMENT '创建人',
    updator BIGINT  COMMENT '修改人',
    mark TINYINT DEFAULT 0 COMMENT '标记删除位（0：正常；1：删除。），默认0',
    create_time DateTime  COMMENT '创建时间',
    update_time DateTime COMMENT '更新时间',
    unique index idx_name(name)
);