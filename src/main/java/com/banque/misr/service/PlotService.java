package com.banque.misr.service;

import com.banque.misr.constant.Constants;
import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import com.banque.misr.repository.PlotConfigurationRepository;
import com.banque.misr.repository.PlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlotService {
    PlotRepository plotRepository;
    PlotConfigurationRepository plotConfigurationRepository;

    public PlotService(PlotRepository plotRepository, PlotConfigurationRepository plotConfigurationRepository) {
        this.plotRepository = plotRepository;
        this.plotConfigurationRepository = plotConfigurationRepository;
    }

    public Plot addPlot(Plot plot) {
        return plotRepository.save(plot);
    }

    public Plot editPlot(Plot plot, Long id) throws ResourceNotFoundException {
        Plot plotDb = plotRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return plotRepository.save(plot);
    }

    public void configurePlot(PlotConfiguration plot, Long id) throws ResourceNotFoundException {
        Plot plotDb = plotRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        plotConfigurationRepository.configurePlot(plot, id);
    }

    public List<Plot> getPlots() {
        return (List<Plot>) plotRepository.findAll();
    }

    public List<Plot> getUnIrrigatedPlots() {
        List<Plot> plots = (List<Plot>) plotRepository.findAll();
        return plots.stream().filter(p -> p.getIsIrrigated() == 0).toList();
    }

    public void loadPlotConfigurations() {
        List<Plot> plots = (List<Plot>) plotRepository.findAll();
        if (!plots.isEmpty()) {
            Constants.plotCronJobs.clear();
            for (Plot plot : plots) {
                if (plot.getPlotConfiguration() != null)
                    Constants.plotCronJobs.putIfAbsent(plot, plot.getPlotConfiguration().getTimeSlot());
            }
        }
    }
}
