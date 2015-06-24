/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Robert
 * @param <K>
 * @param <V>
 */
public class TwoWayHashmap<K extends Object, V extends Object>
{

    private final Map<K, V> forward = new HashMap<>();
    private final Map<V, K> backward = new HashMap<>();

    public synchronized void add(K key, V value)
    {
        forward.put(key, value);
        backward.put(value, key);
    }

    public synchronized boolean hasKeyForward(K key)
    {
        return forward.containsKey(key);
    }
    
    public synchronized boolean hasKeyBackward(V key)
    {
        return backward.containsKey(key);
    }
    
    public synchronized V getForward(K key)
    {
        return forward.get(key);
    }

    public synchronized K getBackward(V key)
    {
        return backward.get(key);
    }
}
