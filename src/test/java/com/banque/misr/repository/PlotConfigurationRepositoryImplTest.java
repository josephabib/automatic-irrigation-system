package com.banque.misr.repository;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PlotConfigurationRepositoryImpl.class})
@ExtendWith(SpringExtension.class)
class PlotConfigurationRepositoryImplTest {
    @Autowired
    private PlotConfigurationRepositoryImpl plotConfigurationRepositoryImpl;

    @MockBean
    private PlotRepository plotRepository;


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
    void testConfigurePlot() throws ResourceNotFoundException {
        Optional<Plot> ofResult = Optional.of(preparePlot1());

        when(plotRepository.save(Mockito.<Plot>any())).thenReturn(preparePlot2());
        when(plotRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PlotConfiguration plotConfiguration3 = new PlotConfiguration();
        plotConfiguration3.setId(1L);
        plotConfiguration3.setTimeSlot("Time Slot");
        plotConfiguration3.setWaterAmount(1);
        plotConfigurationRepositoryImpl.configurePlot(plotConfiguration3, 1L);
        verify(plotRepository).save(Mockito.<Plot>any());
        verify(plotRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testConfigurePlot2() {
        when(plotRepository.save(Mockito.<Plot>any())).thenReturn(preparePlot1());
        when(plotRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        PlotConfiguration plotConfiguration2 = new PlotConfiguration();
        plotConfiguration2.setId(1L);
        plotConfiguration2.setTimeSlot("Time Slot");
        plotConfiguration2.setWaterAmount(1);
        assertThrows(ResourceNotFoundException.class,
                () -> plotConfigurationRepositoryImpl.configurePlot(plotConfiguration2, 1L));
        verify(plotRepository).findById(Mockito.<Long>any());
    }
}

