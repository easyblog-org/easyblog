-- 文章、评论相关

-- 创建文章表
CREATE TABLE article
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID，递增自增',
    code           varchar(32) NOT NULL DEFAULT '' COMMENT '文章code',
    author_id      VARCHAR(10) COMMENT '作者ID，关联user表主键',
    title          VARCHAR(255) COMMENT '文章标题',
    category       BIGINT               DEFAULT 0 COMMENT '文章分类',
    featured_image VARCHAR(255) COMMENT '文章首图',
    status         VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT '文章状态: DRAFT;PUBLISHED;DELETING;DELETED',
    is_top         BIT                  DEFAULT false COMMENT '文章是否置顶，默认为false',
    content_id     INT COMMENT '文章内容ID，关联article_content表主键',
    create_time    DATETIME COMMENT '创建时间',
    update_time    DATETIME COMMENT '更新时间',
    INDEX idx_atc (author_id, title, category)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章表';

-- 创建文章内容表
CREATE TABLE article_content
(
    id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '文章内容ID，递增自增',
    content TEXT COMMENT '文章内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章内容表';

CREATE TABLE article_category
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，递增自增',
    pid         BIGINT        NOT NULL default -1 COMMENT '父级文章分类，顶级文章类型分类有5个：
                                                       编程入门教程；编程实战；编程题库；开源项目；在线工具',
    name        VARBINARY(64) NOT NULL DEFAULT '' COMMENT '分类名称',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章分类表';

create table article_event
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID，递增自增',
    article_code varchar(32) not null default '' comment '文章Code',
    user_code varchar(32) not null default '' comment '用户Code',
    event varchar(32) not null default '' comment '事件',
    create_time DATETIME COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章事件记录';