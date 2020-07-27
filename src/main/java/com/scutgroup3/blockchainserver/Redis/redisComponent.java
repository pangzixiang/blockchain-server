package com.scutgroup3.blockchainserver.Redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class redisComponent {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置缓存失效时间
     * @param key  键
     * @param time 失效时间
     */
    public void setTimeLimit(String key, long time){
        try {
            stringRedisTemplate.expire(key,time, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取失效时间
     * @param key
     * @return 返回失效时间（long), 0为永久， -1为不存在
     */
    public long getTimeLimit(String key){
        try {
            return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断是否有某个键
     * @param key
     * @return 存在为true,不存在为false
     */
    public boolean hasKey(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除键，可传入多个或单个
     * @param key
     */
    public void delKey(String... key){
        if (key.length > 0 && key != null){
            if (key.length == 1){
                stringRedisTemplate.delete(key[0]);
            }else {
                stringRedisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 获取缓存
     * @param key
     * @return 返回键对应的值
     */
    public Object getKey(String key){
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    public Object getObjectKey(String key){
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 存入缓存
     * @param key
     * @param value
     * @param time
     */
    public void setKey(String key, String value, long time){
        try {
            if (time > 0){
                stringRedisTemplate.opsForValue().set(key, value, time);
            } else{
                stringRedisTemplate.opsForValue().set(key,value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param setName
     * @param setValue
     * @param time
     */
    public void setSet(String setName, long time, String... setValue){
        try {
            if (setValue.length > 0 && setValue != null){
                stringRedisTemplate.opsForSet().add(setName,setValue);
                setTimeLimit(setName,time);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * @param setName
     * @param setValue
     * @return
     */
    public boolean checkSet(String setName, String setValue){
        try {
            return stringRedisTemplate.opsForSet().isMember(setName,setValue);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     *
     * @param setName
     * @return
     */
    public Set<String> getSet(String setName){
        try {
            return stringRedisTemplate.opsForSet().members(setName);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void setList(String listName,String listValue){
        try {
            if (listValue != null){
                stringRedisTemplate.opsForList().leftPush(listName,listValue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getList(String listName){
        try {
            return stringRedisTemplate.opsForList().range(listName,0,-1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
