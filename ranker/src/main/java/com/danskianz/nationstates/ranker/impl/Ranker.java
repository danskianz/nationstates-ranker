package com.danskianz.nationstates.ranker.impl;

import com.danskianz.nationstates.common.Category;
import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.enumerator.CensusId;
import java.util.List;

/**
 * The core logic of the NationStates Custom Ranker.
 * 
 * @author Daniel Anzaldo (anye.west@gmail.com) - implementation
 * @author Red Francis Daniel Encabo (redencabo@hotmail.com) - logic
 */
public class Ranker {

    public static synchronized double rank(List<CensusScore> nationScales) {
        double rank = 0.0;
        for (CensusScore scaleScore : nationScales) {
            rank += calculateRank(scaleScore.id, scaleScore.regionalRank);
        }
        return rank;
    }

    private static double calculateRank(int censusId, int regionalRanking) {
        Category redsCategory = getCategory(CensusId.fromInt(censusId));

        if (Category.CAT_UNCATEGORIZED != redsCategory) {
            return gradingScale(regionalRanking) * redsCategory.getMultiplier();
        }
        return 0.0;
    }

    private static Category getCategory(CensusId census) {
        for (Category scoringCategory : Category.values()) {
            if (scoringCategory.getCensusIds().contains(census)) {
                return scoringCategory;
            }
        }
        return Category.CAT_UNCATEGORIZED;
    }

    private static double gradingScale(int rank) {
        double grade = 0.0d;
        
        if (rank == 1) {
            grade = 1.2d;
        } else if (rank < 4) {
            grade = 1.1d;
        } else if (rank < 51) {
            int bucket = Math.floorDiv(rank, 5);
            grade = 1.1d - (bucket * 0.1);
        }
        
        return grade;
    }
}
