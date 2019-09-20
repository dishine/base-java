package com.shinedi.javabase.controller;

import com.shinedi.javabase.common.error.BusinessException;
import com.shinedi.javabase.common.error.EmBusinessError;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author D-S
 * Created on 2019/9/20 9:51 上午
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "")
public class BaseController {

    /**
     * 请求参数不正确时的处理程序
     */
    void handleValidResult(BindingResult bindingResult)  {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
    }


}
