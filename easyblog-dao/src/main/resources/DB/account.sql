-- 用户、账号、权限相关

CREATE TABLE `account`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`          varchar(32)  NOT NULL DEFAULT '' COMMENT '账号code',
    `user_code`     varchar(10)  NOT NULL DEFAULT '' COMMENT '用户code',
    `identity_type` int          NOT NULL DEFAULT '0' COMMENT '系统用户 1、邮箱 2、手机 3，QQ 4、微信 5、微博 6、GitHub 7、Gitlab 8',
    `identifier`    varchar(100) NOT NULL DEFAULT '' COMMENT '身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号',
    `credential`    varchar(256) NOT NULL DEFAULT '' COMMENT '站内账号是密码、第三方登录是Token',
    `verified`      int          NOT NULL DEFAULT '0' COMMENT '授权账号是否被验证',
    `status`        int          NOT NULL DEFAULT '0' COMMENT '账号状态',
    `create_time`   timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_code (code),
    KEY `idx_iic` (`identity_type`, `identifier`, `credential`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户账号';

CREATE TABLE `user`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `code`         varchar(10) NOT NULL DEFAULT '' COMMENT '用户code',
    `nick_name`    varchar(20)          DEFAULT '' COMMENT '昵称',
    `integration`  int                  DEFAULT '0' COMMENT '积分',
    `level`        int                  DEFAULT '1' COMMENT '等级',
    `visit`        int                  DEFAULT '0' COMMENT '用户文章访问量',
    `active`       int                  DEFAULT '1' COMMENT '用户账户状态',
    `introduction` varchar(1000)        DEFAULT '' COMMENT '用户简介',
    `create_time`  timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_code` (`code`),
    KEY `idx_nick_name` (`nick_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表';


CREATE TABLE `user_header`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`           varchar(32)  NOT NULL DEFAULT '' COMMENT '头像唯一code',
    `header_img_url` varchar(500) NOT NULL DEFAULT '' COMMENT '头像url地址',
    `user_code`      varchar(10)  NOT NULL DEFAULT '' COMMENT '用户id',
    `status`         tinyint      NOT NULL DEFAULT '0' COMMENT '状态: 1 Using; 2 Unsing; 3 Deleted',
    `create_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_code (code),
    KEY `idx_uid_status` (`user_code`, `status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户头像';


CREATE TABLE `mobile_area_code`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`           varchar(32)  NOT NULL DEFAULT '' COMMENT '唯一code',
    `continent_code` varchar(32)  NOT NULL DEFAULT 'AS' COMMENT '大洲编号',
    `crown_code`     varchar(10)  NOT NULL DEFAULT '' COMMENT '国际冠码',
    `country_code`   varchar(100) NOT NULL DEFAULT '' COMMENT '国家码',
    `area_code`      varchar(10)  NOT NULL DEFAULT '' COMMENT '区域码',
    `area_name`      varchar(20)  NOT NULL DEFAULT '' COMMENT '区域名',
    `create_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_code (code),
    KEY `idx_cca` (`crown_code`, `country_code`, `area_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '国际电话号码区号';


CREATE TABLE `phone_auth`
(
    `id`                  bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`                varchar(32) NOT NULL DEFAULT '' COMMENT '唯一code',
    `mobile_area_code_id` varchar(10) NOT NULL DEFAULT '',
    `phone`               varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
    `create_time`         timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_code (code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '';


CREATE TABLE `login_log`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`             varchar(32)  NOT NULL DEFAULT '' COMMENT '唯一code',
    `user_code`        varchar(10)  NOT NULL DEFAULT '' COMMENT '用户code',
    `account_code`     varchar(32)  NOT NULL DEFAULT '0',
    `token`            varchar(100) NOT NULL DEFAULT '',
    `status`           int          NOT NULL DEFAULT '0' COMMENT '状态',
    `ip_address`       varchar(128) NOT NULL DEFAULT '' COMMENT '登录设备ip',
    `device`           varchar(100) NOT NULL DEFAULT '' COMMENT '登录设备名称',
    `operation_system` varchar(100) NOT NULL DEFAULT '' COMMENT '登录设备操作系统',
    `location`         varchar(100) NOT NULL DEFAULT '' COMMENT '登录基于ip模糊定位地址',
    `create_time`      timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY idx_code (code),
    KEY `idx_uat` (`user_code`, account_code, token),
    KEY `idx_ip` (`ip_address`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户登录记录';


CREATE TABLE `roles`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT 'id',
    `code`        varchar(6)  NOT NULL DEFAULT '' COMMENT 'code编码',
    `name`        smallint    NOT NULL DEFAULT 3 COMMENT '角色名称：1 Admin；2 BIZ_OWNER；3 NORMAL；',
    `description` varchar(10) NOT NULL DEFAULT '' COMMENT '描述',
    `enabled`     bit         NOT NULL DEFAULT 1 COMMENT '状态：1 有效；0 无效',
    `create_time` timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_code` (`code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '角色';

-- 创建用户角色关联表
CREATE TABLE user_role_relationship
(
    user_id       INT UNSIGNED NOT NULL,
    role_id       INT UNSIGNED NOT NULL,
    `enabled`     bit          NOT NULL DEFAULT 1 COMMENT '状态：1 有效；0 无效',
    `create_time` timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户角色关联表';