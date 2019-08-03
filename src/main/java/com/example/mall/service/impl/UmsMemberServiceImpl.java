package com.example.mall.service.impl;

import com.example.mall.common.api.CommonResult;
import com.example.mall.service.RedisService;
import com.example.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @Auther: Alex
 * @Date: 2019/8/2 23:00
 * @Description:
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTHCODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Override
    public CommonResult generateAuthCode(String telephone) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            sb.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREFIX_AUTHCODE+telephone,sb.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTHCODE+telephone,AUTH_CODE_EXPIRE_SECONDS);
        return CommonResult.success(sb.toString());
    }

    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {
        if(StringUtils.isEmpty(authCode)){
            return CommonResult.failed("请输入验证码");
        }
        String realAuthCode = redisService.get(REDIS_KEY_PREFIX_AUTHCODE+telephone);
        boolean result = authCode.equals(realAuthCode);
        if (result){
            return CommonResult.success(null,"验证成功");
        }else {
            return CommonResult.failed("验证码错误");
        }
    }
}
