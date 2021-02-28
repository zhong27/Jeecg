package org.jeecg.modules.ord.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.ord.service.impl.OrderDetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.ord.entity.OrderDet;
import org.jeecg.modules.ord.entity.OrderBooking;
import org.jeecg.modules.ord.service.IOrderBookingService;
import org.jeecg.modules.ord.service.IOrderDetService;
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
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Api(tags="订单预定")
@RestController
@RequestMapping("/ord/orderBooking")
@Slf4j
public class OrderBookingController extends JeecgController<OrderBooking, IOrderBookingService> {

	@Autowired
	private IOrderBookingService orderBookingService;

	@Autowired
	private IOrderDetService orderDetService;

	 @Autowired
	 private OrderDetServiceImpl orderDetServiceImpl;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param orderBooking
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单预定-分页列表查询")
	@ApiOperation(value="订单预定-分页列表查询", notes="订单预定-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderBooking orderBooking,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderBooking> queryWrapper = QueryGenerator.initQueryWrapper(orderBooking, req.getParameterMap());
		Page<OrderBooking> page = new Page<OrderBooking>(pageNo, pageSize);
		IPage<OrderBooking> pageList = orderBookingService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param orderBooking
     * @return
     */
    @AutoLog(value = "订单预定-添加")
    @ApiOperation(value="订单预定-添加", notes="订单预定-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody OrderBooking orderBooking) {
        orderBookingService.save(orderBooking);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param orderBooking
     * @return
     */
    @AutoLog(value = "订单预定-编辑")
    @ApiOperation(value="订单预定-编辑", notes="订单预定-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody OrderBooking orderBooking) {
        orderBookingService.updateById(orderBooking);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "订单预定-通过id删除")
    @ApiOperation(value="订单预定-通过id删除", notes="订单预定-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        orderBookingService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "订单预定-批量删除")
    @ApiOperation(value="订单预定-批量删除", notes="订单预定-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.orderBookingService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderBooking orderBooking) {
        return super.exportXls(request, orderBooking, OrderBooking.class, "订单预定");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OrderBooking.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-订单明细表-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "订单明细表-通过主表ID查询")
	@ApiOperation(value="订单明细表-通过主表ID查询", notes="订单明细表-通过主表ID查询")
	@GetMapping(value = "/listOrderDetByMainId")
    public Result<?> listOrderDetByMainId(OrderDet orderDet,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<OrderDet> queryWrapper = QueryGenerator.initQueryWrapper(orderDet, req.getParameterMap());
        Page<OrderDet> page = new Page<OrderDet>(pageNo, pageSize);
        IPage<OrderDet> pageList = orderDetService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param orderDet
	 * @return
	 */
	@AutoLog(value = "订单明细表-添加")
	@ApiOperation(value="订单明细表-添加", notes="订单明细表-添加")
	@PostMapping(value = "/addOrderDet")
	public Result<?> addOrderDet(@RequestBody OrderDet orderDet) {
		orderDetServiceImpl.saveMain(orderDet);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param orderDet
	 * @return
	 */
	@AutoLog(value = "订单明细表-编辑")
	@ApiOperation(value="订单明细表-编辑", notes="订单明细表-编辑")
	@PutMapping(value = "/editOrderDet")
	public Result<?> editOrderDet(@RequestBody OrderDet orderDet) {
		orderDetServiceImpl.updateOrderDet(orderDet);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单明细表-通过id删除")
	@ApiOperation(value="订单明细表-通过id删除", notes="订单明细表-通过id删除")
	@DeleteMapping(value = "/deleteOrderDet")
	public Result<?> deleteOrderDet(@RequestParam(name="id",required=true) String id) {
		orderDetService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单明细表-批量删除")
	@ApiOperation(value="订单明细表-批量删除", notes="订单明细表-批量删除")
	@DeleteMapping(value = "/deleteBatchOrderDet")
	public Result<?> deleteBatchOrderDet(@RequestParam(name="ids",required=true) String ids) {
	    this.orderDetService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportOrderDet")
    public ModelAndView exportOrderDet(HttpServletRequest request, OrderDet orderDet) {
		 // Step.1 组装查询条件
		 QueryWrapper<OrderDet> queryWrapper = QueryGenerator.initQueryWrapper(orderDet, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<OrderDet> pageList = orderDetService.list(queryWrapper);
		 List<OrderDet> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "订单明细表"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, OrderDet.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("订单明细表报表", "导出人:" + sysUser.getRealname(), "订单明细表"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importOrderDet/{mainId}")
    public Result<?> importOrderDet(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<OrderDet> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderDet.class, params);
				 for (OrderDet temp : list) {
                    temp.setOrderId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 orderDetService.saveBatch(list);
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

    /*--------------------------------子表处理-订单明细表-end----------------------------------------------*/




}
