package com.social.whales.netty;

import com.social.whales.netty.handler.StompChatHandler;
import com.social.whales.netty.server.StompWebSocketChatServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootApplication
public class WhalesNettyApplication implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory defaultListableBeanFactory;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WhalesNettyApplication.class, args);
        new StompWebSocketChatServer().start(Integer.parseInt(System.getProperty("port", "8500")));
    }

    /**
     *设置此对象运行的ApplicationContext。通常此调用将用于初始化对象。
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        defaultListableBeanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 获取容器
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        String className = clazz.getName();
        defaultListableBeanFactory.registerBeanDefinition(className, beanDefinitionBuilder.getBeanDefinition());
        return (T) applicationContext.getBean(className);
    }

    /**
     * 释放容器
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> void  destroyBean(Class<T> clazz){
        String className = clazz.getName();
        defaultListableBeanFactory.removeBeanDefinition(className);
        System.out.println("destroy " + className);
    }
}
