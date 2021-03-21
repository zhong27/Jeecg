package org.jeecg.modules.ord.mapper;

import java.util.List;
import org.jeecg.modules.ord.entity.OrderConsignee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 收料人信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface OrderConsigneeMapper extends BaseMapper<OrderConsignee> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<OrderConsignee> selectByMainId(@Param("mainId") String mainId);

}