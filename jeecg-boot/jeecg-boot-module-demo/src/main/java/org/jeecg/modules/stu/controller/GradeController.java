package org.jeecg.modules.stu.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.stu.entity.Grade;
import org.jeecg.modules.stu.service.IGradeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.stu.service.impl.GradeServiceImpl;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-02-09
 * @Version: V1.0
 */
@Api(tags="学生成绩表")
@RestController
@RequestMapping("/stu/grade")
@Slf4j
public class GradeController extends JeecgController<Grade, IGradeService> {
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private GradeServiceImpl gradeServiceImpl;
	
	/**
	 * 分页列表查询
	 *
	 * @param grade
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "学生成绩表-分页列表查询")
	@ApiOperation(value="学生成绩表-分页列表查询", notes="学生成绩表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Grade grade,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Grade> queryWrapper = QueryGenerator.initQueryWrapper(grade, req.getParameterMap());
		Page<Grade> page = new Page<Grade>(pageNo, pageSize);
		IPage<Grade> pageList = gradeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param grade
	 * @return
	 */
	@AutoLog(value = "学生成绩表-添加")
	@ApiOperation(value="学生成绩表-添加", notes="学生成绩表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Grade grade) {
		gradeService.save(grade);
		return Result.OK("添加成功！");
	}

	 @AutoLog(value = "添加")
	 @ApiOperation(value="添加", notes="添加")
	 @GetMapping(value = "/addStuGrade")
	 public Result<?> addStuGrade(@RequestParam(name="ids",required=true) String ids) {
		 gradeServiceImpl.addStuGrade(ids);
		 return Result.OK("信息添加成功！");
	 }
	
	/**
	 *  编辑
	 *
	 * @param grade
	 * @return
	 */
	@AutoLog(value = "学生成绩表-编辑")
	@ApiOperation(value="学生成绩表-编辑", notes="学生成绩表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Grade grade) {
		gradeService.updateById(grade);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩表-通过id删除")
	@ApiOperation(value="学生成绩表-通过id删除", notes="学生成绩表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		gradeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "学生成绩表-批量删除")
	@ApiOperation(value="学生成绩表-批量删除", notes="学生成绩表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gradeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "学生成绩表-通过id查询")
	@ApiOperation(value="学生成绩表-通过id查询", notes="学生成绩表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Grade grade = gradeService.getById(id);
		if(grade==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(grade);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param grade
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Grade grade) {
        return super.exportXls(request, grade, Grade.class, "学生成绩表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Grade.class);
    }

}
