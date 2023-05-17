package top.easyblog.support.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: frank.huang
 * @date: 2023-04-25 21:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessMessageRecordContext {
    private Long id;

    private String businessId;

    private String businessModule;

    private String businessEvent;

    private Byte status;

    private Integer retryTimes;

    private String failReason;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    private String businessMessage;

    private Boolean isSync;
}
