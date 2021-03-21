package org.jeecg.modules.ord.mapper;

import java.util.List;
import org.jeecg.modules.ord.entity.OrderDriver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 司机信息
 * @Author: jeecg-boot
 * @Date:   2021-03-21
 * @Version: V1.0
 */
public interface OrderDriverMapper extends BaseMapper<OrderDriver> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<OrderDriver> selectByMainId(@Param("mainId") String mainId);

}
