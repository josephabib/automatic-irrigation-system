package com.banque.misr.controller;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import com.banque.misr.service.IrrigationService;
import com.banque.misr.service.PlotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PlotController.class})
@ExtendWith(SpringExtension.class)
class PlotControllerTest {
    @MockBean
    private IrrigationService irrigationService;

    @Autowired
    private PlotController plotController;

    @MockBean
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
    void testAddPlot() throws Exception {
        when(plotService.addPlot(Mockito.<Plot>any())).thenReturn(preparePlot1());
        String content = (new ObjectMapper()).writeValueAsString(preparePlot2());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/plot/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link PlotController#editPlot(Plot, Long)}
     */
    @Test
    void testEditPlot() throws Exception {
        when(plotService.editPlot(Mockito.<Plot>any(), Mockito.<Long>any())).thenReturn(preparePlot1());

        Plot plot2 = preparePlot2();

        String content = (new ObjectMapper()).writeValueAsString(preparePlot2());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/plot/edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testEditPlot2() throws Exception {
        when(plotService.editPlot(Mockito.<Plot>any(), Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        String content = (new ObjectMapper()).writeValueAsString(preparePlot1());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/plot/edit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testConfigurePlot() throws Exception {
        doNothing().when(plotService).configurePlot(Mockito.<PlotConfiguration>any(), Mockito.<Long>any());

        PlotConfiguration plotConfiguration = new PlotConfiguration();
        plotConfiguration.setId(1L);
        plotConfiguration.setTimeSlot("Time Slot");
        plotConfiguration.setWaterAmount(1);
        String content = (new ObjectMapper()).writeValueAsString(plotConfiguration);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/plot/configure/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testConfigurePlot2() throws Exception {
        doThrow(new ResourceNotFoundException("An error occurred")).when(plotService)
                .configurePlot(Mockito.<PlotConfiguration>any(), Mockito.<Long>any());

        PlotConfiguration plotConfiguration = new PlotConfiguration();
        plotConfiguration.setId(1L);
        plotConfiguration.setTimeSlot("Time Slot");
        plotConfiguration.setWaterAmount(1);
        String content = (new ObjectMapper()).writeValueAsString(plotConfiguration);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/plot/configure/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testIrrigatePlot() throws Exception {
        when(irrigationService.irrigateLand(Mockito.<Long>any())).thenReturn(1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/plot/irrigate/{id}", 1L);
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testIrrigatePlot2() throws Exception {
        when(irrigationService.irrigateLand(Mockito.<Long>any())).thenReturn(1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/plot/irrigate/{id}", 1L);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testListPlot() throws Exception {
        when(plotService.getPlots()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/plot/list");
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testListPlot2() throws Exception {
        when(plotService.getPlots()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/plot/list");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(plotController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

