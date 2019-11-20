package com.shixun.online.cache;

import com.shixun.online.cache.mapper.EmployeeMapper;
import com.shixun.online.cache.pojo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTests {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;        //操作K-V都是字符串的
    @Autowired
    RedisTemplate redisTemplate;                    //K-V都是对象的
    @Autowired
    RedisTemplate<Object,Employee> employeeRedisTemplate;

    /**
     * Redis常见五大数据类型
     *  stringRedisTemplate.opsForValue()   String 字符串
     *  stringRedisTemplate.opsForList()   List  列表
     *  stringRedisTemplate.opsForSet()     Set  集合
     *  stringRedisTemplate.opsForZSet()   ZSet  有序集合
     *  stringRedisTemplate.opsForHash()   Hash  散列
     */
    @Test
    public void test1(){
        //stringRedisTemplate.opsForValue().append("ss1","你好啊");
        //stringRedisTemplate.opsForList().leftPush("List1","第一个值");
       // stringRedisTemplate.opsForSet().add("set","第一个set");
       /* stringRedisTemplate.opsForZSet().add("list2","有序集合01",1);
        stringRedisTemplate.opsForZSet().add("list2","有序集合02",2);*/
       stringRedisTemplate.opsForHash().put("hash","hs01","value值");
    }

    /**
     * 测试缓存对象
     * 默认保存对象 ，使用JDK序列化机制
     * Jackson2JsonRedisSerializer将数据以json的方式缓存
     *  （1）把对象转换成json格式
     *   2）改变了默认的序列话规则
     */
    @Test
    public void test2(){
        Employee employee = employeeMapper.getEmpById(1);
        //redisTemplate.opsForValue().set("emp1",employee);
        //employeeRedisTemplate.opsForValue().set("myEmployee",employee);
        redisTemplate.opsForValue().set("myEmployee",employee);
    }

    @Test
    public void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println("==============:"+empById);
    }

}
