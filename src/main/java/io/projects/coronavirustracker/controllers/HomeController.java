package io.projects.coronavirustracker.controllers;

import io.projects.coronavirustracker.Services.CoronaVirusDataService;
import io.projects.coronavirustracker.models.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;


    @GetMapping("/") // root url
    public String home(Model model) {
//        model.addAttribute("testName", "Test");
//        model.addAttribute("locationStats", coronaVirusDataService.getAllStats()); //Before adding total cases


        List<LocationStats> allStats = coronaVirusDataService.getAllStats();

        int totalCases = allStats.stream().mapToInt(stat -> stat.getLatestReportedCases()).sum();
        // Create an int variable and take a list of objects(allStats) converting it to a stream and mapping each object into an integer value for totalReportedCases
        // for that object and take that stream of each object and sum it up

        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();


        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);


        return "home"; // /template/home.html **Thymeleaf
    }



}
