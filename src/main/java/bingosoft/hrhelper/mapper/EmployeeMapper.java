package bingosoft.hrhelper.mapper;

import bingosoft.hrhelper.model.Employee;

public interface EmployeeMapper {
    int insert(Employee record);

    int insertSelective(Employee record);
}