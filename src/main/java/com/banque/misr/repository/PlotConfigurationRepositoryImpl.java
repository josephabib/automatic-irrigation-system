package com.banque.misr.repository;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public class PlotConfigurationRepositoryImpl implements PlotConfigurationRepository {

    PlotRepository plotRepository;

    public PlotConfigurationRepositoryImpl(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    @Override
    public void configurePlot(PlotConfiguration plotConfiguration, long id) throws ResourceNotFoundException {
        Plot plot = plotRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        plot.setPlotConfiguration(plotConfiguration);
        plotRepository.save(plot);
    }
}
