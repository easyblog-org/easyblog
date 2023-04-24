package top.easyblog.core.strategy.verify;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.easyblog.common.exception.BusinessException;
import top.easyblog.common.response.EasyResultCode;

import java.util.List;
import java.util.Objects;

/**
 * @author: frank.huang
 * @date: 2023-02-12 14:45
 */
@Slf4j
@Component
public class MessageVerifyService {

    @Autowired
    private List<VerifyStrategy> verifyStrategyList;


    public void verify(VerifyContext context) {
        if (CollectionUtils.isEmpty(verifyStrategyList) ||
                Objects.isNull(context) || CollectionUtils.isEmpty(context.getCheckOptions())) {
            log.info("Not found any verify strategy or verify context is null.");
            return;
        }

        List<Class<? extends VerifyStrategy>> checkOptions = context.getCheckOptions();
        for (VerifyStrategy verifyStrategy : verifyStrategyList) {
            Class<? extends VerifyStrategy> strategyClass = verifyStrategy.getClass();
            if (!checkOptions.contains(strategyClass)) {
                continue;
            }
            if (!verifyStrategy.verify(context)) {
                throw new BusinessException(EasyResultCode.ILLEGAL_MESSAGE_RECORD,
                        String.format("Verify not pass:%s", strategyClass.getSimpleName()));
            }
        }
    }

}
