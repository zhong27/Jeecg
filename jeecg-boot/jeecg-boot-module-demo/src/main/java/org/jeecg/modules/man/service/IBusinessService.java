package org.jeecg.modules.man.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.SneakyThrows;
import org.jeecg.modules.JeecgException;
import org.jeecg.modules.man.entity.Business;

import java.util.Date;

/**
 * @Description: 业务员
 * @Author: jeecg-boot
 * @Date: 2021-02-26
 * @Version: V1.0
 */
public interface IBusinessService extends IService<Business> {
    @SneakyThrows
    @Override
    //save添加
    default boolean save(Business business) {

        //判断生日是否大于当前时间
        if (DateUtil.compare(new Date(), business.getBirth()) < 0) {
             throw new JeecgException("生日大于当前时间!");
        }

        //设置年龄
        int age = DateUtil.ageOfNow(business.getBirth());
        business.setAge(age);
        return IService.super.save(business);
    }
    @Override
    default boolean updateById(Business business){
        //判断生日是否大于当前时间
        if (DateUtil.compare(new Date(), business.getBirth()) < 0) {
            throw new JeecgException("不可修改，生日大于当前时间！");
        }
        //设置年龄
        int age = DateUtil.ageOfNow(business.getBirth());
        business.setAge(age);
        return SqlHelper.retBool(getBaseMapper().updateById(business));
    }
}
