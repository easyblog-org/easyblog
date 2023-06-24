-- 文章、评论相关

-- 创建文章表
CREATE TABLE articles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文章ID，递增自增',
  author_id VARCHAR(10) COMMENT '作者ID，关联user表主键',
  title VARCHAR(255) COMMENT '文章标题',
  category VARCHAR(255) COMMENT '文章分类',
  featured_image VARCHAR(255) COMMENT '文章首图',
  status VARCHAR(20) COMMENT '文章状态',
  is_top BIT DEFAULT false COMMENT '文章是否置顶，默认为false',
  content_id INT COMMENT '文章内容ID，关联article_content表主键',
  create_time DATETIME COMMENT '创建时间',
  update_time DATETIME COMMENT '更新时间',
  INDEX idx_atc (author_id, title, category)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章表';

-- 创建文章内容表
CREATE TABLE article_content (
  id INT AUTO_INCREMENT PRIMARY KEY COMMENT '文章内容ID，递增自增',
  content TEXT COMMENT '文章内容'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '文章内容表';

