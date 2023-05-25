package com.banque.misr.repository;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.PlotConfiguration;


public interface PlotConfigurationRepository {
    void configurePlot(PlotConfiguration plotConfiguration, long id) throws ResourceNotFoundException;
}
