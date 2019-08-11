package com.example.mall.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: Alex
 * @Date: 2019/8/11 09:55
 * @Description:
 */
@Component
public class OrderTimeOutCancelTast {
    private Logger logger = LoggerFactory.getLogger(OrderTimeOutCancelTast.class);

    /**
     * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * 每10分钟扫描一次，扫描设定超时时间之前下的订单，如果没支付则取消该订单
     */
    @Scheduled(cron = "0/10 0 * ? * ?")
    private void cancelTimeOutOrder() {
        // TODO: 2019/5/3 此处应调用取消订单的方法，具体查看mall项目源码
        logger.info("取消订单，并根据sku编号释放锁定库存");
    }
}
