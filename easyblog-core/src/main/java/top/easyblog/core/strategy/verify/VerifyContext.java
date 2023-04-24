package top.easyblog.core.strategy.verify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-12 14:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyContext {

    /**
     * 附件
     */
    private List<InputStream> attachments;
    /**
     * 模板
     */
    private String templateCode;
    /**
     * 校验选项
     */
    private List<Class<? extends VerifyStrategy>> checkOptions;

}
