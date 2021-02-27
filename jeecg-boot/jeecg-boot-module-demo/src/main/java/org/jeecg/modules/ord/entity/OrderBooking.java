package org.jeecg.modules.ord.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecgframework.poi.excel.annotation.Excel;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 订单预定
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("order_booking")
@ApiModel(value="order_booking对象", description="订单预定")
public class OrderBooking implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**客户名称*/
    @Excel(name = "客户名称", width = 15, dictTable = "per_customer", dicText = "customer_name", dicCode = "id")
    @Dict(dictTable = "per_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户名称")
    private java.lang.String customer;
	/**订单编号*/
    @Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String orderNo;
	/**订单总价*/
    @Excel(name = "订单总价", width = 15)
    @ApiModelProperty(value = "订单总价")
    private java.math.BigDecimal orderTotal;
	/**司机*/
    @Excel(name = "司机", width = 15)
    @ApiModelProperty(value = "司机")
    private java.lang.String driver;
	/**车牌号*/
    @Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private java.lang.String carNo;
	/**电话号码*/
    @Excel(name = "电话号码", width = 15)
    @ApiModelProperty(value = "电话号码")
    private java.lang.String phone;
	/**支付状态*/
    @Excel(name = "支付状态", width = 15, dicCode = "pay_status")
    @Dict(dicCode = "pay_status")
    @ApiModelProperty(value = "支付状态")
    private java.lang.String payStatus;
}
