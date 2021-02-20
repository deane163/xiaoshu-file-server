package com.xiaoshu.controller;

import com.xiaoshu.model.ResultCode;
import com.xiaoshu.model.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 功能说明： 用于控制使用，例如踢出用户登录状态，获得用户当前登录状态等；
 *
 * @ com.xiaoshu.controller
 * <p>
 * Original @Author: deane.jia-贾亮亮,@2021/2/9@11:08
 * <p>
 * Copyright (C)2012-@2021 小树盛凯科技 All rights reserved.
 */
@Slf4j
@RestController
@RequestMapping(value = "/main")
public class MainController {


    @GetMapping(value = "/online/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<String> getUserOnlineStatus(@PathVariable String userId) {
        // TODO
        return new ResultVo(ResultCode.SUCCESS, null);
    }

    @DeleteMapping(value = "/remove/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo<String> removeClientByUserId(@PathVariable String userId) {
        // TODO
        return new ResultVo(ResultCode.SUCCESS, null);
    }
}
