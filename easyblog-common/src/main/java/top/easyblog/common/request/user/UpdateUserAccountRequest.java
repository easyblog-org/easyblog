package top.easyblog.common.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: frank.huang
 * @date: 2023-02-25 14:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserAccountRequest {
    private String nickName;

    private Integer active;

    private List<String> roles;

    private Integer identityType;

    private String email;

    private String password;
}
