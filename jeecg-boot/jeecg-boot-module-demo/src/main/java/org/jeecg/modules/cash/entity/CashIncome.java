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
 * @Description: 来款管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("cash_income")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cash_income对象", description="来款管理")
public class CashIncome implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
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
	/**客户*/
	@Excel(name = "客户", width = 15, dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
	@Dict(dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "客户")
    private java.lang.String customerName;
	/**来款金额*/
	@Excel(name = "来款金额", width = 15)
    @ApiModelProperty(value = "来款金额")
    private java.math.BigDecimal income;
	/**来款银行*/
	@Excel(name = "来款银行", width = 15, dicCode = "bank")
	@Dict(dicCode = "bank")
    @ApiModelProperty(value = "来款银行")
    private java.lang.String bank;
	/**审核状态*/
	@Excel(name = "审核状态", width = 15)
    @Dict(dicCode = "check_status")
    @ApiModelProperty(value = "审核状态")
    private java.lang.String status;
	/**来款日期*/
	@Excel(name = "来款日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "来款日期")
    private java.util.Date incomeDate;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
    /**来款编号*/
    @Excel(name = "来款编号", width = 15)
    @ApiModelProperty(value = "来款编号")
    private java.lang.String incomeNo;
    /**审核人*/
    @Excel(name = "审核人", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "id")
    @ApiModelProperty(value = "审核人")
    private java.lang.String checker;
    /**逻辑删除*/
    @Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
    private int delFlag;
    /**租户id*/
    @Excel(name = "租户id", width = 15)
    @ApiModelProperty(value = "租户id")
    private java.lang.String tenantId;
}
