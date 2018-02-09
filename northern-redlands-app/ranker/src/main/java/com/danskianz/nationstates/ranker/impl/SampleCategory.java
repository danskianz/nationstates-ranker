package com.danskianz.nationstates.ranker.impl;

import com.github.agadar.nationstates.enumerator.CensusId;
import static com.github.agadar.nationstates.enumerator.CensusId.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Sample Scoring Categories and their multipliers.
 * 
 * A Category is composed of a percentage weight and Census ID/s that share the
 * multiplier.
 * 
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public enum SampleCategory {

    CAT_CHARACTER(0.10,
            CHEERFULNESS, COMPASSION, INCLUSIVENESS, NICENESS, NUDITY,
            RECREATIONAL_DRUG_USE, SOCIAL_CONSERVATISM),
    
    CAT_INV_CHARACTER(-0.10,
            AVERAGENESS, CHARMLESSNESS, RUDENESS),
    
    CAT_POLITICS(0.15,
            CIVIL_RIGHTS, POLITICAL_FREEDOM, POPULATION, ECO_FRIENDLINESS,
            FREEDOM_FROM_TAXATION, HEALTH, INCOME_EQUALITY, INTELLIGENCE),
    
    CAT_CIVILIZATION(0.25,
            ENVIRONMENTAL_BEAUTY, LIFESPAN, HUMAN_DEVELOPMENT_INDEX,
            PUBLIC_EDUCATION, PUBLIC_HEALTHCARE, PUBLIC_TRANSPORT, SAFETY,
            SCIENTIFIC_ADVANCEMENT, WEATHER, INTEGRITY, OBESITY, CULTURE,
            RELIGIOUSNESS, RESIDENCY, YOUTH_REBELLIOUSNESS),
    
    CAT_INV_CIVILIZATION(-0.15, CRIME, DEATH_RATE, IGNORANCE, POLITICAL_APATHY,
            IDEOLOGICAL_RADICALITY),
    
    CAT_POWER(0.15,
            AVERAGE_INCOME, COMPLIANCE, DEFENSE_FORCES, GOVERNMENT_SIZE,
            INFLUENCE, LAW_ENFORCEMENT, PACIFISM, AUTHORITARIANISM, CORRUPTION,
            TAXATION),
    
    CAT_ECONOMY(0.15,
            BLACK_MARKET, ECONOMY, BUSINESS_SUBSIDIZATION, ECONOMIC_FREEDOM,
            ECONOMIC_OUTPUT, EMPLOYMENT, SECTOR_AGRICULTURE,
            SECTOR_MANUFACTURING, TOURISM),
    
    CAT_INDUSTRIES(0.20,
            INDUSTRY_ARMS_MANUFACTURING, INDUSTRY_INFORMATION_TECHNOLOGY,
            INDUSTRY_INSURANCE, INDUSTRY_AUTOMOBILE_MANUFACTURING,
            INDUSTRY_BEVERAGE_SALES, INDUSTRY_GAMBLING, INDUSTRY_PIZZA_DELIVERY,
            INDUSTRY_RETAIL, INDUSTRY_BASKET_WEAVING, INDUSTRY_BOOK_PUBLISHING,
            INDUSTRY_CHEESE_EXPORTS, INDUSTRY_FURNITURE_RESTORATION,
            INDUSTRY_MINING, INDUSTRY_TIMBERWOOD_CHIPPING,
            INDUSTRY_TROUT_FISHING),
    
    CAT_EMPTY(0.0); /* uncategorized census data */

    private final double multiplier;
    private final CensusId[] censusIds;

    private static List<CensusId> categorizedCensusIds;
    
    static {
        initializeCategories();
    }

    private SampleCategory(double multiplier, CensusId... censusIds) {
        this.multiplier = multiplier;
        this.censusIds = censusIds;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public List<CensusId> getCensusIds() {
        return Arrays.asList(censusIds);
    }

    public static List<CensusId> getCategorizedCensusIds() {
        if (categorizedCensusIds == null) {
            initializeCategories();
        }
        return Collections.unmodifiableList(categorizedCensusIds);
    }

    private static void initializeCategories() {
        categorizedCensusIds = new ArrayList<>();
        for (SampleCategory base : SampleCategory.values()) {
            categorizedCensusIds.addAll(base.getCensusIds());
        }
    }
}
