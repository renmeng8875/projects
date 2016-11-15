package com.yy.ent.platform.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.util.Pool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JedisTemplate 提供了一个template方法，负责对Jedis连接的获取与归还。
 * JedisAction<T> 和 JedisActionNoResult两种回调接口，适用于有无返回值两种情况。
 * <p/>
 * 同时提供一些JedisOperation中定义的 最常用函数的封装, 如get/set/zadd等。
 */
public class RedisTemplate implements InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(RedisTemplate.class);

    protected Pool<Jedis> jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jedisPool, "jedisPool can't be null");
    }

    protected Jedis getJedis() {
        return jedisPool.getResource();
    }

    public RedisTemplate() {
    }

    public RedisTemplate(Pool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * Callback interface for template.
     */
    public interface JedisAction<T> {
        T action(Jedis jedis);
    }

    /**
     * Callback interface for template.
     */
    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }

    public interface Hscan {
        void doInHscan(List<Map.Entry<String, String>> list);
    }

    /**
     * Execute with a call back action without result.
     */
    public <T> T execute(JedisAction<T> jedisAction) {
        Jedis jedis = getJedis();
        try {
            return jedisAction.action(jedis);
        } finally {
            returnSource(jedis);
        }
    }

    public void execute(JedisActionNoResult jedisAction) {
        Jedis jedis = getJedis();
        try {
            jedisAction.action(jedis);
        } finally {
            returnSource(jedis);
        }
    }

    private void returnSource(Jedis jedis) {
        try {
            jedis.close();
        } catch (Exception e) {
            logger.warn("return jedis error!", e);
        }
    }

    /**
     * Return the internal JedisPool.
     */
//    protected JedisPool getJedisPool() {
//        return jedisPool;
//    }


    // / Common Actions ///

    /**
     * Remove the specified keys. If a given key does not exist no operation is
     * performed for this key.
     * <p/>
     * return false if one of the key is not exist.
     */
    public Boolean del(final String... keys) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.del(keys) == keys.length ? true : false;
            }
        });
    }

    public Boolean sismember(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.sismember(key, member);
            }
        });
    }

    public Double zincrby(final String key, final double score, final String member) {
        return execute(new JedisAction<Double>() {
            @Override
            public Double action(Jedis jedis) {
                return jedis.zincrby(key, score, member);
            }
        });
    }

    public void flushDB() {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.flushDB();
            }
        });
    }

    // / String Actions ///

    /**
     * Get the value of the specified key. If the key does not exist null is
     * returned. If the value stored at key is not a string an error is returned
     * because GET can only handle string values.
     */
    public String get(final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    /**
     * Get the value of the specified key as Long.If the key does not exist null is returned.
     */
    public Long getAsLong(final String key) {
        String result = get(key);
        return result != null ? Long.valueOf(result) : null;
    }

    /**
     * Get the value of the specified key as Integer.If the key does not exist null is returned.
     */
    public Integer getAsInt(final String key) {
        String result = get(key);
        return result != null ? Integer.valueOf(result) : null;
    }

    /**
     * Get the values of all the specified keys. If one or more keys dont exist
     * or is not of type String, a 'nil' value is returned instead of the value
     * of the specified key, but the operation never fails.
     */
    public List<String> mget(final String... keys) {
        return execute(new JedisAction<List<String>>() {

            @Override
            public List<String> action(Jedis jedis) {
                return jedis.mget(keys);
            }
        });
    }

    /**
     * Set the string value as value of the key.
     * The string can't be longer than 1073741824 bytes (1 GB).
     */
    public void set(final String key, final String value) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.set(key, value);
            }
        });
    }

    public void setex(final String key, final String value, final int seconds) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.setex(key, seconds, value);
            }
        });
    }

    public Boolean setnx(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.setnx(key, value) == 1 ? true : false;
            }
        });
    }

    /**
     * GETSET is an atomic set this value and return the old value command. Set
     * key to the string value and return the old value stored at key. The
     * string can't be longer than 1073741824 bytes (1 GB).
     */
    public String getSet(final String key, final String value) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.getSet(key, value);
            }
        });
    }

    /**
     * Increment the number stored at key by one. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "0" before
     * to perform the increment operation.
     * <p/>
     * INCR commands are limited to 64 bit signed integers.
     * <p/>
     * Note: this is actually a string operation, that is, in Redis there are not "integer" types. Simply the string
     * stored at the key is parsed as a base 10 64 bit signed integer, incremented, and then converted back as a string.
     *
     * @return Integer reply, this commands will reply with the new value of key
     * after the increment.
     */
    public Long incr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    public Long incrBy(final String key, final long increment) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.incrBy(key, increment);
            }
        });
    }

    public Double incrByFloat(final String key, final double increment) {
        return execute(new JedisAction<Double>() {
            @Override
            public Double action(Jedis jedis) {
                return jedis.incrByFloat(key, increment);
            }
        });
    }

    /**
     * Decrement the number stored at key by one. If the key does not exist or
     * contains a value of a wrong type, set the key to the value of "0" before
     * to perform the decrement operation.
     */
    public Long decr(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.decr(key);
            }
        });
    }

    public Long decrBy(final String key, final long decrement) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.decrBy(key, decrement);
            }
        });
    }

    // / Hash Actions ///

    /**
     * If key holds a hash, retrieve the value associated to the specified
     * field.
     * <p/>
     * If the field is not found or the key does not exist, a special 'nil' value is returned.
     */
    public String hget(final String key, final String fieldName) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.hget(key, fieldName);
            }
        });
    }

    public List<String> hmget(final String key, final String... fieldsNames) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.hmget(key, fieldsNames);
            }
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(new JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public void hset(final String key, final String fieldName, final String value) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.hset(key, fieldName, value);
            }
        });
    }

    public void hmset(final String key, final Map<String, String> map) {
        execute(new JedisActionNoResult() {

            @Override
            public void action(Jedis jedis) {
                jedis.hmset(key, map);
            }
        });

    }

    public Boolean hsetnx(final String key, final String fieldName, final String value) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.hsetnx(key, fieldName, value) == 1 ? true : false;
            }
        });
    }

    public Long hincrBy(final String key, final String fieldName, final long increment) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.hincrBy(key, fieldName, increment);
            }
        });
    }

    public Double hincrByFloat(final String key, final String fieldName, final double increment) {
        return execute(new JedisAction<Double>() {
            @Override
            public Double action(Jedis jedis) {
                return jedis.hincrByFloat(key, fieldName, increment);
            }
        });
    }

    public Long hdel(final String key, final String... fieldsNames) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.hdel(key, fieldsNames);
            }
        });
    }

    public Boolean hexists(final String key, final String fieldName) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.hexists(key, fieldName);
            }
        });
    }

    public Set<String> hkeys(final String key) {
        return execute(new JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.hkeys(key);
            }
        });
    }

    public Long hlen(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.hlen(key);
            }
        });
    }

    // / List Actions ///

    public Long lpush(final String key, final String... values) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.lpush(key, values);
            }
        });
    }

    public String rpop(final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    public String brpop(final int timeout, final String key) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                List<String> nameValuePair = jedis.brpop(timeout, key);
                if (nameValuePair != null) {
                    return nameValuePair.get(1);
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Not support for sharding.
     */
    public String rpoplpush(final String sourceKey, final String destinationKey) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.rpoplpush(sourceKey, destinationKey);
            }
        });
    }

    /**
     * Not support for sharding.
     */
    public String brpoplpush(final String source, final String destination, final int timeout) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.brpoplpush(source, destination, timeout);
            }
        });
    }

    public Long llen(final String key) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    public String lindex(final String key, final long index) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.lindex(key, index);
            }
        });
    }

    public List<String> lrange(final String key, final int start, final int end) {
        return execute(new JedisAction<List<String>>() {

            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    public void ltrim(final String key, final int start, final int end) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.ltrim(key, start, end);
            }
        });
    }

    public void ltrimFromLeft(final String key, final int size) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                jedis.ltrim(key, 0, size - 1);
            }
        });
    }

    public Boolean lremFirst(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 1, value);
                return (count == 1);
            }
        });
    }

    public Boolean lremAll(final String key, final String value) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                Long count = jedis.lrem(key, 0, value);
                return (count > 0);
            }
        });
    }

    // / Set Actions ///
    public Boolean sadd(final String key, final String... members) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.sadd(key, members) == 1 ? true : false;
            }
        });
    }

    public Boolean srem(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.srem(key, member) == 1 ? true : false;
            }
        });
    }

    public Long scard(final String key) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.scard(key);
            }
        });
    }

    public Set<String> smembers(final String key) {
        return execute(new JedisAction<Set<String>>() {

            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(key);
            }
        });
    }

    // / Ordered Set Actions ///

    /**
     * return true for add new element, false for only update the score.
     */
    public Boolean zadd(final String key, final double score, final String member) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zadd(key, score, member) == 1 ? true : false;
            }
        });
    }

    public Double zscore(final String key, final String member) {
        return execute(new JedisAction<Double>() {

            @Override
            public Double action(Jedis jedis) {
                return jedis.zscore(key, member);
            }
        });
    }

    public Long zrank(final String key, final String member) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zrank(key, member);
            }
        });
    }

    public Long zrevrank(final String key, final String member) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zrevrank(key, member);
            }
        });
    }

    public Long zcount(final String key, final double min, final double max) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zcount(key, min, max);
            }
        });
    }

    public Set<String> zrange(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<String>>() {

            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrange(key, start, end);
            }
        });
    }

    public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<Tuple>>() {

            @Override
            public Set<Tuple> action(Jedis jedis) {
                return jedis.zrangeWithScores(key, start, end);
            }
        });
    }

    public Set<String> zrevrange(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<String>>() {

            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrevrange(key, start, end);
            }
        });
    }

    public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int end) {
        return execute(new JedisAction<Set<Tuple>>() {

            @Override
            public Set<Tuple> action(Jedis jedis) {
                return jedis.zrevrangeWithScores(key, start, end);
            }
        });
    }

    public Set<String> zrangeByScore(final String key, final double min, final double max) {
        return execute(new JedisAction<Set<String>>() {

            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrangeByScore(key, min, max);
            }
        });
    }

    public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
        return execute(new JedisAction<Set<Tuple>>() {

            @Override
            public Set<Tuple> action(Jedis jedis) {
                return jedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
        return execute(new JedisAction<Set<String>>() {

            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
        return execute(new JedisAction<Set<Tuple>>() {

            @Override
            public Set<Tuple> action(Jedis jedis) {
                return jedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }

    public Boolean zrem(final String key, final String member) {
        return execute(new JedisAction<Boolean>() {

            @Override
            public Boolean action(Jedis jedis) {
                return jedis.zrem(key, member) == 1 ? true : false;
            }
        });
    }

    public Long zremByScore(final String key, final double start, final double end) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zremrangeByScore(key, start, end);
            }
        });
    }

    public Long zremByRank(final String key, final long start, final long end) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zremrangeByRank(key, start, end);
            }
        });
    }

    public Long zcard(final String key) {
        return execute(new JedisAction<Long>() {

            @Override
            public Long action(Jedis jedis) {
                return jedis.zcard(key);
            }
        });
    }


    public void hscan(final String key, final int count, final Hscan hscanCallback) {
        execute(new JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                logger.info("begin hscan {}", key);
                final ScanParams params = new ScanParams();
                params.count(count);
                ScanResult<Map.Entry<String, String>> hscan;
                String cursor = "0";
                List<Map.Entry<String, String>> list;
                int currentHandle = 0;
                do {
                    hscan = jedis.hscan(key, cursor, params);
                    cursor = hscan.getStringCursor();
                    list = hscan.getResult();
                    if (list != null) {
                        logger.info("hscan currentHandle:{},cursor:{},list.size:{}", currentHandle, cursor, list.size());
                        currentHandle += list.size();
                        hscanCallback.doInHscan(list);
                    }
                } while (!"0".equals(cursor));//redis.cursor为0时表示结束
                logger.info("end hscan {} size:{}", key, currentHandle);
            }
        });
    }
}
