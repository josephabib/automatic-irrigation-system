package com.banque.misr.threads;

import com.banque.misr.constant.Constants;
import com.banque.misr.models.Plot;
import com.banque.misr.service.IrrigationService;
import com.banque.misr.service.PlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConfigurationThread implements Runnable {
    @Autowired
    private PlotService plotService;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    private IrrigationService irrigationService;
    @Value("${thread.configuration-thread.sleep-time}")
    private String sleepTime;

    @Override
    public void run() {
        while (true) {
            plotService.loadPlotConfigurations();
            for (Map.Entry<Plot, String> plotConfigMap : Constants.plotCronJobs.entrySet()) {
                if (!Constants.threadCronJobs.contains(plotConfigMap.getKey().getId())) {
                    PlotIrrigationThread plotIrrigationThread = new PlotIrrigationThread(plotConfigMap.getKey(), irrigationService, plotService);
                    taskScheduler.schedule(plotIrrigationThread, new CronTrigger(plotConfigMap.getValue()));
                    Constants.threadCronJobs.add(plotConfigMap.getKey().getId());
                }
            }
            try {
                Thread.sleep(Long.parseLong(sleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
