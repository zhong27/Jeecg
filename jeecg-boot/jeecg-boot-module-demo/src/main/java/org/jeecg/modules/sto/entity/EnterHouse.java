package org.jeecg.modules.sto.entity;

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
 * @Description: 材料入库
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("sto_enter_house")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sto_enter_house对象", description="材料入库")
public class EnterHouse implements Serializable {
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
	/**产品大类*/
	@Excel(name = "产品大类", width = 15)
    @Dict(dicCode = "product_class")
    @ApiModelProperty(value = "产品大类")
    private java.lang.String productClass;
	/**材料名称*/
	@Excel(name = "材料名称", width = 15)
    @ApiModelProperty(value = "材料名称")
    private java.lang.String productName;
	/**材料长度*/
	@Excel(name = "材料长度", width = 15)
    @ApiModelProperty(value = "材料长度")
    private java.math.BigDecimal matLen;
	/**材料厚度*/
	@Excel(name = "材料厚度", width = 15)
    @ApiModelProperty(value = "材料厚度")
    private java.math.BigDecimal matThick;
	/**材料宽度*/
	@Excel(name = "材料宽度", width = 15)
    @ApiModelProperty(value = "材料宽度")
    private java.math.BigDecimal matWidth;
	/**材料重量*/
	@Excel(name = "材料重量", width = 15)
    @ApiModelProperty(value = "材料重量")
    private java.math.BigDecimal matWeight;
	/**材料数量*/
	@Excel(name = "材料数量", width = 15)
    @ApiModelProperty(value = "材料数量")
    private java.lang.Integer matNumber;
	/**材料号*/
	@Excel(name = "材料号", width = 15)
    @ApiModelProperty(value = "材料号")
    private java.lang.String matNo;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
    @Dict(dicCode = "id",dictTable="sto_warehouse",dicText="house_name")
    @ApiModelProperty(value = "仓库")
    private java.lang.String warehouse;
}
