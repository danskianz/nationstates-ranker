/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danskianz.nationstates.ranker;

import com.github.agadar.nationstates.NationStates;
import com.github.agadar.nationstates.domain.DailyDumpNations;
import com.github.agadar.nationstates.domain.DailyDumpRegions;
import com.github.agadar.nationstates.domain.nation.Nation;
import com.github.agadar.nationstates.domain.region.Region;
import com.github.agadar.nationstates.enumerator.DailyDumpMode;

/**
 *
 * @author Daniel Anzaldo (anye.west@gmail.com)
 */
public class DailyDumps {
    
    public Region getRegion(String region) {
        DailyDumpRegions regionDump = getRegionDump();

        for (Region land : regionDump.regions) {
            if (land.name.equalsIgnoreCase(region)) {
                return land;
            }
        }
        return null;
    }

    public Nation getNation(String nation) {
        DailyDumpNations nationDump = getNationDump();

        for (Nation country : nationDump.nations) {
            if (country.name.equalsIgnoreCase(nation)) {
                return country;
            }
        }
        return null;
    }
    
    private static DailyDumpRegions getRegionDump() {
        return NationStates.regiondump(DailyDumpMode.READ_LOCAL).execute();
    }
    
    private static DailyDumpNations getNationDump() {
        return NationStates.nationdump(DailyDumpMode.READ_LOCAL).execute();
    }
}
