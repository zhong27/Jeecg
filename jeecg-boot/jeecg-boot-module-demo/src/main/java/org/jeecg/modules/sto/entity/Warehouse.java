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
 * @Description: 仓库管理
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
@Data
@TableName("sto_warehouse")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="sto_warehouse对象", description="仓库管理")
public class Warehouse implements Serializable {
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
	/**仓库名称*/
	@Excel(name = "仓库名称", width = 15)
    @ApiModelProperty(value = "仓库名称")
    private java.lang.String houseName;
	/**仓库地址*/
	@Excel(name = "仓库地址", width = 15)
    @ApiModelProperty(value = "仓库地址")
    private java.lang.String address;
	/**仓库负责人*/
	@Excel(name = "仓库负责人", width = 15)
    @ApiModelProperty(value = "仓库负责人")
    private java.lang.String houseMan;
	/**负责人联系号码*/
	@Excel(name = "负责人联系号码", width = 15)
    @ApiModelProperty(value = "负责人联系号码")
    private java.lang.String phone;
	/**占地面积*/
	@Excel(name = "占地面积", width = 15)
    @ApiModelProperty(value = "占地面积")
    private java.math.BigDecimal area;
    /**逻辑删除*/
    @Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
    private int delFlag;
    /**租户id*/
    @Excel(name = "租户id", width = 15)
    @ApiModelProperty(value = "租户id")
    private java.lang.String tenantId;
}
