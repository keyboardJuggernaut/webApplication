package it.polimi.parkingService.webApplication.analysis.controllers;

import it.polimi.parkingService.webApplication.analysis.DataAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AnalysisController {

    private DataAnalyzer analyzer;

    @Autowired
    public AnalysisController(DataAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    @GetMapping("/analysis")
    public String showAnalysis(Model model) {
        Map<String, Double> barChartData = analyzer.getPeriodicIncome();
        Map<String, Integer> pieChartData = analyzer.getPeriodicBooking();
        model.addAttribute("barChartData", barChartData);
        model.addAttribute("pieChartData", pieChartData);
        return "analysis/analysis";
    }
 }
