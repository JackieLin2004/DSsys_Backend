package ynu.jackielin.dssys_backend.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVO {
    int id;
    String username;
    String role;
    String token;
    Date expire;
}
