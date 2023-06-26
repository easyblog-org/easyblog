package top.easyblog.service.section;

/**
 * @author: frank.huang
 * @date: 2023-06-25 21:38
 */
public interface ISectionInfoInquireService<T, CTX> {

    /**
     * 执行选项信息查询
     *
     * @param section               选项名称
     * @param ctx                   上下文
     * @param queryParams           查询参数
     * @param queryWhenSectionEmpty 是否在选项名称为空时继续执行查询
     * @return
     */
    void execute(String section, CTX ctx, T queryParams, boolean queryWhenSectionEmpty);

    /**
     * 执行选项信息查询
     *
     * @param section     选项名称
     * @param ctx         上下文
     * @param queryParams 查询参数
     * @return
     */
    default void execute(String section, CTX ctx, T queryParams) {
        execute(section, ctx, queryParams, false);
    }

    /**
     * 执行选项信息查询,当选项参数为空时将继续执行查询
     *
     * @param ctx         上下文
     * @param queryParams 查询参数
     * @return
     */
    default void execute(CTX ctx, T queryParams) {
        execute(null, ctx, queryParams, true);
    }

}
