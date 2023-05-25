package com.banque.misr.config;

import com.banque.misr.threads.ConfigurationThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class Listener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            System.out.println("Config Thread Started");
            taskExecutor.execute(ctx.getBean(ConfigurationThread.class));
        }
    }
}