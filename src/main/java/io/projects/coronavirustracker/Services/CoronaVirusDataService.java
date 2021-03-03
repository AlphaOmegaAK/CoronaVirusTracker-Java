package io.projects.coronavirustracker.Services;
import io.projects.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service // Create an instance
public class CoronaVirusDataService {

    // US : By State
//    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports_us/01-01-2021.csv";
    // Global
//    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/02-28-2021.csv";




    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct // Execute this method
    @Scheduled(cron = "* * 1 * * *") // Tells when to rerun this scheduled method ( Second, Minute, Hour, Day_of_Month, Month, Day(s)_of_Week )
    public void fetchVirusData() throws IOException, InterruptedException{
        List<LocationStats> newStats = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

//        System.out.println(httpResponse.body()); // Type String : convert to Reader

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        // Working with CSV Format : Commons CSV
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader); // Pass reader rather than file

        for(CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();

//            String state = record.get("Province_State");
            locationStat.setState(record.get("Province/State"));

//            String region = record.get("Country_Region");
            locationStat.setCountry(record.get("Country/Region"));

//            locationStat.setLatestReportedCases(Integer.parseInt(record.get("Last_Update")));
//            locationStat.setLatestReportedCases(Integer.parseInt(record.get(record.size() - 1)));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int previousDayCases = Integer.parseInt(record.get(record.size() - 2));

            locationStat.setLatestReportedCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - previousDayCases);
//            System.out.println(locationStat.getDiffFromPrevDay());
//            System.out.println(locationStat);
            newStats.add(locationStat);

            System.out.println();

//            String lastUpdate = record.get("Last_Update");
//            String latitude = record.get("Lat");
//            String longitude = record.get("Long_");



        }
        this.allStats = newStats; // Help with concurrency



    }

}
