package com.shixun.online.cache.service;
import com.shixun.online.cache.mapper.EmployeeMapper;
import com.shixun.online.cache.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp")            //抽取缓存的公共配置
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Qualifier("cacheManager")
    @Autowired
    RedisCacheManager cacheManager;

    //key:默认是使用的SimpleKeyGenerator生成
    //@Cacheable(/*value = {"emp"},*/cacheNames = "emp",/*key = "#root.args[0]"*//*key = "#p0"*/keyGenerator = "myKeyGenerator",unless = "#a0>1")
       @Cacheable(/*value = "emp",*/key="#a0")
       public Employee getEmp(Integer id){
           System.out.println("查询"+id+"员工");
         Employee emp = employeeMapper.getEmpById(id);
          return  emp;
       }
//    public Employee getEmp(Integer id){
//        System.out.println("查询"+id+"员工");
//        Employee employee = employeeMapper.getEmpById(id);
//        Cache cache = cacheManager.getCache("emp");
//        cache.put("emp:"+id,employee);
//        return employee;
//    }


    /**
     * @CachePut:既调用方法，又更新缓存
     * 修改了数据库的数据，并同时更新缓存
     *  运行机制：
     *      1、先调用目标方法；
     *      2、将目标方法的结果缓存起来
     */
    @CachePut(/*value = "emp",*/key="#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("update:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict:清除缓存
     *  allEntries=true清空这个缓存中的所有数据
     *  beforeInvocation=true代表清空缓存操作是在方法执行前就执行了，无论方法是否出现异常，缓存都会被清除
     *  没有参数的话，报出异常时不会清除缓存
     */
   @CacheEvict(/*value = "emp",*/key = "#id",allEntries = true/*,beforeInvocation = true*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        //employeeMapper.deleteEmpById(id);
       // int a = 10/0;
    }

    /**
     * @Caching 定义复杂的缓存规则
     */
    @Caching(
            cacheable = {
                    @Cacheable(/*value = "emp",*/key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/key = "#result.id")
            }

    )
    public Employee getEmployeeByLastName(String lastName){
        System.out.println("getEmployeeByLastName:"+lastName);
        return employeeMapper.getEmpByLastName(lastName);
    }
}
