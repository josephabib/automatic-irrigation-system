package com.banque.misr.service;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import com.banque.misr.repository.PlotConfigurationRepository;
import com.banque.misr.repository.PlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PlotService.class})
@ExtendWith(SpringExtension.class)
class PlotServiceTest {
    @MockBean
    private PlotConfigurationRepository plotConfigurationRepository;

    @MockBean
    private PlotRepository plotRepository;

    @Autowired
    private PlotService plotService;


    public Plot preparePlot1() {
        PlotConfiguration plotConfiguration = new PlotConfiguration();
        plotConfiguration.setId(1L);
        plotConfiguration.setTimeSlot("Time Slot");
        plotConfiguration.setWaterAmount(1);

        Plot plot = new Plot();
        plot.setAddress("42 Main St");
        plot.setArea(1);
        plot.setAttributesList(new ArrayList<>());
        plot.setId(1L);
        plot.setIsIrrigated(1);
        plot.setPlotConfiguration(plotConfiguration);
        plot.setSlopeAngle(1);
        return plot;
    }

    public Plot preparePlot2() {
        PlotConfiguration plotConfiguration2 = new PlotConfiguration();
        plotConfiguration2.setId(1L);
        plotConfiguration2.setTimeSlot("Time Slot");
        plotConfiguration2.setWaterAmount(1);

        Plot plot2 = new Plot();
        plot2.setAddress("42 Main St");
        plot2.setArea(1);
        plot2.setAttributesList(new ArrayList<>());
        plot2.setId(1L);
        plot2.setIsIrrigated(1);
        plot2.setPlotConfiguration(plotConfiguration2);
        plot2.setSlopeAngle(1);
        return plot2;
    }


    @Test
    void testEditPlot() {
        when(plotRepository.save(Mockito.<Plot>any())).thenReturn(preparePlot1());
        when(plotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> plotService.editPlot(preparePlot2(), 1L));
        verify(plotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testConfigurePlot() throws ResourceNotFoundException {
        Optional<Plot> ofResult = Optional.of(preparePlot1());
        when(plotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doNothing().when(plotConfigurationRepository).configurePlot(Mockito.<PlotConfiguration>any(), anyLong());

        PlotConfiguration plotConfiguration = new PlotConfiguration();
        plotConfiguration.setId(1L);
        plotConfiguration.setTimeSlot("Time Slot");
        plotConfiguration.setWaterAmount(1);
        plotService.configurePlot(plotConfiguration, 1L);
        verify(plotRepository).findById(Mockito.<Long>any());
        verify(plotConfigurationRepository).configurePlot(Mockito.<PlotConfiguration>any(), anyLong());
        assertEquals(1L, plotConfiguration.getId());
        assertEquals(1, plotConfiguration.getWaterAmount());
        assertEquals("Time Slot", plotConfiguration.getTimeSlot());
    }


}