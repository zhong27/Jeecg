package org.jeecg.modules.ord.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 提单管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("order_bill")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="order_bill对象", description="提单管理")
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
	/**客户名称*/
	@Excel(name = "客户名称", width = 15, dictTable = "per_customer", dicText = "customer_name", dicCode = "id")
	@Dict(dictTable = "per_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户名称")
    private java.lang.String customerId;
	/**提单编号*/
	@Excel(name = "提单编号", width = 15)
    @ApiModelProperty(value = "提单编号")
    private java.lang.String billNo;
	/**订单编号*/
	@Excel(name = "订单编号", width = 15)
    @ApiModelProperty(value = "订单编号")
    private java.lang.String orderNo;
	/**产品名称*/
	@Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private java.lang.String productName;
	/**产品大类*/
	@Excel(name = "产品大类", width = 15, dicCode = "product_class")
	@Dict(dicCode = "product_class")
    @ApiModelProperty(value = "产品大类")
    private java.lang.String productClass;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private java.math.BigDecimal total;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;
	/**收货人*/
	@Excel(name = "收货人", width = 15)
    @ApiModelProperty(value = "收货人")
    private java.lang.String consignee;
	/**收货地址*/
	@Excel(name = "收货地址", width = 15)
    @ApiModelProperty(value = "收货地址")
    private java.lang.String consigneeAddress;
	/**总重量*/
	@Excel(name = "总重量", width = 15)
    @ApiModelProperty(value = "总重量")
    private java.math.BigDecimal totalWeight;
    /**业务员*/
    @Excel(name = "业务员", width = 15, dictTable = "per_business", dicText = "name", dicCode = "id")
    @Dict(dictTable = "per_business", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "业务员")
    private java.lang.String business;
    /**司机*/
    @Excel(name = "司机", width = 15)
    @ApiModelProperty(value = "司机")
    private java.lang.String driver;
    /**车牌号*/
    @Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private java.lang.String carNo;
    /**银行*/
    @Excel(name = "银行", width = 15)
    @Dict(dicCode = "bank")
    @ApiModelProperty(value = "银行")
    private java.lang.String bank;
    /**银行账户*/
    @Excel(name = "银行账户", width = 15)
    @ApiModelProperty(value = "银行账户")
    private java.lang.String bankAccount;
}
