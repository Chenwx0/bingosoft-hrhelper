package bingosoft.hrhelper.mapper;

import java.util.List;

import bingosoft.hrhelper.form.EmployeeEmailForm;
import bingosoft.hrhelper.model.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    List<Employee> listAllEmployee();

    /**
     * 获取员工邮件地址列表
     * @param employeeName
     * @return
     */
    List<EmployeeEmailForm> listEmail(@Param("employeeName") String employeeName);
}