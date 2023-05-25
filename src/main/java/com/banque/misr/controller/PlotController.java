package com.banque.misr.controller;

import com.banque.misr.exception.ResourceNotFoundException;
import com.banque.misr.models.Plot;
import com.banque.misr.models.PlotConfiguration;
import com.banque.misr.service.IrrigationService;
import com.banque.misr.service.PlotService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plot")
public class PlotController {

    PlotService plotService;
    IrrigationService irrigationService;

    public PlotController(PlotService plotService, IrrigationService irrigationService) {
        this.plotService = plotService;
        this.irrigationService = irrigationService;
    }

    @GetMapping("/list")
    public List<Plot> listPlot() {
        return plotService.getPlots();
    }

    @PostMapping("/add")
    public void addPlot(@Validated @RequestBody Plot plot) throws ResourceNotFoundException {
        plotService.addPlot(plot);
    }

    @PutMapping("/edit/{id}")
    public void editPlot(@Validated @RequestBody Plot plot, @PathVariable Long id) throws ResourceNotFoundException {
        plotService.editPlot(plot, id);
    }

    @PutMapping("/configure/{id}")
    public void configurePlot(@Validated @RequestBody PlotConfiguration plotConfiguration, @PathVariable Long id) throws ResourceNotFoundException {
        plotService.configurePlot(plotConfiguration, id);
    }

    @GetMapping("/irrigate/{id}")
    public void irrigatePlot(@PathVariable Long id) {
        System.out.println("new request");
        irrigationService.irrigateLand(id);
    }

}
