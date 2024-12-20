package ynu.jackielin.dssys_backend.entity.vo.response;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherVO {
    int id;
    String username;
    String email;
    Date registerTime;
}
