package io.smartcat.cassandra.token.distribution.analyzer;

import io.smartcat.cassandra.token.distribution.analyzer.utils.ClusterClient;
import io.smartcat.cassandra.token.distribution.analyzer.utils.FileProcessor;
import io.smartcat.cassandra.token.distribution.analyzer.utils.IKeyProcessor;
import io.smartcat.cassandra.token.distribution.analyzer.utils.Key;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.Configuration;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

/**
 * Calculates distribution of keys per Cassandra nodes.
 */
public class KeysDistributionCalculator {

    /**
     * Calculates distribution of keys per Cassandra nodes.
     * 
     * @param endpoint Endpoint of the Cassandra cluster.
     * @param keyspaceName Name of the keyspace.
     * @param processor File processor used to parse input files and create keys.
     * @param paths Paths of input files.
     * @return Map of hosts and their keys. For each host, keys are divided into sets, so that i-th set represents keys
     *         for which the host is i-th replica.
     * @throws IOException
     */
    public static HashMap<Host, List<Set<Key>>> calculateKeysDistribution(String endpoint, String keyspaceName,
            IKeyProcessor processor, String... paths) throws IOException {

        HashMap<Host, List<Set<Key>>> hostToKeysMap = new HashMap<Host, List<Set<Key>>>();

        ClusterClient client = new ClusterClient();
        client.connect(endpoint);
        Metadata metadata = client.getMetadata();
        Configuration configuration = client.getConfiguration();
        client.close();

        List<String> lines = FileProcessor.getLines(paths);
        int replicationFactor = HostResolver.getHostsForKey(metadata, configuration, keyspaceName,
                processor.processString(lines.get(0))).size();
        Key key;
        Set<Host> hosts;
        for (String line : lines) {
            key = processor.processString(line);
            hosts = HostResolver.getHostsForKey(metadata, configuration, keyspaceName, key);

            int replicaIndex = 0;
            // Hosts are ordered, so that the first one is the first replica, the second one is the second replica etc.
            for (Host host : hosts)
                linkKeyToHost(hostToKeysMap, key, host, replicaIndex++, replicationFactor);
        }
        return hostToKeysMap;
    }

    /**
     * Links key to its replica host.
     * 
     * @param map Map of hosts and their keys. For each host, keys are divided into sets, so that i-th set represents
     *            keys for which the host is i-th replica.
     * @param key The key,
     * @param host The host.
     * @param replicaIndex Tells us which replica is the host for provided key.
     * @param replicationFactor Replication factor.
     */
    private static void linkKeyToHost(HashMap<Host, List<Set<Key>>> hostToKeysMap, Key key, Host host,
            int replicaIndex, int replicationFactor) {
        // For each host we have multiple sets of keys. The i-th set represents keys for which the host is i-th replica.
        List<Set<Key>> list = hostToKeysMap.get(host);
        if (list == null) {
            list = new ArrayList<Set<Key>>();
            for (int i = 0; i < replicationFactor; i++) {
                list.add(new HashSet<Key>());
            }
            hostToKeysMap.put(host, list);
        }

        // Get set of keys for which this host is given replica (replicationIndex).
        Set<Key> set = list.get(replicaIndex);
        if (set == null) {
            set = new HashSet<Key>();
            list.set(replicaIndex, set);
        }
        set.add(key);
    }

}
