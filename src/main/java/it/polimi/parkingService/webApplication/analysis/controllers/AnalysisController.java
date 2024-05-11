package it.polimi.parkingService.webApplication.analysis.controllers;

import it.polimi.parkingService.webApplication.analysis.DataAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.Map;

/**
 * The {@code AnalysisController} handles any analysis related requests
 */
@Controller
public class AnalysisController {

    private final DataAnalyzer analyzer;

    /**
     * Constructs the controller
     * @param analyzer the data analyzer
     */
    @Autowired
    public AnalysisController(DataAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     * Handle any request for analysis request
     * @param model the model for charts
     * @return the view reference
     */
    @GetMapping("/analysis")
    public String showAnalysis(Model model) {
        Map<String, Double> barChartData = analyzer.getPeriodicIncome();
        Map<String, Integer> pieChartData = analyzer.getPeriodicBooking();
        model.addAttribute("barChartData", barChartData);
        model.addAttribute("pieChartData", pieChartData);
        return "analysis/analysis";
    }
 }
