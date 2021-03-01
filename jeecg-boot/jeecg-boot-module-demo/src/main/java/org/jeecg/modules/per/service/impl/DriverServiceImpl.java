package org.jeecg.modules.per.service.impl;

import org.jeecg.modules.per.entity.Driver;
import org.jeecg.modules.per.mapper.DriverMapper;
import org.jeecg.modules.per.service.IDriverService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 司机
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Service
public class DriverServiceImpl extends ServiceImpl<DriverMapper, Driver> implements IDriverService {

}
