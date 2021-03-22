package org.jeecg.modules.cash.entity;

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
 * @Description: 退款管理
 * @Author: jeecg-boot
 * @Date:   2021-03-22
 * @Version: V1.0
 */
@Data
@TableName("cash_refund")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cash_refund对象", description="退款管理")
public class Refund implements Serializable {
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
	/**退款编号*/
	@Excel(name = "退款编号", width = 15)
    @ApiModelProperty(value = "退款编号")
    private java.lang.String refundNo;
	/**客户*/
	@Excel(name = "客户", width = 15, dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
	@Dict(dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户")
    private java.lang.String customer;
	/**退款金额*/
	@Excel(name = "退款金额", width = 15)
    @ApiModelProperty(value = "退款金额")
    private java.math.BigDecimal refundMoney;
	/**提单编号*/
	@Excel(name = "提单编号", width = 15)
    @ApiModelProperty(value = "提单编号")
    private java.lang.String billNo;
	/**退款类型*/
	@Excel(name = "退款类型", width = 15, dicCode = "refund_type")
	@Dict(dicCode = "refund_type")
    @ApiModelProperty(value = "退款类型")
    private java.lang.String refundType;
	/**退货确认*/
	@Excel(name = "退货确认", width = 15, dicCode = "refund_agree")
	@Dict(dicCode = "refund_agree")
    @ApiModelProperty(value = "退货确认")
    private java.lang.String refundAgree;
	/**退款审核*/
	@Excel(name = "退款审核", width = 15, dicCode = "refund_check")
	@Dict(dicCode = "refund_check")
    @ApiModelProperty(value = "退款审核")
    private java.lang.String refundStatus;
	/**审核人*/
	@Excel(name = "审核人", width = 15)
    @ApiModelProperty(value = "审核人")
    private java.lang.String checker;
	/**退款时间*/
	@Excel(name = "退款时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "退款时间")
    private java.util.Date refundDate;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remaks;
}
