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
 * @Description: 资金账户
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("cash_balance")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cash_balance对象", description="资金账户")
public class CashBalance implements Serializable {
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
	/**资金账户*/
	@Excel(name = "资金账户", width = 15, dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
	@Dict(dictTable = "man_customer", dicText = "customer_name", dicCode = "id")
    @ApiModelProperty(value = "资金账户")
    private java.lang.String customerName;
	/**总金额*/
	@Excel(name = "总金额", width = 15)
    @ApiModelProperty(value = "总金额")
    private java.math.BigDecimal totalMoney;
	/**可用余额*/
	@Excel(name = "可用余额", width = 15)
    @ApiModelProperty(value = "可用余额")
    private java.math.BigDecimal remainMoney;
    /**已用金额*/
    @Excel(name = "已用金额", width = 15)
    @ApiModelProperty(value = "已用金额")
    private java.math.BigDecimal amountUsed;
    /**退款金额*/
    @Excel(name = "退款金额", width = 15)
    @ApiModelProperty(value = "退款金额")
    private java.math.BigDecimal refundAmount;
    /**逻辑删除*/
    @Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
    private int delFlag;
    /**租户id*/
    @Excel(name = "租户id", width = 15)
    @ApiModelProperty(value = "租户id")
    private java.lang.String tenantId;
}
