package org.jeecg.modules.man.service;

import org.jeecg.modules.man.entity.Bank;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 银行卡信息
 * @Author: jeecg-boot
 * @Date:   2021-02-27
 * @Version: V1.0
 */
public interface IBankService extends IService<Bank> {

	public List<Bank> selectByMainId(String mainId);
}
