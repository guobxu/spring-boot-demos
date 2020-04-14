package com.sf.demo.locale;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Creator: 01380994
 * Date: 2020/4/10
 */
@Component("localeMessages")
public class LocaleMessages {

    @Autowired
    private MessageSource messageSource;

    public String get(String code) {
        return messageSource.getMessage(code, null, Locale.CHINESE);
    }

    public String get(String code, String lang) {
        if(lang == null) {
            return get(code);
        }

        return messageSource.getMessage(code, null, new Locale(lang));
    }

    public String getByRpcContext(String code) {
        String lang = RpcContext.getContext().getAttachment("lang");

        return get(code, lang);
    }

}
