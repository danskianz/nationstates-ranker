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

        if (Category.CAT_EMPTY != redsCategory) {
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
        return Category.CAT_EMPTY;
    }

    private static double gradingScale(int rank) {
        double grade;

        if (rank == 1) {
            grade = 100.0;
        } else if (rank < 6) {
            grade = 90.0;
        } else if (rank < 11) {
            grade = 80.0;
        } else if (rank < 16) {
            grade = 70.0;
        } else if (rank < 21) {
            grade = 60.0;
        } else if (rank < 26) {
            grade = 50.0;
        } else if (rank < 69) {
            grade = 0.0;
        } else {
            grade = -50.0;
        }

        return grade;
    }
}
