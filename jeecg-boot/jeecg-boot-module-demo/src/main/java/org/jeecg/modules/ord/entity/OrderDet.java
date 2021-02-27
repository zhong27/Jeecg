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
 * @Description: 订单明细表
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("order_det")
@ApiModel(value="order_booking对象", description="订单预定")
public class OrderDet implements Serializable {
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
	/**主表id*/
	@ApiModelProperty(value = "主表id")
	private java.lang.String orderId;
	/**材料名称*/
	@Excel(name = "材料名称", width = 15)
	@ApiModelProperty(value = "材料名称")
	private java.lang.String productName;
	/**产品大类*/
	@Excel(name = "产品大类", width = 15)
    @Dict(dicCode = "product_class")
	@ApiModelProperty(value = "产品大类")
	private java.lang.String productClass;
	/**材料长度*/
	@Excel(name = "材料长度", width = 15)
	@ApiModelProperty(value = "材料长度")
	private java.math.BigDecimal matLen;
	/**材料宽度*/
	@Excel(name = "材料宽度", width = 15)
	@ApiModelProperty(value = "材料宽度")
	private java.math.BigDecimal matWidth;
	/**材料厚度*/
	@Excel(name = "材料厚度", width = 15)
	@ApiModelProperty(value = "材料厚度")
	private java.math.BigDecimal matThick;
	/**材料号*/
	@Excel(name = "材料号", width = 15)
	@ApiModelProperty(value = "材料号")
	private java.lang.String matNo;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
    @Dict(dicCode = "id",dicText = "house_name",dictTable = "sto_warehouse")
	@ApiModelProperty(value = "仓库")
	private java.lang.String warehouse;
	/**单价*/
	@Excel(name = "单价", width = 15)
	@ApiModelProperty(value = "单价")
	private java.math.BigDecimal price;
	/**数量*/
	@Excel(name = "数量", width = 15)
	@ApiModelProperty(value = "数量")
	private java.lang.Integer num;
	/**总价*/
	@Excel(name = "总价", width = 15)
	@ApiModelProperty(value = "总价")
	private java.math.BigDecimal total;
}
