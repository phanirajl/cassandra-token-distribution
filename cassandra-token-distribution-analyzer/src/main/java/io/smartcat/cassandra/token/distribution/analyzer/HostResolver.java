package io.smartcat.cassandra.token.distribution.analyzer;

import io.smartcat.cassandra.token.distribution.analyzer.utils.Key;
import io.smartcat.cassandra.token.distribution.analyzer.utils.KeyElement;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.datastax.driver.core.Configuration;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

/**
 * HostResolver finds hosts (replicas) where keys are stored.
 */
public class HostResolver {

    /**
     * Gets set of hosts where the given key is stored. The set preserves order, i.e. the first host is the primary
     * replica, the second host is the secondary replica etc.
     * 
     * @param clusterMetadata Cluster metadata
     * @param clusterConfiguration Cluster configuration
     * @param keyspaceName Keyspace name
     * @param key The key
     * @return Set of hosts for given key.
     */
    public static Set<Host> getHostsForKey(Metadata clusterMetadata, Configuration clusterConfiguration,
            String keyspaceName, Key key) {

        // We need to binarize the key
        ByteBuffer partitionKey;
        List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
        for (KeyElement element : key.getElements()) {
            ByteBuffer buffer = element.getDatatype().serialize(element.getValue(),
                    clusterConfiguration.getProtocolOptions().getProtocolVersion());
            buffers.add(buffer);
        }
        if (buffers.size() == 1)
            partitionKey = buffers.get(0); // This is different comparing to calling compose (on one value)
        else
            partitionKey = compose(buffers);

        // For NetworkTopologyStrategy and SimpleStrategy computeTokenToReplicaMap method, called in getReplicas,
        // preserves order of replicas (i.e, first replica in the set is the primary etc).
        return clusterMetadata.getReplicas(keyspaceName, partitionKey);

        // for token: partitioner.getToken( partitionKey)
    }

    /**
     * Composes multiple byte buffers into one.
     * 
     * @param buffers Byte buffers
     * @return Composed buffer
     */
    private static ByteBuffer compose(List<ByteBuffer> buffers) {
        int length = 0;
        for (ByteBuffer buffer : buffers)
            length += 2 + buffer.remaining() + 1;

        ByteBuffer out = ByteBuffer.allocate(length);
        for (ByteBuffer buffer : buffers) {
            ByteBuffer bb = buffer.duplicate();
            putShortLength(out, bb.remaining());
            out.put(bb);
            out.put((byte) 0);
        }
        out.flip();
        return out;
    }

    private static void putShortLength(ByteBuffer buffer, int length) {
        buffer.put((byte) ((length >> 8) & 0xFF));
        buffer.put((byte) (length & 0xFF));
    }

}
