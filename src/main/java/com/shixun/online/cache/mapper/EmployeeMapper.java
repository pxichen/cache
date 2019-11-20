package com.shixun.online.cache.mapper;
import com.shixun.online.cache.pojo.Employee;
import org.apache.ibatis.annotations.*;


@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getEmpById(Integer id);

    @Insert("insert into employee(lastName,email,gender,d_id) values(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmp(Employee employee);

    @Update("update employee set lastName=#{employee.lastName},email=#{employee.email},gender=#{employee.gender},d_id=#{employee.dId} where id=#{employee.id}")
    public void updateEmp(@Param("employee") Employee employee);

    @Delete("delete from employee where id=#{id}")
    public void deleteEmpById(Integer id);

    @Select("select * from employee where lastName=#{lastName}")
    public Employee getEmpByLastName(String lastName);
}
