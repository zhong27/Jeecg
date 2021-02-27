package org.jeecg.modules.stu.entity;

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
 * @Description: 学生成绩表
 * @Author: jeecg-boot
 * @Date:   2021-02-09
 * @Version: V1.0
 */
@Data
@TableName("stu_grade")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="stu_grade对象", description="学生成绩表")
public class Grade implements Serializable {
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
	/**语文*/
	@Excel(name = "语文", width = 15)
    @ApiModelProperty(value = "语文")
    private java.lang.Double chinese;
	/**数学*/
	@Excel(name = "数学", width = 15)
    @ApiModelProperty(value = "数学")
    private java.lang.Double math;
	/**英语*/
	@Excel(name = "英语", width = 15)
    @ApiModelProperty(value = "英语")
    private java.lang.Double english;
	/**生物*/
	@Excel(name = "生物", width = 15)
    @ApiModelProperty(value = "生物")
    private java.lang.Double biology;
	/**历史*/
	@Excel(name = "历史", width = 15)
    @ApiModelProperty(value = "历史")
    private java.lang.Double history;
	/**化学*/
	@Excel(name = "化学", width = 15)
    @ApiModelProperty(value = "化学")
    private java.lang.Double chemistry;
	/**物理*/
	@Excel(name = "物理", width = 15)
    @ApiModelProperty(value = "物理")
    private java.lang.Double physics;
	/**地理*/
	@Excel(name = "地理", width = 15)
    @ApiModelProperty(value = "地理")
    private java.lang.Double geography;
    /**学生姓名*/
    @Excel(name = "学生姓名", width = 15)
    @Dict(dicCode = "id",dictTable="student",dicText="stu_name")
    @ApiModelProperty(value = "学生姓名")
    private java.lang.String stuName;
}
