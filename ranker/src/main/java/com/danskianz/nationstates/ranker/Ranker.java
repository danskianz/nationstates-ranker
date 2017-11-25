package com.danskianz.nationstates.ranker;

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

    public static synchronized double rawScore(List<CensusScore> nationScales) {
        double rawScore = 0.0;
        for (CensusScore scaleScore : nationScales) {
            rawScore += computeScore(scaleScore.id, scaleScore.regionalRank);
        }
        return rawScore;
    }

    private static double computeScore(int censusId, int regionalRanking) {
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
        } else if (rank < 65) {
            grade = 0.0;
        } else {
            grade = -50.0;
        }

        return grade;
    }
}
