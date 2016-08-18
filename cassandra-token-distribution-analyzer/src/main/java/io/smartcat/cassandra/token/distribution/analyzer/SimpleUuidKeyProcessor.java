package io.smartcat.cassandra.token.distribution.analyzer;

import io.smartcat.cassandra.token.distribution.analyzer.utils.IKeyProcessor;
import io.smartcat.cassandra.token.distribution.analyzer.utils.Key;
import io.smartcat.cassandra.token.distribution.analyzer.utils.KeyElement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.DataType;

/**
 * Transforms list of strings into list of UUID keys.
 */
public class SimpleUuidKeyProcessor implements IKeyProcessor {

    /**
     * Processes list of strings and creates list of UUID keys. All strings are expected to be in UUID format.
     * 
     * @param strings List of strings to be processed
     * @return List of keys
     */
    public List<Key> processStrings(List<String> strings) {
        List<Key> keys = new ArrayList<Key>();
        for (String string : strings) {
            Key key = processString(string);
            keys.add(key);
        }
        return keys;
    }

    /**
     * Transforms sting into UUID key. The string is expected to be in UUID format.
     * 
     * @param string String to be transformed
     * @return Key
     */
    public Key processString(String string) {
        Key key = new Key();
        key.addElement(new KeyElement(null, DataType.uuid(), UUID.fromString(string.trim())));
        return key;
    }

}
