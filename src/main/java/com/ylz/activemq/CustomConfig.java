package com.ylz.activemq;

import java.util.HashMap;  
import java.util.Map;  
import java.util.Properties;  
  
import org.springframework.beans.BeansException;  
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;  
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;  
  
/**
 * 用PropertyPlaceholderConfigurer在加载上下文的时候暴露properties
 * @author Auser
 *
 */
public class CustomConfig extends PropertyPlaceholderConfigurer {  
  
    private static Map<String, String> ctxPropertiesMap;  
  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        ctxPropertiesMap = new HashMap<String, String>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    //static method for accessing context properties  
    public static String getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }  
}  