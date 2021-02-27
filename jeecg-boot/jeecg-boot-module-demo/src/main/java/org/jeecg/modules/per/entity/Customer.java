package org.jeecg.modules.per.entity;

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
 * @Description: 客户信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("per_customer")
@ApiModel(value="per_customer对象", description="客户信息")
public class Customer implements Serializable {
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
    @Excel(name = "客户名称", width = 15)
    @ApiModelProperty(value = "客户名称")
    private java.lang.String customerName;
	/**地址*/
    @Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String addres;
	/** 电话号码*/
    @Excel(name = " 电话号码", width = 15)
    @ApiModelProperty(value = " 电话号码")
    private java.lang.String number;
	/**客户类型   */
    @Excel(name = "客户类型   ", width = 15, dicCode = "customer_type")
    @Dict(dicCode = "customer_type")
    @ApiModelProperty(value = "客户类型   ")
    private java.lang.String customerType;
	/**账户状态*/
    @Excel(name = "账户状态", width = 15, dicCode = "account_type")
    @Dict(dicCode = "account_type")
    @ApiModelProperty(value = "账户状态")
    private java.lang.String accountStatus;
	/**营业执照*/
    @Excel(name = "营业执照", width = 15)
    @ApiModelProperty(value = "营业执照")
    private java.lang.String price;
}
