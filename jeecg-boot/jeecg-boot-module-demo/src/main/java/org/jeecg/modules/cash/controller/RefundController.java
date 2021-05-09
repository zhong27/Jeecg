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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.cash.RefundCheckEnum;
import org.jeecg.modules.cash.entity.Refund;
import org.jeecg.modules.cash.mapper.RefundMapper;
import org.jeecg.modules.cash.service.IRefundService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.cash.service.impl.RefundServiceImpl;
import org.jeecg.modules.ord.BillStatusEnum;
import org.jeecg.modules.ord.entity.OrderBill;
import org.jeecg.modules.ord.mapper.OrderBillMapper;
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
 * @Description: 退款管理
 * @Author: jeecg-boot
 * @Date:   2021-03-22
 * @Version: V1.0
 */
@Api(tags="退款管理")
@RestController
@RequestMapping("/cash/refund")
@Slf4j
public class RefundController extends JeecgController<Refund, IRefundService> {
	@Autowired
	private IRefundService refundService;
	@Autowired
	private OrderBillMapper orderBillMapper;
	@Autowired
	private RefundServiceImpl refundServiceimp;
	 @Autowired
	 private ISysBaseAPI sysBaseAPI;
	
	/**
	 * 分页列表查询
	 *
	 * @param refund
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "退款管理-分页列表查询")
	@ApiOperation(value="退款管理-分页列表查询", notes="退款管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Refund refund,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Refund> queryWrapper = QueryGenerator.initQueryWrapper(refund, req.getParameterMap());
		Page<Refund> page = new Page<Refund>(pageNo, pageSize);
		IPage<Refund> pageList = refundService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param refund
	 * @return
	 */
	@AutoLog(value = "退款管理-添加")
	@ApiOperation(value="退款管理-添加", notes="退款管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Refund refund) {
		refundService.save(refund);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param refund
	 * @return
	 */
	@AutoLog(value = "退款管理-编辑")
	@ApiOperation(value="退款管理-编辑", notes="退款管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Refund refund) {
		refundService.updateById(refund);
		return Result.OK("编辑成功!");
	}

	 /**
	  * 提单退款
	  *
	  * @param orderBillId
	  * @return
	  */
	 @AutoLog(value = "提单退单")
	 @ApiOperation(value="提单退单", notes="提单退单")
	 @GetMapping(value = "/refundBill")
	 public Result<?> refundBill(@RequestParam(name="id",required=true) String orderBillId) {
		 String result = "";
		 //更新提单状态
		 OrderBill orderBill = orderBillMapper.selectById(orderBillId);
		 //未取件，提单状态设为已退货
		 if (orderBill.getBillStatus().equals(BillStatusEnum.TAKING.getValue())){
		 	result = refundServiceimp.addRefund(orderBillId);
		 	orderBill.setBillStatus(BillStatusEnum.REFUNDED.getValue());
		 }
		 if (orderBill.getBillStatus().equals(BillStatusEnum.PICKED_UP.getValue())){
			 orderBill.setBillStatus(BillStatusEnum.REFUNDING.getValue());
		 }
		 orderBillMapper.updateById(orderBill);
		 return Result.OK(result);
	 }

	 /**
	  * 退款确认
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "退款确认")
	 @ApiOperation(value="退款确认", notes="退款确认")
	 @GetMapping(value = "/refundVerify")
	 public Result<?> refundVerify(@RequestParam(name="id",required=true) String id,String status) {
		 refundServiceimp.refundVerify(id, status);
		 return Result.OK();
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "退款管理-通过id删除")
	@ApiOperation(value="退款管理-通过id删除", notes="退款管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		refundService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "退款管理-批量删除")
	@ApiOperation(value="退款管理-批量删除", notes="退款管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.refundService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "退款管理-通过id查询")
	@ApiOperation(value="退款管理-通过id查询", notes="退款管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Refund refund = refundService.getById(id);
		if(refund==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(refund);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param refund
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Refund refund) {
        return super.exportXls(request, refund, Refund.class, "退款管理");
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
        return super.importExcel(request, response, Refund.class);
    }

}
