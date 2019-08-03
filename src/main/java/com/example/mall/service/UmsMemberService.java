package com.example.mall.service;

import com.example.mall.common.api.CommonResult;

public interface UmsMemberService {
    /**
     * Description 生成验证码
     */
    CommonResult generateAuthCode(String telephone);
    /**
     * Description 判断验证码与手机号是否匹配
     */
    CommonResult verifyAuthCode(String telephone,String authCode);

}
