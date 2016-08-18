package io.smartcat.cassandra.token.distribution.analyzer.utils;

import java.util.List;

/**
 * Describes methods used for transforming strings into keys.
 */
public interface IKeyProcessor {

    /**
     * Processes list of strings and creates list of keys.
     * 
     * @param strings List of strings to be processed
     * @return List of keys
     */
    List<Key> processStrings(List<String> strings);

    /**
     * Transforms string into key.
     * 
     * @param string String to be transformed
     * @return Key
     */
    Key processString(String string);

}
