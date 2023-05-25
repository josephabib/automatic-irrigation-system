package com.banque.misr.threads;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.service.IrrigationService;
import com.banque.misr.service.PlotService;

public class PlotIrrigationThread implements Runnable {

    Plot plot;
    IrrigationService irrigationService;
    PlotService plotService;

    public PlotIrrigationThread(Plot plot, IrrigationService irrigationService, PlotService plotService) {
        this.plot = plot;
        this.irrigationService = irrigationService;
        this.plotService = plotService;
    }

    @Override
    public void run() {
        int status = irrigationService.irrigateLand(plot.getId());
        if (status == 1) {
            plot.setIsIrrigated(1);
            try {
                plotService.editPlot(plot, plot.getId());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
