package ynu.jackielin.dssys_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import ynu.jackielin.dssys_backend.entity.dto.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
