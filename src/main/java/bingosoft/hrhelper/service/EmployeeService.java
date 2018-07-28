package bingosoft.hrhelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.model.Employee;

public class EmployeeService {
	
	@Autowired
	EmployeeMapper em ;
	
	public List<Employee> listEmployee(){
		return null;
	}
}
