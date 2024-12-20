package ynu.jackielin.dssys_backend;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ynu.jackielin.dssys_backend.service.AdministratorService;

@SpringBootTest
class DSsysBackendApplicationTests {

    @Resource
    AdministratorService service;

    @Test
    void contextLoads() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    void test1() {
        System.out.println(service.showTeachers());
    }

}
