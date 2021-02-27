package org.jeecg.modules.stu.service.impl;

import org.jeecg.modules.stu.entity.Student;
import org.jeecg.modules.stu.mapper.StudentMapper;
import org.jeecg.modules.stu.service.IStudentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 学生信息表
 * @Author: jeecg-boot
 * @Date:   2020-12-28
 * @Version: V1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
