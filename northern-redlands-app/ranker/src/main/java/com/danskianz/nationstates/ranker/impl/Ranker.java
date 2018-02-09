package com.danskianz.nationstates.ranker.impl;

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

        if (rank == 1) { /* 1 */
            grade = 20.0;
        } else if (rank < 4) { /* 2 to 3 */
            grade = 19.0;
        } else if (rank < 6) { /* 4 to 5 */
            grade = 18.0;
        } else if (rank < 11) { /* 6 to 10 */
            grade = 17.0;
        } else if (rank < 16) { /* 11 to 15 */
            grade = 16.0;
        } else if (rank < 21) { /* 16 to 20 */
            grade = 15.0;
        } else if (rank < 26) { /* 21 to 25 */
            grade = 14.0;
        } else if (rank < 31) { /* 26 to 30 */
            grade = 13.0;
        } else if (rank < 36) { /* 31 to 35 */
            grade = 12.0;
        } else if (rank < 41) { /* 36 to 40 */
            grade = 11.0;
        } else if (rank < 46) { /* 41 to 45 */
            grade = 10.0;
        } else if (rank < 51) { /* 46 to 50 */
            grade = 9.0;
        } else if (rank < 56) { /* 51 to 55 */
            grade = 8.0;
        } else if (rank < 61) { /* 56 to 60 */
            grade = 7.0;
        } else if (rank < 66) { /* 61 to 65 */
            grade = 6.0;
        } else if (rank < 71) { /* 66 to 70 */
            grade = 5.0;
        } else if (rank < 76) { /* 71 to 75 */
            grade = 4.0;
        } else if (rank < 81) { /* 76 to 80 */
            grade = 3.0;
        } else if (rank < 86) { /* 81 to 85 */
            grade = 2.0;
        } else if (rank < 91) { /* 86 to 90 */
            grade = 1.0;
        } else if (rank < 96) { /* 91 to 95 */
            grade = 0.0;
        } else { /* 96 to 100 */
            grade = -1.0;
        }

        return grade;
    }
}
