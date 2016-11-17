package com.musingscafe.grabber;

/**
 * Created by ayadav on 11/17/16.
 */
public interface ICache
{
    /**
     * Returns the object from the cache identified by the given key,
     * or returns null if the object doesn't exist in the cache or
     * is stale and the cache was unable to load it from the persistent
     * storage (if the cache was configured with a backing dataProvider
     * ICache implementation).
     * <p>
     * See {@link #getExisting(Object)} for a version of get() that
     * supports storing null as a valid value.
     *
     * @param key the key identifying the value to retrieve
     * @return the retrieved value, or null
     */
    Object get(Object key);

    /**
     * Returns the object from the cache identified by the given key,
     * or throws a {@link KeyNotFoundException} if the key is not found or
     * is stale and couldn't be loaded from the backing dataProvider.
     * <p>
     * Unlike the {@link #get(Object)} method, this method throws
     * an exception instead of returning null in the case that a key
     * is not found or is stale. This means that null may be stored
     * as a valid value when using this method.
     *
     * @param key the key identifying the value to retrieve
     * @return the retrieved value
     * @throws KeyNotFoundException
     */
    Object getExisting(Object key) throws Exception;

    /**
     * Puts the given object into the cache using the given key. It
     * is left up to the ICache implementation for how the Object
     * key is handled within the cache. Implementations should
     * explicitly document their key handling algorithm.
     * This method will overwrite the existing object with the new
     * object if the key already exists.
     * <p>
     * If there's data provider registered in the ICache instance,
     * the key and value will be put through to the provider as well.
     *
     * @param key the key identifying the value to store
     * @param value the value being stored
     */
    void put(Object key, Object value);

    /**
     * Puts the given ICacheable object into the cache using the key
     * retrieved from the ICacheable object's getCacheKey() method.
     * This method will overwrite the existing object with the new
     * object if the key already exists.
     *
     * @param value the value being stored
     */
    //void put(ICacheable value);

    /**
     * Deletes all entries from this cache instance.
     */
    void hardFlush();

    /**
     * Updates a property that indicates all entries in this cache
     * instance need to be lazily flushed, such that the next request
     * of an item will cause that value to be flushed.
     */
    void softFlush();

    /**
     * Removes the object identified by the given key from the cache.
     * If there's data provider registered in the ICache instance,
     * the key will be deleted from the provider as well.
     *
     * @param key the key identifying the value to remove
     * @return true if the value was found and removed,
     * 			or false if it did not exist
     */
    boolean remove(Object key);
}
