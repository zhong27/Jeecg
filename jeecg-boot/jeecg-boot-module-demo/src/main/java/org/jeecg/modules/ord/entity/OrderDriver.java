package org.jeecg.modules.ord.entity;

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
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
@Data
@TableName("order_driver")
@ApiModel(value="order_bill对象", description="提单信息管理")
public class OrderDriver implements Serializable {
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
	/**姓名*/
	@Excel(name = "姓名", width = 15)
	@ApiModelProperty(value = "姓名")
	private java.lang.String name;
	/**性别*/
	@Excel(name = "性别", width = 15)
	@ApiModelProperty(value = "性别")
	private java.lang.String sex;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
	@ApiModelProperty(value = "车牌号")
	private java.lang.String carNo;
	/**电话号码*/
	@Excel(name = "电话号码", width = 15)
	@ApiModelProperty(value = "电话号码")
	private java.lang.String phone;
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
	@ApiModelProperty(value = "逻辑删除")
	private java.lang.String delFlag;
	/**提单id*/
	@ApiModelProperty(value = "提单id")
	private java.lang.String billId;
}
