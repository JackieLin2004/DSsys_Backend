package ynu.jackielin.dssys_backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import ynu.jackielin.dssys_backend.entity.vo.response.TeacherVO;
import ynu.jackielin.dssys_backend.service.AdministratorService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdministratorController {

    @Resource
    AdministratorService service;

    @PostMapping ("/add-teacher")
    public void addTeacher(@RequestBody TeacherVO vo) {
        service.addTeacher(vo);
    }

    @GetMapping("/get-teachers")
    public List<TeacherVO> showTeachers() {
        return service.showTeachers();
    }

}
