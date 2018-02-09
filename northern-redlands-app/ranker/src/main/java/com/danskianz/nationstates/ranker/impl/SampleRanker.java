package com.danskianz.nationstates.ranker.impl;

import com.github.agadar.nationstates.domain.common.CensusScore;
import com.github.agadar.nationstates.enumerator.CensusId;
import java.util.List;

/**
 * The core logic of the NationStates Custom Ranker.
 * 
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public class SampleRanker {

    public static synchronized double rank(List<CensusScore> nationScales) {
        double rank = 0.0;
        rank = nationScales.stream()
                .map((census) -> calculateRank(census.id, census.regionalRank))
                .reduce(rank, (total, mappedRank) -> total + mappedRank);
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

    // Feel free to edit this function with your own grading scale.
    // This tapers down from 20, 19 up to 0 for every multiple of 5.
    private static double gradingScale(int rank) {
        if (rank == 1) {
            return 20.0d;
        } else {
            double g = 19.0d - Math.floorDiv(rank, 5);
            return (Double.compare(g, 0.0d) > 0) ? g : 0.0d;
        }
    }
}
