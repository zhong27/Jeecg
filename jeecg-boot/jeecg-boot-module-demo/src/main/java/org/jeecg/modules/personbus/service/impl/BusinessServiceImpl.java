package org.jeecg.modules.personbus.service.impl;

import org.jeecg.modules.personbus.entity.Business;
import org.jeecg.modules.personbus.mapper.BusinessMapper;
import org.jeecg.modules.personbus.service.IBusinessService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 业务员
 * @Author: jeecg-boot
 * @Date:   2021-02-26
 * @Version: V1.0
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

}
