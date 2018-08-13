package bingosoft.hrhelper.service;

import java.util.ArrayList;
import java.util.List;

import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.EmployeeEmailForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.model.Employee;
import org.springframework.stereotype.Service;

/**
 * @创建人 chenwx
 * @功能描述 员工信息业务服务类
 * @创建时间 2018-08-13 18:06:06
 */
@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmployeeMapper em ;
	
	public List<Employee> listEmployee(){
		return null;
	}

	/**
	 * 获取员工邮件地址列表
	 * @param employeeName
	 * @return
	 */
	public Result<List<EmployeeEmailForm>> getEmailList(String employeeName){
		Result<List<EmployeeEmailForm>> result = new Result<>();

		List<EmployeeEmailForm> list =  new ArrayList<>();
		try{
			list = em.listEmail(employeeName);
			result.setResultEntity(list);
		}catch (Exception e){
			logger.error(TipMessage.QUERY_FAIL, e);
			result.setSuccess(false);
			result.setMessage(TipMessage.QUERY_FAIL);
		}

		return result;
	};


}
