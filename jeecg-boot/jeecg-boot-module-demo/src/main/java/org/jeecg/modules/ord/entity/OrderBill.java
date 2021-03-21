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
 * @Description: 提单信息管理
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Data
@TableName("order_bill")
@ApiModel(value="order_bill对象", description="提单信息管理")
public class OrderBill implements Serializable {
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
	/**逻辑删除*/
    @Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
    private int delFlag;
	/**租户*/
    @Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private java.lang.String tenantId;
	/**客户*/
    @Excel(name = "客户", width = 15, dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
    @Dict(dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户")
    private java.lang.String customerId;
	/**提单编号*/
    @Excel(name = "提单编号", width = 15)
    @ApiModelProperty(value = "提单编号")
    private java.lang.String billNo;
	/**订单编号*/
    @Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String orderNo;
	/**业务员*/
    @Excel(name = "业务员", width = 15, dictTable = "man_business", dicText = "name", dicCode = "id")
    @Dict(dictTable = "man_business", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "业务员")
    private java.lang.String business;
	/**总重量*/
    @Excel(name = "总重量", width = 15)
    @ApiModelProperty(value = "总重量")
    private java.math.BigDecimal totalWeight;
	/**总价*/
    @Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private java.math.BigDecimal total;
	/**订单id*/
    @Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;
}
