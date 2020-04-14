package com.sf.demo.aop;

import com.alibaba.dubbo.rpc.RpcContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@Aspect
@Configuration
public class RpcLocaleAop {

    private static final String KEY_LANG = "lang";
    private static final String DEFAULT_LANG = "zh";

    @Around("execution(public * com.sf.demo.controller.*.*(..))")
    public Object setRpcLang(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        if(args == null || args.length == 0) {
            return pjp.proceed(args);
        }

        Object lastArg = args[args.length - 1];
        if(!( lastArg instanceof HttpServletRequest )) {
            return pjp.proceed(args);
        }

        String lang = ((HttpServletRequest)lastArg).getHeader(KEY_LANG);
        if(lang != null) {
            RpcContext.getContext().setAttachment(KEY_LANG, lang);
        }

        return pjp.proceed(args);
    }

}


