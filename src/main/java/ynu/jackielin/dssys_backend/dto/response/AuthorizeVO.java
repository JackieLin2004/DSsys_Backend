package ynu.jackielin.dssys_backend.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizeVO {
    String username;
    String role;
    String token;
    Date expire;
}
