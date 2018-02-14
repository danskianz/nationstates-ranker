package com.danskianz.nationstates.ranker.report;

import com.danskianz.nationstates.common.CalculationMode;
import com.danskianz.nationstates.ranker.RankService;
import com.danskianz.nationstates.ranker.ReportService;
import static com.danskianz.nationstates.ranker.report.BBCode.*;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;

/**
 *
 * @author Daniel Paul Anzaldo <anye.west@gmail.com>
 */
@Named("reportProvider")
@ApplicationScoped
@Service
public class ReportServiceImpl implements ReportService {

    @Inject
    private RankService rankService;
    
    @Override
    public String getBBCodeReport(String region) {
        Map<String, Double> scores = rankService
                .getRegionScores(region, CalculationMode.CACHED);
        
        return buildReport(region, scores);
    }

    private static final DecimalFormat fmt = new DecimalFormat("#0.0000");
    
    private String buildReport(String region, Map<String, Double> scores) {
        Map<String, Double> byRanking = scores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        String title = bold("Rankings for the Region of " + region(region)) + '\n';
        
        StringBuilder sb = new StringBuilder();
        int i = 1;
        sb.append(tableRow(tableData("RANK")
                + tableData("NATION") + tableData("SCORE")));
        for (Map.Entry<String, Double> nation : byRanking.entrySet()) {
            sb.append(tableRow(tableData(String.valueOf(i))
                + tableData(nation(nation.getKey()))
                    + tableData(fmt.format(nation.getValue()))));
            i++;
        }
        return title + table(sb.toString());
    }
    
}
