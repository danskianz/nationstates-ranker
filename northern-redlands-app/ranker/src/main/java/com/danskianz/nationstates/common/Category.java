package com.danskianz.nationstates.common;

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

    CAT_ADVENTURE(0.05,
            CHEERFULNESS, COMPASSION, INCLUSIVENESS, NICENESS, NUDITY,
            RECREATIONAL_DRUG_USE, TOURISM),
    
    CAT_INV_ADVENTURE(-0.05,
            AVERAGENESS, CHARMLESSNESS, OBESITY, RUDENESS),
    
    CAT_CITIZENSHIP(0.20,
            CIVIL_RIGHTS, POLITICAL_FREEDOM, POPULATION, AVERAGE_INCOME,
            AVERAGE_INCOME_OF_POOR, AVERAGE_INCOME_OF_RICH, ECO_FRIENDLINESS,
            FREEDOM_FROM_TAXATION, HEALTH, INCOME_EQUALITY, INTELLIGENCE,
            LIFESPAN, WELFARE),
    
    CAT_INV_CITIZENSHIP(-0.20,
            DEATH_RATE, IGNORANCE, POLITICAL_APATHY, IDEOLOGICAL_RADICALITY,
            SOCIAL_CONSERVATISM, TAXATION),
    
    CAT_HERITAGE(0.05, CULTURE, RELIGIOUSNESS, RESIDENCY),
    
    CAT_ECONOMY(0.20,
            ECONOMY, BUSINESS_SUBSIDIZATION, ECONOMIC_FREEDOM, ECONOMIC_OUTPUT,
            EMPLOYMENT, SECTOR_AGRICULTURE, SECTOR_MANUFACTURING),
    
    CAT_INV_ECONOMY(-0.20, BLACK_MARKET),
    
    CAT_POWER(0.15,
            COMPLIANCE, DEFENSE_FORCES, GOVERNMENT_SIZE, INFLUENCE, INTEGRITY,
            LAW_ENFORCEMENT, PACIFISM),
    
    CAT_INV_POWER(-0.15, AUTHORITARIANISM, CORRUPTION),
    
    CAT_LIVING_STANDARDS(0.20,
            ENVIRONMENTAL_BEAUTY, HUMAN_DEVELOPMENT_INDEX, PUBLIC_EDUCATION,
            PUBLIC_HEALTHCARE, PUBLIC_TRANSPORT, SAFETY, SCIENTIFIC_ADVANCEMENT,
            WEATHER),
    
    CAT_INV_LIVING_STANDARDS(-0.20, CRIME),
    
    CAT_ADVANCED_INDUSTRIES(0.07,
            INDUSTRY_ARMS_MANUFACTURING, INDUSTRY_INFORMATION_TECHNOLOGY,
            INDUSTRY_INSURANCE),
    
    CAT_MIDDLE_INDUSTRIES(0.05,
            INDUSTRY_AUTOMOBILE_MANUFACTURING, INDUSTRY_BEVERAGE_SALES,
            INDUSTRY_GAMBLING, INDUSTRY_PIZZA_DELIVERY, INDUSTRY_RETAIL),
    
    CAT_EARLY_INDUSTRIES(0.03,
            INDUSTRY_BASKET_WEAVING, INDUSTRY_BOOK_PUBLISHING,
            INDUSTRY_CHEESE_EXPORTS, INDUSTRY_FURNITURE_RESTORATION,
            INDUSTRY_MINING, INDUSTRY_TIMBERWOOD_CHIPPING,
            INDUSTRY_TROUT_FISHING),
    
    /* uncategorized census data */
    CAT_EMPTY(0.0);

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
