package com.social.whales.netty.listener;

import com.social.whales.netty.server.StompWebSocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class NettyRunServletContextListener implements ServletContextListener {

    @Autowired
    StompWebSocketChatServer stompWebSocketChatServer;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void  contextDestroyed(ServletContextEvent sce) {

    }

    @Override
    public  void contextInitialized(ServletContextEvent sce){
        //WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
        this.initializerStrategy();
    }

    private void initializerStrategy() {
        //applicationContext.getBeansWithAnnotation()
    }
}
