package org.jeecg.modules.per.mapper;

import java.util.List;
import org.jeecg.modules.per.entity.Bank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 银行卡信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface BankMapper extends BaseMapper<Bank> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<Bank> selectByMainId(@Param("mainId") String mainId);

}
