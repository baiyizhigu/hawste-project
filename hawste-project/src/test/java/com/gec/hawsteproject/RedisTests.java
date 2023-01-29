package com.gec.hawsteproject;

import com.gec.hawsteproject.hawaste.entity.SysOffice;
import com.gec.hawsteproject.hawaste.service.ISysOfficeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisTests {


    /**
     * 默认创建的对象有两个，
     * 一个是泛型为<Object,Object>的RedisTemplate对象
     * 一个是泛型为<String,String>的StringRedisTemplate对象
     */
    @Autowired
    RedisTemplate<String,String> redisTemplate ;

    @Autowired
//    RedisTemplate<Object,Object> objectRedisTemplate;//由于自定义了RedisTemplate对象，原springboot的对象就不会创建了
    RedisTemplate<String,Object> objectRedisTemplate;

    @Autowired
    ISysOfficeService officeService;

    @Test
    void contextLoads() {
        /*for (String key : redisTemplate.keys("*")) {
            System.out.println(key);
        }*/

        for (String music : redisTemplate.opsForList().range("musics", 0, -1)) {
            System.out.println(music);
        }

        System.out.println("--------------");
        for (Object music : objectRedisTemplate.opsForList().range("musics", 0, -1)) {
            System.out.println(music);
        }
    }


    @Test
    public void testStringSerializer(){
        SysOffice office = officeService.getById(2);
        //StringRedistTemplate 需要自己处理java对象到json字符串的转换
//        redisTemplate.opsForValue().set("office:1",office);
        ValueOperations<String, Object> opsForValue = objectRedisTemplate.opsForValue();
        opsForValue.set("office:2",office);
        //使用默认的序列化方式，对LocalDateTime会序列化成对象格式，反序列化的时候无法转换成LocalDateTime
        SysOffice sysOffice = (SysOffice) opsForValue.get("office:2");
        System.out.println(sysOffice);
    }
}
