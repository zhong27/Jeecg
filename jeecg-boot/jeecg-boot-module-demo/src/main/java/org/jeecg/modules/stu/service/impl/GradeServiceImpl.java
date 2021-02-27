package org.jeecg.modules.stu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.stu.entity.Grade;
import org.jeecg.modules.stu.mapper.GradeMapper;
import org.jeecg.modules.stu.service.IGradeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date: 2021-02-09
 * @Version: V1.0
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {

    public boolean addStuGrade(String ids) {
        String[] idArr = ids.split(",");
        List<Grade> gradeList = new ArrayList<>();
        for (String id : idArr) {
            Grade grade = new Grade();
            grade.setStuName(id);
            grade.setChinese(0d);
            grade.setMath(0d);
            grade.setEnglish(0d);
            grade.setBiology(0d);
            grade.setChemistry(0d);
            grade.setGeography(0d);
            grade.setHistory(0d);
            grade.setPhysics(0d);
            gradeList.add(grade);
        }
        return super.saveBatch(gradeList);
    }


}
