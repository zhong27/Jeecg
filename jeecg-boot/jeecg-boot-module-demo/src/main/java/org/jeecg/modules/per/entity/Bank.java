package org.jeecg.modules.per.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 银行卡信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("per_bank")
@ApiModel(value="per_customer对象", description="客户信息")
public class Bank implements Serializable {
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
	/**开户银行*/
	@Excel(name = "开户银行", width = 15)
    @Dict(dicCode = "bank")
	@ApiModelProperty(value = "开户银行")
	private java.lang.String bankName;
	/**账号*/
	@Excel(name = "账号", width = 15)
	@ApiModelProperty(value = "账号")
	private java.lang.String accountBank;
	/**开户人*/
	@Excel(name = "开户人", width = 15)
	@ApiModelProperty(value = "开户人")
	private java.lang.String accountMan;
	/**电话号码*/
	@Excel(name = "电话号码", width = 15)
	@ApiModelProperty(value = "电话号码")
	private java.lang.String phone;
	/**主表id*/
	@ApiModelProperty(value = "主表id")
	private java.lang.String mainId;
}
