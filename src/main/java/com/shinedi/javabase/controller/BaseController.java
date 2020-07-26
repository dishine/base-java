package com.shinedi.javabase.controller;

import com.shinedi.javabase.common.error.BusinessException;
import com.shinedi.javabase.common.error.EmBusinessError;
import com.shinedi.javabase.model.entity.Base;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author D-S
 * Created on 2019/9/20 9:51 上午
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "")
public class BaseController {

    private  int anInt = 1;
    /**
     * 请求参数不正确时的处理程序
     */
    void handleValidResult(BindingResult bindingResult) throws InterruptedException {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        Base base = new Base();
        base.wait();
        base.notify();
    }
    protected  void  test(){
         ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
         readWriteLock.writeLock().tryLock();
         readWriteLock.writeLock().unlock();
         ReentrantLock reentrantLock =new ReentrantLock();
         reentrantLock.getHoldCount();

        AtomicInteger dfsd = new AtomicInteger();
    }
}
