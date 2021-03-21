package org.jeecg.modules.ord.controller;

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
import org.jeecg.modules.ord.entity.OrderDriver;
import org.jeecg.modules.ord.entity.OrderMater;
import org.jeecg.modules.ord.entity.OrderConsignee;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.service.IOrderBillService;
import org.jeecg.modules.ord.service.IOrderDriverService;
import org.jeecg.modules.ord.service.IOrderMaterService;
import org.jeecg.modules.ord.service.IOrderConsigneeService;
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
 * @Description: 提单信息管理
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Api(tags="提单信息管理")
@RestController
@RequestMapping("/ord/orderBill")
@Slf4j
public class OrderBillController extends JeecgController<OrderBill, IOrderBillService> {

	@Autowired
	private IOrderBillService orderBillService;

	@Autowired
	private IOrderDriverService orderDriverService;

	@Autowired
	private IOrderMaterService orderMaterService;

	@Autowired
	private IOrderConsigneeService orderConsigneeService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param orderBill
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "提单信息管理-分页列表查询")
	@ApiOperation(value="提单信息管理-分页列表查询", notes="提单信息管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderBill orderBill,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderBill> queryWrapper = QueryGenerator.initQueryWrapper(orderBill, req.getParameterMap());
		Page<OrderBill> page = new Page<OrderBill>(pageNo, pageSize);
		IPage<OrderBill> pageList = orderBillService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param orderBill
     * @return
     */
    @AutoLog(value = "提单信息管理-添加")
    @ApiOperation(value="提单信息管理-添加", notes="提单信息管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody OrderBill orderBill) {
        orderBillService.save(orderBill);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param orderBill
     * @return
     */
    @AutoLog(value = "提单信息管理-编辑")
    @ApiOperation(value="提单信息管理-编辑", notes="提单信息管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody OrderBill orderBill) {
        orderBillService.updateById(orderBill);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "提单信息管理-通过id删除")
    @ApiOperation(value="提单信息管理-通过id删除", notes="提单信息管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        orderBillService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "提单信息管理-批量删除")
    @ApiOperation(value="提单信息管理-批量删除", notes="提单信息管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.orderBillService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderBill orderBill) {
        return super.exportXls(request, orderBill, OrderBill.class, "提单信息管理");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OrderBill.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-司机信息-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "司机信息-通过主表ID查询")
	@ApiOperation(value="司机信息-通过主表ID查询", notes="司机信息-通过主表ID查询")
	@GetMapping(value = "/listOrderDriverByMainId")
    public Result<?> listOrderDriverByMainId(OrderDriver orderDriver,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<OrderDriver> queryWrapper = QueryGenerator.initQueryWrapper(orderDriver, req.getParameterMap());
        Page<OrderDriver> page = new Page<OrderDriver>(pageNo, pageSize);
        IPage<OrderDriver> pageList = orderDriverService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param orderDriver
	 * @return
	 */
	@AutoLog(value = "司机信息-添加")
	@ApiOperation(value="司机信息-添加", notes="司机信息-添加")
	@PostMapping(value = "/addOrderDriver")
	public Result<?> addOrderDriver(@RequestBody OrderDriver orderDriver) {
		orderDriverService.save(orderDriver);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param orderDriver
	 * @return
	 */
	@AutoLog(value = "司机信息-编辑")
	@ApiOperation(value="司机信息-编辑", notes="司机信息-编辑")
	@PutMapping(value = "/editOrderDriver")
	public Result<?> editOrderDriver(@RequestBody OrderDriver orderDriver) {
		orderDriverService.updateById(orderDriver);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "司机信息-通过id删除")
	@ApiOperation(value="司机信息-通过id删除", notes="司机信息-通过id删除")
	@DeleteMapping(value = "/deleteOrderDriver")
	public Result<?> deleteOrderDriver(@RequestParam(name="id",required=true) String id) {
		orderDriverService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "司机信息-批量删除")
	@ApiOperation(value="司机信息-批量删除", notes="司机信息-批量删除")
	@DeleteMapping(value = "/deleteBatchOrderDriver")
	public Result<?> deleteBatchOrderDriver(@RequestParam(name="ids",required=true) String ids) {
	    this.orderDriverService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportOrderDriver")
    public ModelAndView exportOrderDriver(HttpServletRequest request, OrderDriver orderDriver) {
		 // Step.1 组装查询条件
		 QueryWrapper<OrderDriver> queryWrapper = QueryGenerator.initQueryWrapper(orderDriver, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<OrderDriver> pageList = orderDriverService.list(queryWrapper);
		 List<OrderDriver> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "司机信息"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, OrderDriver.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("司机信息报表", "导出人:" + sysUser.getRealname(), "司机信息"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importOrderDriver/{mainId}")
    public Result<?> importOrderDriver(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<OrderDriver> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderDriver.class, params);
				 for (OrderDriver temp : list) {
                    temp.setBillId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 orderDriverService.saveBatch(list);
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

    /*--------------------------------子表处理-司机信息-end----------------------------------------------*/

    /*--------------------------------子表处理-提单材料明细-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "提单材料明细-通过主表ID查询")
	@ApiOperation(value="提单材料明细-通过主表ID查询", notes="提单材料明细-通过主表ID查询")
	@GetMapping(value = "/listOrderMaterByMainId")
    public Result<?> listOrderMaterByMainId(OrderMater orderMater,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<OrderMater> queryWrapper = QueryGenerator.initQueryWrapper(orderMater, req.getParameterMap());
        Page<OrderMater> page = new Page<OrderMater>(pageNo, pageSize);
        IPage<OrderMater> pageList = orderMaterService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param orderMater
	 * @return
	 */
	@AutoLog(value = "提单材料明细-添加")
	@ApiOperation(value="提单材料明细-添加", notes="提单材料明细-添加")
	@PostMapping(value = "/addOrderMater")
	public Result<?> addOrderMater(@RequestBody OrderMater orderMater) {
		orderMaterService.save(orderMater);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param orderMater
	 * @return
	 */
	@AutoLog(value = "提单材料明细-编辑")
	@ApiOperation(value="提单材料明细-编辑", notes="提单材料明细-编辑")
	@PutMapping(value = "/editOrderMater")
	public Result<?> editOrderMater(@RequestBody OrderMater orderMater) {
		orderMaterService.updateById(orderMater);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "提单材料明细-通过id删除")
	@ApiOperation(value="提单材料明细-通过id删除", notes="提单材料明细-通过id删除")
	@DeleteMapping(value = "/deleteOrderMater")
	public Result<?> deleteOrderMater(@RequestParam(name="id",required=true) String id) {
		orderMaterService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "提单材料明细-批量删除")
	@ApiOperation(value="提单材料明细-批量删除", notes="提单材料明细-批量删除")
	@DeleteMapping(value = "/deleteBatchOrderMater")
	public Result<?> deleteBatchOrderMater(@RequestParam(name="ids",required=true) String ids) {
	    this.orderMaterService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportOrderMater")
    public ModelAndView exportOrderMater(HttpServletRequest request, OrderMater orderMater) {
		 // Step.1 组装查询条件
		 QueryWrapper<OrderMater> queryWrapper = QueryGenerator.initQueryWrapper(orderMater, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<OrderMater> pageList = orderMaterService.list(queryWrapper);
		 List<OrderMater> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "提单材料明细"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, OrderMater.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("提单材料明细报表", "导出人:" + sysUser.getRealname(), "提单材料明细"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importOrderMater/{mainId}")
    public Result<?> importOrderMater(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<OrderMater> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderMater.class, params);
				 for (OrderMater temp : list) {
                    temp.setBillId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 orderMaterService.saveBatch(list);
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

    /*--------------------------------子表处理-提单材料明细-end----------------------------------------------*/

    /*--------------------------------子表处理-收料人信息-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "收料人信息-通过主表ID查询")
	@ApiOperation(value="收料人信息-通过主表ID查询", notes="收料人信息-通过主表ID查询")
	@GetMapping(value = "/listOrderConsigneeByMainId")
    public Result<?> listOrderConsigneeByMainId(OrderConsignee orderConsignee,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<OrderConsignee> queryWrapper = QueryGenerator.initQueryWrapper(orderConsignee, req.getParameterMap());
        Page<OrderConsignee> page = new Page<OrderConsignee>(pageNo, pageSize);
        IPage<OrderConsignee> pageList = orderConsigneeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param orderConsignee
	 * @return
	 */
	@AutoLog(value = "收料人信息-添加")
	@ApiOperation(value="收料人信息-添加", notes="收料人信息-添加")
	@PostMapping(value = "/addOrderConsignee")
	public Result<?> addOrderConsignee(@RequestBody OrderConsignee orderConsignee) {
		orderConsigneeService.save(orderConsignee);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param orderConsignee
	 * @return
	 */
	@AutoLog(value = "收料人信息-编辑")
	@ApiOperation(value="收料人信息-编辑", notes="收料人信息-编辑")
	@PutMapping(value = "/editOrderConsignee")
	public Result<?> editOrderConsignee(@RequestBody OrderConsignee orderConsignee) {
		orderConsigneeService.updateById(orderConsignee);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收料人信息-通过id删除")
	@ApiOperation(value="收料人信息-通过id删除", notes="收料人信息-通过id删除")
	@DeleteMapping(value = "/deleteOrderConsignee")
	public Result<?> deleteOrderConsignee(@RequestParam(name="id",required=true) String id) {
		orderConsigneeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "收料人信息-批量删除")
	@ApiOperation(value="收料人信息-批量删除", notes="收料人信息-批量删除")
	@DeleteMapping(value = "/deleteBatchOrderConsignee")
	public Result<?> deleteBatchOrderConsignee(@RequestParam(name="ids",required=true) String ids) {
	    this.orderConsigneeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportOrderConsignee")
    public ModelAndView exportOrderConsignee(HttpServletRequest request, OrderConsignee orderConsignee) {
		 // Step.1 组装查询条件
		 QueryWrapper<OrderConsignee> queryWrapper = QueryGenerator.initQueryWrapper(orderConsignee, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<OrderConsignee> pageList = orderConsigneeService.list(queryWrapper);
		 List<OrderConsignee> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "收料人信息"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, OrderConsignee.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("收料人信息报表", "导出人:" + sysUser.getRealname(), "收料人信息"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importOrderConsignee/{mainId}")
    public Result<?> importOrderConsignee(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<OrderConsignee> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderConsignee.class, params);
				 for (OrderConsignee temp : list) {
                    temp.setBillId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 orderConsigneeService.saveBatch(list);
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

    /*--------------------------------子表处理-收料人信息-end----------------------------------------------*/




}
