package org.jeecg.modules.man.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.man.entity.Business;
import org.jeecg.modules.man.mapper.BusinessMapper;
import org.jeecg.modules.man.service.IBusinessService;
import org.springframework.stereotype.Service;

/**
 * @Description: 业务员
 * @Author: jeecg-boot
 * @Date:   2021-02-26
 * @Version: V1.0
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

}
