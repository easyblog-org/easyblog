package top.easyblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: frank.huang
 * @date: 2023-07-29 19:55
 */
@Getter
@AllArgsConstructor
public enum ArticleReportEvent {

    //vulgar pornography
    VULGAR_PORNOGRAPHY("E0001", "低俗色情"),
    //plagiarism
    CONTENT_PLAGIARISM("E0002", "内容抄袭"),
    //Suspected of illegal
    SUSPECTED_OF_ILLEGAL("E0003", "涉嫌违法"),
    //malicious marketing
    MALICIOUS_MARKETING("E0004", "恶意营销"),
    //Content quality is too poor
    CONTENT_QUALITY_POOR("E0005", "内容质量太差"),
    //Violation of reputation/privacy/authorship/portrait rights, etc.
    VIOLATION("E0006", "侵犯名誉/隐私/著作/肖像权等"),
    OTHERS("E0007", "其他原因"),
    ;
    private final String code;
    private final String desc;
}
