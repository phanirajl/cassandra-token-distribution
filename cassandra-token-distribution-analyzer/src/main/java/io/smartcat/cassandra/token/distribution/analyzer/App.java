package io.smartcat.cassandra.token.distribution.analyzer;

import io.smartcat.cassandra.token.distribution.analyzer.utils.Key;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.Host;

public class App {

    public static void main(String[] args) throws IOException {
        /*
         * HashMap<Host, List<Set<Key>>> hostToKeysMap = KeysDistributionCalculator.calculateKeysDistribution(
         * "10.22.12.90", "rts", new SimpleUuidKeyProcessor(),
         * "/home/stefan/Public/Read input files for V12, V13 and V14 tests/V14/1_2016-03-15_14.txt");
         * 
         * for (Host h : hostToKeysMap.keySet()) { System.out.println(h.getAddress() + " : " +
         * hostToKeysMap.get(h).get(0).size() + ", " + hostToKeysMap.get(h).get(1).size() + ", " +
         * hostToKeysMap.get(h).get(2).size()); }
         */
    }

}
