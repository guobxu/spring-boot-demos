package com.sf.demo.cache.impl;

import com.sf.demo.cache.Cache;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache {

    private RedisTemplate<String, Object> redisTemplate;

    private RedisSerializer getKeySerializer() {
        return redisTemplate.getKeySerializer();
    }

    private RedisSerializer getValueSerializer() {
        return redisTemplate.getValueSerializer();
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public byte[] rawKey(String key) {
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        return keySerializer.serialize(key);
    }

    @Override
    public byte[] rawValue(Object val) {
        RedisSerializer valSerializer = redisTemplate.getValueSerializer();
        return valSerializer.serialize(val);
    }

    @Override
    public Object deserValue(byte[] bytes) {
        RedisSerializer valSerializer = redisTemplate.getValueSerializer();

        return valSerializer.deserialize(bytes);
    }

    @Override
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public List<Long> incr(List<String> keys) {
        List<Object> objList = execPipelined(conn -> {
            for(String key : keys) {
                byte[] keyBytes = rawKey(key);
                conn.incr(keyBytes);
            }

            return null;
        });

        return objAsLong(objList, true);
    }

    @Override
    public Long incrBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public boolean exists(String key) {
//        return redisTemplate.execute((RedisCallback<Boolean>) conn -> conn.exists(key.getBytes()));
        return redisTemplate.hasKey(key);
    }

//    @Override
//    public boolean set(byte[] key, byte[] value, long activeTime) {
//        return redisTemplate.execute((RedisCallback<Boolean>) conn -> {
//            boolean rs = true;
//            conn.set(key, value);
//            if (activeTime > 0) {
//                rs = conn.expire(key, activeTime);
//            }
//            return rs;
//        });
//    }

    @Override
    public void set(String key, Object obj, long activeTime) {
        redisTemplate.opsForValue().set(key, obj, activeTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object obj = get(key);

        return (T)obj;
    }

    @Override
    public Long getAsLong(String key, boolean nullAsZero) {
        Object obj = get(key);

        return objAsLong(obj, nullAsZero);
    }

    @Override
    public List<Long> getAsLong(List<String> keys, boolean nullAsZero) {
        List<Object> objList = redisTemplate.opsForValue().multiGet(keys);

        List<Long> result = new ArrayList<>();
        objList.forEach(obj -> {
            result.add(objAsLong(obj, nullAsZero));
        });

        return result;
    }

    private Long objAsLong(Object obj, boolean nullAsZero) {
        if(obj == null) return nullAsZero ? 0L : null;

        if(obj instanceof Long) {
            return (Long)obj;
        } else if(obj instanceof Integer) {
            return ((Integer) obj).longValue();
        } else {
            return null;
        }
    }

    private List<Long> objAsLong(List<Object> objList, boolean nullAsZero) {
        List<Long> result = new ArrayList<>();
        objList.forEach(obj -> result.add(objAsLong(obj, nullAsZero)));

        return result;
    }

    private Long objAsLong(Object obj) {
        return objAsLong(obj, false);
    }

    @Override
    public Object[] mGet(String... keys) {
        List<Object> objList = redisTemplate.opsForValue().multiGet(Arrays.asList(keys));

        return objList.toArray();
    }

    @Override
    public List<Object> mGet(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public <T> List<T> mGet(List<String> keys, Class<T> clazz) {
        List<Object> objList = redisTemplate.opsForValue().multiGet(keys);

        List<T> converted = new ArrayList<>();
        for(Object obj : objList) converted.add((T)obj);

        return converted;
    }

    private byte[][] toKeyBytes(String... keys) {
        List<String> keyList = Arrays.asList(keys);

        return toKeyBytes(keyList);

    }

    private byte[][] toKeyBytes(List<String> keys) {
        int len = keys.size();
        byte[][] bytes = new byte[len][];

        for(int i = 0; i < len; i++) {
            bytes[i] = rawKey(keys.get(i));
        }

        return bytes;
    }

    private byte[][] toValueBytes(Object... values) {
        int len = values.length;
        byte[][] bytes = new byte[len][];

        RedisSerializer serializer = getValueSerializer();
        for(int i = 0; i <len; i++) {
            bytes[i] = serializer.serialize(values[i]);
        }

        return bytes;
    }

    @Override
    public Object hGet(String hash, String key) {
        return redisTemplate.opsForHash().get(hash, key);
    }

    @Override
    public Long hGetAsLong(String hash, String key, boolean nullAsZero) {
        Object val = hGet(hash, key);
        return objAsLong(val, nullAsZero);
    }

    @Override
    public Map<String, Object> hGetAll(String hash) {
        return redisTemplate.<String, Object>opsForHash().entries(hash);
    }

    @Override
    public void hSet(String hash, String key, Object obj) {
        redisTemplate.opsForHash().put(hash, key, obj);

//        return redisTemplate.execute((RedisCallback<Boolean>) conn -> {
//            Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer)getValueSerializer();
//            byte[] valBytes = serializer.serialize(obj);
//
//            return conn.hSet(hash.getBytes(), key.getBytes(), valBytes);
//        });
    }

    @Override
    public void hMSet(String hash, Map<String, Object> kvs) {
        redisTemplate.<String, Object>opsForHash().putAll(hash, kvs);
    }

//    @Override
//    public void hMSet(String hash, List<String> keys, List<Object> objs) {
//        redisTemplate.<String, Object>opsForHash().putAll();
//
//        redisTemplate.execute((RedisCallback<Boolean>) conn -> {
//            Jackson2JsonRedisSerializer serializer = (Jackson2JsonRedisSerializer) getValueSerializer();
//
//            Map<byte[], byte[]> byteMap = new HashMap<>();
//            for(int i = 0; i < keys.size(); i++) {
//                byteMap.put(keys.get(i).getBytes(), serializer.serialize(objs.get(i)));
//            }
//
//            conn.hMSet(hash.getBytes(), byteMap);
//
//            return Boolean.TRUE;
//        });
//    }

    @Override
    public long hIncrBy(String hash, String key, long delta) {
        return redisTemplate.opsForHash().increment(hash, key, delta);

//        Long val = redisTemplate.execute((RedisCallback<Long>) conn ->
//                conn.hIncrBy(hash.getBytes(), key.getBytes(), delta)
//        );
//
//        return val;
    }



    @Override
    public Long hDel(String hash, String key) {
        return redisTemplate.opsForHash().delete(hash, key);

//        return redisTemplate.execute((RedisCallback<Long>) conn ->
//                conn.hDel(hash.getBytes(), key.getBytes())
//        );
    }

    @Override
    public List<Object> hMGet(String hash, List<String> keys) {
        return redisTemplate.<String, Object>opsForHash().multiGet(hash, keys);
    }

    @Override
    public List<Long> hMGetAsLong(String hash, List<String> keys, boolean nullAsZero) {
        List<Object> objList = redisTemplate.<String, Object>opsForHash().multiGet(hash, keys);

        return objAsLong(objList, nullAsZero);
    }

    @Override
    public List<Object> hMGet(String hash, String[] keys) {
        return hMGet(hash, Arrays.asList(keys));
    }

    @Override
    public Object execCallback(RedisCallback callback) {
        return redisTemplate.execute(callback);
    }

    @Override
    public List<Object> execPipelined(RedisCallback callback) {
        return redisTemplate.executePipelined(callback);
    }

    @Override
    public List<Object> executeSessionCallback(SessionCallback callback) {
        return redisTemplate.<List<Object>>execute(callback);
    }

    @Override
    public boolean setNX(String key, Object obj) {
        return redisTemplate.opsForValue().setIfAbsent(key, obj);
    }

    @Override
    public Set<String> keys(String ptrn) {
        RedisSerializer serializer = getKeySerializer();

        return redisTemplate.execute((RedisCallback<Set<String>>) conn -> {
            Set<byte[]> bytesSet = conn.keys(rawKey(ptrn));

            Set<String> keySet = new HashSet<>();
            if(bytesSet == null) return keySet;

            for(byte[] bytes : bytesSet) {
                keySet.add((String)serializer.deserialize(bytes));
            }

            return keySet;
        });
    }

    @Override
    public boolean setNX(String key, Object obj, long activeTime) {
        if(activeTime > 0) {
            return setNX(rawKey(key), rawValue(obj), activeTime);
        } else {
            return setNX(key, obj);
        }
    }

    private boolean setNX(byte[] key, byte[] value,long activeTime) {
        return redisTemplate.execute((RedisCallback<Boolean>) conn -> {
            boolean rs = conn.setNX(key, value);
            if(rs && activeTime > 0){
                conn.expire(key,activeTime);
            }
            return rs;
        });
    }

    @Override
    public boolean hsetNX(String key, String field, Object obj) {
        return hsetNX(rawKey(key), rawKey(field), rawValue(obj));
    }

    public boolean hsetNX(byte[] key, byte[] field, byte[] value) {
        return redisTemplate.execute((RedisCallback<Boolean>) conn ->
                conn.hSetNX(key, field, value)
        );
    }

    public Long hDel(String key,List<String> keys) {
        return redisTemplate.execute((RedisCallback<Long>) conn -> {
            byte[][] byteArr = new byte[keys.size()][];
            for(int i = 0; i < keys.size(); i++) {
                byteArr[i] = keys.get(i).getBytes();
            }

            return conn.hDel(key.getBytes(), byteArr);
        });
    }

    public boolean expire(String key, long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Double zIncrBy(String key, Object member, double scoreDelta) {
        return redisTemplate.opsForZSet().incrementScore(key, member, scoreDelta);
    }

    @Override
    public <T> List<T> zRange(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRange(key.getBytes(), start, end);

            return bytesCollectionToList(bytesSet);
        });
    }

    @Override
    public <T> List<T> zRevRange(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRevRange(key.getBytes(), start, end);

            return bytesCollectionToList(bytesSet);
        });
    }

    @Override
    public List<Long> zRevRangeAsLong(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<List<Long>>)conn -> {
            Set<byte[]> bytesSet = conn.zRevRange(key.getBytes(), start, end);

            List<Long> vals = new ArrayList<>();
            if(bytesSet == null || bytesSet.size() == 0) return vals;

            RedisSerializer valueSerializer = getValueSerializer();
            for(byte[] bytes : bytesSet) {
                Object obj = valueSerializer.deserialize(bytes);
                vals.add(objAsLong(obj));
            }

            return vals;
        });
    }

    @Override
    public <T> List<T> zRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRangeByScore(key.getBytes(), min, max, offset, count);

            return bytesCollectionToList(bytesSet);
        });
    }

    @Override
    public <T> Map<T, Double> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRangeByScoreWithScores(key.getBytes(), min, max);

            return tupleSetToMap(tupleSet);
        });
    }

    @Override
    public <T> Map<T, Double> zRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRangeByScoreWithScores(key.getBytes(), min, max, offset, count);

            return tupleSetToMap(tupleSet);
        });
    }

    @Override
    public <T> List<T> zRangeByScore(String key, double min, double max) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRangeByScore(key.getBytes(), min, max);

            return bytesCollectionToList(bytesSet);
        });
    }

    @Override
    public <T> List<T> zRevRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRevRangeByScore(key.getBytes(), min, max, offset, count);

            return bytesCollectionToList(bytesSet);
        });
    }

    @Override
    public <T> Map<T, Double> zRevRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRevRangeByScoreWithScores(key.getBytes(), min, max);

            return tupleSetToMap(tupleSet);
        });
    }

    @Override
    public <T> Map<T, Double> zRevRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRevRangeByScoreWithScores(key.getBytes(), min, max, offset, count);

            return tupleSetToMap(tupleSet);
        });
    }

    @Override
    public Long zRemRangeByScore(String key, double min, double max) {
        return redisTemplate.execute((RedisCallback<Long>) conn ->
                conn.zRemRangeByScore(key.getBytes(), min, max)
        );
    }

    @Override
    public <T> List<T> zRevRangeByScore(String key, double min, double max) {
        return redisTemplate.execute((RedisCallback<List<T>>)conn -> {
            Set<byte[]> bytesSet = conn.zRevRangeByScore(key.getBytes(), min, max);

            return bytesCollectionToList(bytesSet);
        });
    }

    private <T> List<T> bytesCollectionToList(Collection<byte[]> bytesCol) {
        List<T> valueList = new ArrayList<>();

        if(bytesCol == null || bytesCol.isEmpty()) return valueList;

        RedisSerializer valueSerializer = getValueSerializer();
        for(byte[] bytes : bytesCol) {
            valueList.add((T)valueSerializer.deserialize(bytes));
        }

        return valueList;
    }

    private <T> List<T> bytesCollectionToList(Collection<byte[]> bytesCol, Class<T> clazz) {
        List<T> valueList = new ArrayList<>();

        if(bytesCol == null || bytesCol.isEmpty()) return valueList;

        RedisSerializer valueSerializer = getValueSerializer();
        for(byte[] bytes : bytesCol) {
            valueList.add((T)valueSerializer.deserialize(bytes));
        }

        return valueList;
    }

    @Override
    public <T> Map<T, Double> zRangeWithScore(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRangeWithScores(key.getBytes(), start, end);

            return tupleSetToMap(tupleSet);
        });
    }

    @Override
    public <T> Map<T, Double> zRevRangeWithScore(String key, long start, long end) {
        return redisTemplate.execute((RedisCallback<Map<T, Double>>)conn -> {
            Set<Tuple> tupleSet = conn.zRevRangeWithScores(key.getBytes(), start, end);

            return tupleSetToMap(tupleSet);
        });
    }

    private <T> Map<T, Double> tupleSetToMap(Set<Tuple> tupleSet) {
        Map<T, Double> memberScores = new LinkedHashMap<>();

        if(tupleSet == null || tupleSet.isEmpty()) return memberScores;

        RedisSerializer valueSerializer = getValueSerializer();
        for(Tuple tuple : tupleSet) {
            Object member = valueSerializer.deserialize(tuple.getValue());

            memberScores.put((T)member, tuple.getScore());
        }

        return memberScores;
    }

    @Override
    public <T> Long zRem(String key, List<T> memberList) {
        return redisTemplate.opsForZSet().remove(key, memberList.toArray());
    }

    @Override
    public <T> Long zRem(String key, T[] members) {
        return redisTemplate.opsForZSet().remove(key, members);
    }

    @Override
    public <T> Long zRem(String key, T member) {
        return redisTemplate.opsForZSet().remove(key, member);
    }

    @Override
    public Long zLen(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public List<Long> zLen(List<String> keys) {
        List<Object> objList = execPipelined(conn -> {
            keys.forEach(key -> conn.zCard(rawKey(key)));

            return null;
        });

        List<Long> result = new ArrayList<>();
        objList.forEach(obj -> result.add((Long)obj));

        return result;
    }

    @Override
    public <T> Boolean zAdd(String key, T member, Double score) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public <T> Long zAdd(String key, List<T> memberList, List<Double> scoreList) {
        Set<ZSetOperations.TypedTuple<Object>> tupleSet = new HashSet<>();
        for(int i = 0; i < memberList.size(); i++) {
            DefaultTypedTuple tuple = new DefaultTypedTuple(memberList.get(i), scoreList.get(i));
            tupleSet.add(tuple);
        }

        return redisTemplate.opsForZSet().add(key, tupleSet);
    }

    @Override
    public <T> Double zScore(String key, T member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public <T> boolean zIsMember(String key, T member) {
        Double score = zScore(key, member);

        return score != null;
    }

    @Override
    public <T> List<Boolean> zIsMember(String key, List<T> memberList) {
        byte[] keyBytes = rawKey(key);
        List<Object> objList = redisTemplate.executePipelined((RedisCallback) conn -> {
            for(T obj : memberList) {
                byte[] valBytes = rawValue(obj);
                conn.zScore(keyBytes, valBytes);
            }

            return null;
        });

        List<Boolean> boolRet = new ArrayList<>();
        for(Object obj : objList) {
            boolRet.add(obj != null);
        }

        return boolRet;
    }

    @Override
    public <T> List<Boolean> zIsMember(List<String> keyList, List<T> memberList) {
        List<Object> objList = redisTemplate.executePipelined((RedisCallback) conn -> {
            for(int i = 0, len = keyList.size(); i < len; i++) {
                byte[] keyBytes = rawKey(keyList.get(i)), valBytes = rawValue(memberList.get(i));
                conn.zScore(keyBytes, valBytes);
            }

            return null;
        });

        List<Boolean> boolRet = new ArrayList<>();
        for(Object obj : objList) {
            boolRet.add(obj != null);
        }

        return boolRet;
    }

    @Override
    public <T> List<Boolean> zIsMember(List<String> keys, T member) {
        List<Object> objList = redisTemplate.executePipelined((RedisCallback) conn -> {
            byte[] valBytes = rawValue(member);
            keys.forEach(key -> conn.zScore(rawKey(key), valBytes));

            return null;
        });

        List<Boolean> boolRet = new ArrayList<>();
        for(Object obj : objList) {
            boolRet.add(obj != null);
        }

        return boolRet;
    }

    @Override
    public <T> Long lPush(String key, List<T> objs) {
        return redisTemplate.opsForList().leftPushAll(key, objs);
    }

    @Override
    public <T> Long lPush(String key, T... objs) {
        return redisTemplate.opsForList().leftPushAll(key, objs);
    }

    @Override
    public void lTrim(String key, long start, long stop) {
        redisTemplate.opsForList().trim(key, start, stop);
    }

    @Override
    public <T> List<T> lRange(String key, long start, long stop) {
        return redisTemplate.execute((RedisCallback<List<T>>) conn -> {
            List<byte[]> bytesList= conn.lRange(rawKey(key), start, stop);

            return bytesCollectionToList(bytesList);
        });
    }

    @Override
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    //    @Override
//    public List<Object> lRange(String key, int start, int stop) {
//        return redisTemplate.opsForList().range(key, start, stop);
//    }

    @Override
    public Object rPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public <T> T rPop(String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForList().rightPop(key);
        return (T)obj;
    }

    @Override
    public <T> Long sAdd(String key, List<T> objList) {
        return redisTemplate.opsForSet().add(key, objList.toArray());
    }

    @Override
    public <T> Long sAdd(String key, T... objs) {
        return redisTemplate.opsForSet().add(key, objs);
    }

    @Override
    public <T> Long sRem(String key, T... objs) {
        return redisTemplate.opsForSet().remove(key, objs);
    }

    @Override
    public boolean sIsMember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    @Override
    public <T> List<Boolean> sIsMember(String key, List<T> memberList) {
        byte[] keyBytes = rawKey(key);
        List<Object> objRet = redisTemplate.executePipelined((RedisCallback) conn -> {
            for(Object member : memberList) {
                byte[] valBytes = rawValue(member);
                conn.sIsMember(keyBytes, valBytes);
            }

            return null;
        });

        List<Boolean> boolRet = new ArrayList<>();
        for(Object obj : objRet) {
            boolRet.add((Boolean)obj);
        }

        return boolRet;
    }

    @Override
    public <T> List<Boolean> sIsMember(List<String> keyList, List<T> memberList) {
        List<Object> objRet = redisTemplate.executePipelined((RedisCallback) conn -> {
            for(int i = 0, len = keyList.size(); i < len; i++) {
                byte[] keyBytes = rawKey(keyList.get(i)), valBytes = rawValue(memberList.get(i));
                conn.sIsMember(keyBytes, valBytes);
            }

            return null;
        });

        List<Boolean> boolRet = new ArrayList<>();
        for(Object obj : objRet) {
            boolRet.add((Boolean)obj);
        }

        return boolRet;
    }

}
