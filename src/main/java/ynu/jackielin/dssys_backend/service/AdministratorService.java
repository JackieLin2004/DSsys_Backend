package ynu.jackielin.dssys_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ynu.jackielin.dssys_backend.entity.dto.Account;
import ynu.jackielin.dssys_backend.entity.vo.response.TeacherVO;

import java.util.List;

public interface AdministratorService extends IService<Account> {
    void addTeacher(TeacherVO vo);
    List<TeacherVO> showTeachers();
}
