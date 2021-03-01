package org.jeecg.modules.cash.controller;

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
import org.jeecg.modules.cash.entity.CashIncome;
import org.jeecg.modules.cash.service.ICashIncomeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.cash.service.impl.CashIncomeServiceImpl;
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
 * @Description: 来款管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Api(tags="来款管理")
@RestController
@RequestMapping("/cash/cashIncome")
@Slf4j
public class CashIncomeController extends JeecgController<CashIncome, ICashIncomeService> {
	@Autowired
	private ICashIncomeService cashIncomeService;
	 @Autowired
	 CashIncomeServiceImpl cashIncomeServiceImpl;
	/**
	 * 分页列表查询
	 *
	 * @param cashIncome
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "来款管理-分页列表查询")
	@ApiOperation(value="来款管理-分页列表查询", notes="来款管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CashIncome cashIncome,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CashIncome> queryWrapper = QueryGenerator.initQueryWrapper(cashIncome, req.getParameterMap());
		Page<CashIncome> page = new Page<CashIncome>(pageNo, pageSize);
		IPage<CashIncome> pageList = cashIncomeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param cashIncome
	 * @return
	 */
	@AutoLog(value = "来款管理-添加")
	@ApiOperation(value="来款管理-添加", notes="来款管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CashIncome cashIncome) {
		cashIncomeService.save(cashIncome);
		return Result.OK("添加成功！");
	}

	 /**
	  *   来款审核
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "来款审核")
	 @ApiOperation(value="来款审核", notes="来款审核")
	 @GetMapping(value = "/checkIncome")
	 public Result<?> checkIncome(@RequestParam(name="id",required=true) String id) {
		 cashIncomeServiceImpl.checkIncome(id);
		 return Result.OK("信息添加成功！");
	 }


	 /**
	 *  编辑
	 *
	 * @param cashIncome
	 * @return
	 */
	@AutoLog(value = "来款管理-编辑")
	@ApiOperation(value="来款管理-编辑", notes="来款管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CashIncome cashIncome) {
		cashIncomeService.updateById(cashIncome);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "来款管理-通过id删除")
	@ApiOperation(value="来款管理-通过id删除", notes="来款管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		cashIncomeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "来款管理-批量删除")
	@ApiOperation(value="来款管理-批量删除", notes="来款管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.cashIncomeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "来款管理-通过id查询")
	@ApiOperation(value="来款管理-通过id查询", notes="来款管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CashIncome cashIncome = cashIncomeService.getById(id);
		if(cashIncome==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(cashIncome);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param cashIncome
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CashIncome cashIncome) {
        return super.exportXls(request, cashIncome, CashIncome.class, "来款管理");
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
        return super.importExcel(request, response, CashIncome.class);
    }

}
