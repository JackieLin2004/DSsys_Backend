package ynu.jackielin.dssys_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ynu.jackielin.dssys_backend.entity.dto.Account;
import ynu.jackielin.dssys_backend.entity.vo.response.TeacherVO;
import ynu.jackielin.dssys_backend.mapper.AccountMapper;
import ynu.jackielin.dssys_backend.service.AdministratorService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AccountMapper, Account> implements AdministratorService {
    @Resource
    PasswordEncoder encoder;

    @Override
    public void addTeacher(TeacherVO vo) {
        String password = encoder.encode("123456");
        Account account = new Account(null, vo.getUsername(), password, vo.getEmail(), "teacher", new Date());
        this.save(account);
    }

    @Override
    public List<TeacherVO> showTeachers() {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", "teacher");
        List<Account> teachers = this.list(queryWrapper);
        return teachers.stream().map(account -> {
            TeacherVO vo = new TeacherVO();
            vo.setId(account.getId());
            vo.setUsername(account.getUsername());
            vo.setEmail(account.getEmail());
            vo.setRegisterTime(account.getRegisterTime());
            return vo;
        }).collect(Collectors.toList());
    }
}
