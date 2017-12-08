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
    
    CAT_ATMOSPHERE(0.05,
            AVERAGENESS, CHEERFULNESS, COMPASSION, INCLUSIVENESS, NICENESS,
            NUDITY, RECREATIONAL_DRUG_USE, TOURISM),
    
    CAT_INV_ATMOSPHERE(-0.05, CHARMLESSNESS, RUDENESS),
    
    CAT_CITIZENSHIP(0.2,
            CIVIL_RIGHTS, POLITICAL_FREEDOM, POPULATION, AVERAGE_INCOME,
            AVERAGE_INCOME_OF_POOR, AVERAGE_INCOME_OF_RICH, ECO_FRIENDLINESS,
            FREEDOM_FROM_TAXATION, HEALTH, INCOME_EQUALITY, INTELLIGENCE,
            LIFESPAN, POLITICAL_APATHY, SOCIAL_CONSERVATISM, WEAPONIZATION),
    
    CAT_INV_CITIZENSHIP(-0.2,
            DEATH_RATE, IGNORANCE, IDEOLOGICAL_RADICALITY, OBESITY,
            WEALTH_GAPS),
    
    CAT_HERITAGE(0.05, CULTURE, RELIGIOUSNESS, RESIDENCY, TOURISM),
    
    CAT_ECONOMY(0.25,
            BLACK_MARKET, BUSINESS_SUBSIDIZATION, ECONOMIC_FREEDOM,
            ECONOMIC_OUTPUT, ECONOMY, EMPLOYMENT,
            SECTOR_AGRICULTURE, SECTOR_MANUFACTURING),
    
    CAT_POWER(0.15,
            AUTHORITARIANISM, COMPLIANCE, DEFENSE_FORCES, FOREIGN_AID,
            GOVERNMENT_SIZE, INFLUENCE, INTEGRITY, LAW_ENFORCEMENT, PACIFISM),
    
    CAT_INV_POWER(-0.15, CORRUPTION),
    
    CAT_QUALITY(0.25,
            ENVIRONMENTAL_BEAUTY, HUMAN_DEVELOPMENT_INDEX, PUBLIC_EDUCATION,
            PUBLIC_HEALTHCARE, PUBLIC_TRANSPORT, SAFETY, SCIENTIFIC_ADVANCEMENT,
            WEATHER),
    
    CAT_INV_QUALITY(-0.25, CRIME),
    
    CAT_ADVANCED_INDUSTRY(0.07,
            INDUSTRY_ARMS_MANUFACTURING, INDUSTRY_INFORMATION_TECHNOLOGY,
            INDUSTRY_INSURANCE),
    
    CAT_MIDDLE_INDUSTRY(0.05,
            INDUSTRY_AUTOMOBILE_MANUFACTURING, INDUSTRY_BEVERAGE_SALES,
            INDUSTRY_GAMBLING, INDUSTRY_PIZZA_DELIVERY, INDUSTRY_RETAIL),
    
    CAT_EARLY_INDUSTRIES(0.03,
            INDUSTRY_BASKET_WEAVING, INDUSTRY_BOOK_PUBLISHING,
            INDUSTRY_CHEESE_EXPORTS, INDUSTRY_FURNITURE_RESTORATION,
            INDUSTRY_MINING, INDUSTRY_TIMBERWOOD_CHIPPING,
            INDUSTRY_TROUT_FISHING),
    
    CAT_UNCATEGORIZED(0.0); /* uncategorized census data */

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
