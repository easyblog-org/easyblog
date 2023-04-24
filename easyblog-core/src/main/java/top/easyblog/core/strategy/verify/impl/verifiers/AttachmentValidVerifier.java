package top.easyblog.core.strategy.verify.impl.verifiers;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import top.easyblog.core.strategy.verify.VerifyStrategy;
import top.easyblog.core.strategy.verify.VerifyContext;

import java.io.InputStream;
import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-12 15:54
 */
@Component
public class AttachmentValidVerifier implements VerifyStrategy {
    @Override
    public boolean verify(VerifyContext request) {
        List<InputStream> attachments = request.getAttachments();
        return CollectionUtils.isNotEmpty(attachments);
    }
}
