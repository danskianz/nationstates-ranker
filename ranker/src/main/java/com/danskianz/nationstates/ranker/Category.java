package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.enumerator.CensusId;
import static com.github.agadar.nationstates.enumerator.CensusId.*;
import java.util.Arrays;
import java.util.List;

/**
 * Scoring Categories and their multipliers.
 *
 * @author Daniel Anzaldo (anye.west@gmail.com) - implementation
 * @author Red Francis Daniel Encabo (redencabo@hotmail.com) - logic
 */
public enum Category {
    CAT_CITIZENSHIP(0.2,
            CIVIL_RIGHTS, POLITICAL_FREEDOM, ECO_FRIENDLINESS, INTEGRITY),
    
    CAT_LIVING_STANDARDS(0.25,
            AVERAGE_INCOME, ENVIRONMENTAL_BEAUTY, HEALTH,
            HUMAN_DEVELOPMENT_INDEX, LIFESPAN, PUBLIC_EDUCATION,
            PUBLIC_HEALTHCARE, PUBLIC_TRANSPORT, SAFETY, SCIENTIFIC_ADVANCEMENT,
            WEATHER),
    
    CAT_ECONOMY(0.2,
            ECONOMY, BLACK_MARKET, ECONOMIC_FREEDOM, EMPLOYMENT,
            INDUSTRY_ARMS_MANUFACTURING, INDUSTRY_AUTOMOBILE_MANUFACTURING,
            INDUSTRY_BASKET_WEAVING, INDUSTRY_BEVERAGE_SALES,
            INDUSTRY_BOOK_PUBLISHING, INDUSTRY_CHEESE_EXPORTS,
            INDUSTRY_FURNITURE_RESTORATION, INDUSTRY_GAMBLING,
            INDUSTRY_INFORMATION_TECHNOLOGY, INDUSTRY_INSURANCE,
            INDUSTRY_MINING, INDUSTRY_PIZZA_DELIVERY, INDUSTRY_RETAIL,
            INDUSTRY_TIMBERWOOD_CHIPPING, INDUSTRY_TROUT_FISHING,
            SECTOR_AGRICULTURE, SECTOR_MANUFACTURING),
    
    CAT_POWER(0.1,
            POPULATION, DEFENSE_FORCES, ECONOMIC_OUTPUT, INFLUENCE,
            INTELLIGENCE, PACIFISM),
    
    CAT_HERITAGE(0.05, CULTURE, RESIDENCY, TOURISM),
    
    CAT_EMPTY(0.0); /* uncategorized census data */

    private final double multiplier;
    private final CensusId[] censusIds;

    private Category(double multiplier, CensusId... censusIds) {
        this.multiplier = multiplier;
        this.censusIds = censusIds;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public List<CensusId> getCensusIds() {
        return Arrays.asList(censusIds);
    }

}
