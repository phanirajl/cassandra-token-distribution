package io.smartcat.cassandra.token.distribution.analyzer.utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Configuration;
import com.datastax.driver.core.Metadata;

/**
 * Utility class for communication with Cassandra cluster.
 */
public class ClusterClient {

    /**
     * The cluster.
     */
    private Cluster cluster;

    /**
     * Connects to Cassandra cluster.
     * 
     * @param node IP address of a node
     */
    public void connect(String node) {
        cluster = Cluster.builder().addContactPoint(node).build();
    }

    /**
     * Closes the cluster connection.
     */
    public void close() {
        cluster.close();
    }

    /**
     * Gets cluster metadata.
     * 
     * @return Cluster metadata
     */
    public Metadata getMetadata() {
        return cluster.getMetadata();
    }

    /**
     * Gets cluster configuration.
     * 
     * @return Cluster configuration
     */
    public Configuration getConfiguration() {
        return cluster.getConfiguration();
    }

}
