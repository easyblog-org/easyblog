package top.easyblog.dao.auto.model.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer limit;

    public ArticleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        limit = null;
        offset = null;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public ArticleExample limit(Integer rows) {
        this.limit = rows;
        return this;
    }

    public ArticleExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.limit = rows;
        return this;
    }

    public ArticleExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.limit = pageSize;
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNull() {
            addCriterion("author_id is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIsNotNull() {
            addCriterion("author_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorIdEqualTo(String value) {
            addCriterion("author_id =", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotEqualTo(String value) {
            addCriterion("author_id <>", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThan(String value) {
            addCriterion("author_id >", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdGreaterThanOrEqualTo(String value) {
            addCriterion("author_id >=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThan(String value) {
            addCriterion("author_id <", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLessThanOrEqualTo(String value) {
            addCriterion("author_id <=", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdLike(String value) {
            addCriterion("author_id like", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotLike(String value) {
            addCriterion("author_id not like", value, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdIn(List<String> values) {
            addCriterion("author_id in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotIn(List<String> values) {
            addCriterion("author_id not in", values, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdBetween(String value1, String value2) {
            addCriterion("author_id between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andAuthorIdNotBetween(String value1, String value2) {
            addCriterion("author_id not between", value1, value2, "authorId");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageIsNull() {
            addCriterion("featured_image is null");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageIsNotNull() {
            addCriterion("featured_image is not null");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageEqualTo(String value) {
            addCriterion("featured_image =", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageNotEqualTo(String value) {
            addCriterion("featured_image <>", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageGreaterThan(String value) {
            addCriterion("featured_image >", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageGreaterThanOrEqualTo(String value) {
            addCriterion("featured_image >=", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageLessThan(String value) {
            addCriterion("featured_image <", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageLessThanOrEqualTo(String value) {
            addCriterion("featured_image <=", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageLike(String value) {
            addCriterion("featured_image like", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageNotLike(String value) {
            addCriterion("featured_image not like", value, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageIn(List<String> values) {
            addCriterion("featured_image in", values, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageNotIn(List<String> values) {
            addCriterion("featured_image not in", values, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageBetween(String value1, String value2) {
            addCriterion("featured_image between", value1, value2, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andFeaturedImageNotBetween(String value1, String value2) {
            addCriterion("featured_image not between", value1, value2, "featuredImage");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNull() {
            addCriterion("content_id is null");
            return (Criteria) this;
        }

        public Criteria andContentIdIsNotNull() {
            addCriterion("content_id is not null");
            return (Criteria) this;
        }

        public Criteria andContentIdEqualTo(String value) {
            addCriterion("content_id =", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotEqualTo(String value) {
            addCriterion("content_id <>", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThan(String value) {
            addCriterion("content_id >", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdGreaterThanOrEqualTo(String value) {
            addCriterion("content_id >=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThan(String value) {
            addCriterion("content_id <", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLessThanOrEqualTo(String value) {
            addCriterion("content_id <=", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdLike(String value) {
            addCriterion("content_id like", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotLike(String value) {
            addCriterion("content_id not like", value, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdIn(List<String> values) {
            addCriterion("content_id in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotIn(List<String> values) {
            addCriterion("content_id not in", values, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdBetween(String value1, String value2) {
            addCriterion("content_id between", value1, value2, "contentId");
            return (Criteria) this;
        }

        public Criteria andContentIdNotBetween(String value1, String value2) {
            addCriterion("content_id not between", value1, value2, "contentId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIsTopIsNull() {
            addCriterion("is_top is null");
            return (Criteria) this;
        }

        public Criteria andIsTopIsNotNull() {
            addCriterion("is_top is not null");
            return (Criteria) this;
        }

        public Criteria andIsTopEqualTo(Boolean value) {
            addCriterion("is_top =", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopNotEqualTo(Boolean value) {
            addCriterion("is_top <>", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopGreaterThan(Boolean value) {
            addCriterion("is_top >", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_top >=", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopLessThan(Boolean value) {
            addCriterion("is_top <", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopLessThanOrEqualTo(Boolean value) {
            addCriterion("is_top <=", value, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopIn(List<Boolean> values) {
            addCriterion("is_top in", values, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopNotIn(List<Boolean> values) {
            addCriterion("is_top not in", values, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopBetween(Boolean value1, Boolean value2) {
            addCriterion("is_top between", value1, value2, "isTop");
            return (Criteria) this;
        }

        public Criteria andIsTopNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_top not between", value1, value2, "isTop");
            return (Criteria) this;
        }

        public Criteria andLikesNumIsNull() {
            addCriterion("likes_num is null");
            return (Criteria) this;
        }

        public Criteria andLikesNumIsNotNull() {
            addCriterion("likes_num is not null");
            return (Criteria) this;
        }

        public Criteria andLikesNumEqualTo(Integer value) {
            addCriterion("likes_num =", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumNotEqualTo(Integer value) {
            addCriterion("likes_num <>", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumGreaterThan(Integer value) {
            addCriterion("likes_num >", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("likes_num >=", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumLessThan(Integer value) {
            addCriterion("likes_num <", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumLessThanOrEqualTo(Integer value) {
            addCriterion("likes_num <=", value, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumIn(List<Integer> values) {
            addCriterion("likes_num in", values, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumNotIn(List<Integer> values) {
            addCriterion("likes_num not in", values, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumBetween(Integer value1, Integer value2) {
            addCriterion("likes_num between", value1, value2, "likesNum");
            return (Criteria) this;
        }

        public Criteria andLikesNumNotBetween(Integer value1, Integer value2) {
            addCriterion("likes_num not between", value1, value2, "likesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumIsNull() {
            addCriterion("favorites_num is null");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumIsNotNull() {
            addCriterion("favorites_num is not null");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumEqualTo(Integer value) {
            addCriterion("favorites_num =", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumNotEqualTo(Integer value) {
            addCriterion("favorites_num <>", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumGreaterThan(Integer value) {
            addCriterion("favorites_num >", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorites_num >=", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumLessThan(Integer value) {
            addCriterion("favorites_num <", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumLessThanOrEqualTo(Integer value) {
            addCriterion("favorites_num <=", value, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumIn(List<Integer> values) {
            addCriterion("favorites_num in", values, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumNotIn(List<Integer> values) {
            addCriterion("favorites_num not in", values, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumBetween(Integer value1, Integer value2) {
            addCriterion("favorites_num between", value1, value2, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andFavoritesNumNotBetween(Integer value1, Integer value2) {
            addCriterion("favorites_num not between", value1, value2, "favoritesNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumIsNull() {
            addCriterion("retweets_num is null");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumIsNotNull() {
            addCriterion("retweets_num is not null");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumEqualTo(Integer value) {
            addCriterion("retweets_num =", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumNotEqualTo(Integer value) {
            addCriterion("retweets_num <>", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumGreaterThan(Integer value) {
            addCriterion("retweets_num >", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("retweets_num >=", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumLessThan(Integer value) {
            addCriterion("retweets_num <", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumLessThanOrEqualTo(Integer value) {
            addCriterion("retweets_num <=", value, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumIn(List<Integer> values) {
            addCriterion("retweets_num in", values, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumNotIn(List<Integer> values) {
            addCriterion("retweets_num not in", values, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumBetween(Integer value1, Integer value2) {
            addCriterion("retweets_num between", value1, value2, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andRetweetsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("retweets_num not between", value1, value2, "retweetsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumIsNull() {
            addCriterion("reports_num is null");
            return (Criteria) this;
        }

        public Criteria andReportsNumIsNotNull() {
            addCriterion("reports_num is not null");
            return (Criteria) this;
        }

        public Criteria andReportsNumEqualTo(Integer value) {
            addCriterion("reports_num =", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumNotEqualTo(Integer value) {
            addCriterion("reports_num <>", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumGreaterThan(Integer value) {
            addCriterion("reports_num >", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reports_num >=", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumLessThan(Integer value) {
            addCriterion("reports_num <", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumLessThanOrEqualTo(Integer value) {
            addCriterion("reports_num <=", value, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumIn(List<Integer> values) {
            addCriterion("reports_num in", values, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumNotIn(List<Integer> values) {
            addCriterion("reports_num not in", values, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumBetween(Integer value1, Integer value2) {
            addCriterion("reports_num between", value1, value2, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andReportsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reports_num not between", value1, value2, "reportsNum");
            return (Criteria) this;
        }

        public Criteria andPageViewsIsNull() {
            addCriterion("page_views is null");
            return (Criteria) this;
        }

        public Criteria andPageViewsIsNotNull() {
            addCriterion("page_views is not null");
            return (Criteria) this;
        }

        public Criteria andPageViewsEqualTo(Integer value) {
            addCriterion("page_views =", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsNotEqualTo(Integer value) {
            addCriterion("page_views <>", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsGreaterThan(Integer value) {
            addCriterion("page_views >", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsGreaterThanOrEqualTo(Integer value) {
            addCriterion("page_views >=", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsLessThan(Integer value) {
            addCriterion("page_views <", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsLessThanOrEqualTo(Integer value) {
            addCriterion("page_views <=", value, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsIn(List<Integer> values) {
            addCriterion("page_views in", values, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsNotIn(List<Integer> values) {
            addCriterion("page_views not in", values, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsBetween(Integer value1, Integer value2) {
            addCriterion("page_views between", value1, value2, "pageViews");
            return (Criteria) this;
        }

        public Criteria andPageViewsNotBetween(Integer value1, Integer value2) {
            addCriterion("page_views not between", value1, value2, "pageViews");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}