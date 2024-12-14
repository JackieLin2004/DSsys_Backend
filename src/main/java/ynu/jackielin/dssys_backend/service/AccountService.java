package ynu.jackielin.dssys_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;
import ynu.jackielin.dssys_backend.entity.dto.Account;
import ynu.jackielin.dssys_backend.entity.vo.request.ConfirmResetVO;
import ynu.jackielin.dssys_backend.entity.vo.request.EmailRegisterVO;
import ynu.jackielin.dssys_backend.entity.vo.request.EmailResetVO;

public interface AccountService extends IService<Account>, UserDetailsService {
    Account findAccountByUsernameOrEmail(String text);

    String registerEmailVerifyCode(String type, String email, String ip);

    String registerEmailAccount(EmailRegisterVO info);

    String resetConfirm(ConfirmResetVO vo);

    String resetEmailAccountPassword(EmailResetVO vo);
}
