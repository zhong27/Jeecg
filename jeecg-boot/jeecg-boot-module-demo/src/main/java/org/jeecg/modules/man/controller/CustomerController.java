package org.jeecg.modules.man.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.man.entity.Bank;
import org.jeecg.modules.man.entity.Customer;
import org.jeecg.modules.man.service.ICustomerService;
import org.jeecg.modules.man.service.IBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Api(tags="客户信息")
@RestController
@RequestMapping("/per/customer")
@Slf4j
public class CustomerController extends JeecgController<Customer, ICustomerService> {

	@Autowired
	private ICustomerService customerService;

	@Autowired
	private IBankService bankService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param customer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "客户信息-分页列表查询")
	@ApiOperation(value="客户信息-分页列表查询", notes="客户信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Customer customer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Customer> queryWrapper = QueryGenerator.initQueryWrapper(customer, req.getParameterMap());
		Page<Customer> page = new Page<Customer>(pageNo, pageSize);
		IPage<Customer> pageList = customerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param customer
     * @return
     */
    @AutoLog(value = "客户信息-添加")
    @ApiOperation(value="客户信息-添加", notes="客户信息-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Customer customer) {
        customerService.save(customer);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param customer
     * @return
     */
    @AutoLog(value = "客户信息-编辑")
    @ApiOperation(value="客户信息-编辑", notes="客户信息-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Customer customer) {
        customerService.updateById(customer);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "客户信息-通过id删除")
    @ApiOperation(value="客户信息-通过id删除", notes="客户信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        customerService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "客户信息-批量删除")
    @ApiOperation(value="客户信息-批量删除", notes="客户信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.customerService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Customer customer) {
        return super.exportXls(request, customer, Customer.class, "客户信息");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Customer.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-银行卡信息-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "银行卡信息-通过主表ID查询")
	@ApiOperation(value="银行卡信息-通过主表ID查询", notes="银行卡信息-通过主表ID查询")
	@GetMapping(value = "/listBankByMainId")
    public Result<?> listBankByMainId(Bank bank,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<Bank> queryWrapper = QueryGenerator.initQueryWrapper(bank, req.getParameterMap());
        Page<Bank> page = new Page<Bank>(pageNo, pageSize);
        IPage<Bank> pageList = bankService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param bank
	 * @return
	 */
	@AutoLog(value = "银行卡信息-添加")
	@ApiOperation(value="银行卡信息-添加", notes="银行卡信息-添加")
	@PostMapping(value = "/addBank")
	public Result<?> addBank(@RequestBody Bank bank) {
		bankService.save(bank);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param bank
	 * @return
	 */
	@AutoLog(value = "银行卡信息-编辑")
	@ApiOperation(value="银行卡信息-编辑", notes="银行卡信息-编辑")
	@PutMapping(value = "/editBank")
	public Result<?> editBank(@RequestBody Bank bank) {
		bankService.updateById(bank);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "银行卡信息-通过id删除")
	@ApiOperation(value="银行卡信息-通过id删除", notes="银行卡信息-通过id删除")
	@DeleteMapping(value = "/deleteBank")
	public Result<?> deleteBank(@RequestParam(name="id",required=true) String id) {
		bankService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "银行卡信息-批量删除")
	@ApiOperation(value="银行卡信息-批量删除", notes="银行卡信息-批量删除")
	@DeleteMapping(value = "/deleteBatchBank")
	public Result<?> deleteBatchBank(@RequestParam(name="ids",required=true) String ids) {
	    this.bankService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportBank")
    public ModelAndView exportBank(HttpServletRequest request, Bank bank) {
		 // Step.1 组装查询条件
		 QueryWrapper<Bank> queryWrapper = QueryGenerator.initQueryWrapper(bank, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<Bank> pageList = bankService.list(queryWrapper);
		 List<Bank> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "银行卡信息"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, Bank.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("银行卡信息报表", "导出人:" + sysUser.getRealname(), "银行卡信息"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importBank/{mainId}")
    public Result<?> importBank(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<Bank> list = ExcelImportUtil.importExcel(file.getInputStream(), Bank.class, params);
				 for (Bank temp : list) {
                    temp.setMainId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 bankService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-银行卡信息-end----------------------------------------------*/




}
