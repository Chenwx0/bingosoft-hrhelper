package bingosoft.hrhelper.service;

import bingosoft.hrhelper.common.DateTransferUtils;
import bingosoft.hrhelper.mapper.EmployeeMapper;
import bingosoft.hrhelper.mapper.ModelMapper;
import bingosoft.hrhelper.model.Employee;
import bingosoft.hrhelper.model.Model;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据发送人和邮件构造发送内容
 *
 * @author cc
 * @date 2018-07-29 23:15:15
 */

@Service
public class CreateMailContentService {

    //定义以“#[XX]#”模式的正则表达式
    //字符串以convert#开始，以#结束，不包括#号
    private String regex = "convert#[^#]+";

    //特殊字段
    //工作月份，向上取整
    private String workMath = "convert#WorkMonth#";
    //截至日期
    private String deadline = "convert#Deadline#";
    //发送时间
    private String sendDate = "convert#SendDate#";


    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    ModelMapper modelMapper;
   
    public String getMailContent(String userId, String mailModelId, String sendTime, String deadDate){
        Employee employee = employeeMapper.selectByPrimaryKey(userId);
        Model model = modelMapper.selectByPrimaryKey(mailModelId);
        //获取邮件模板内容
        String content = model.getModelContent();
        Matcher matcher = Pattern.compile(regex).matcher(content);
        //map用来存放邮件模板可直接进行替换的值对
        Map<String, String> map = new HashMap<>();
        while (matcher.find()){
            String key = matcher.group(0) + "#";
            String keyEntry = formatFiled(key);
            String value = employee.getValue(keyEntry.substring(8,keyEntry.length()-1));
            if (!value.equals("")){
                map.put(key,value);
            }
        }
        //替换map中的值对
        for (String key : map.keySet()){
            String value = map.get(key);
            content = content.replaceAll(key,value);
        }
        //替换"WorkMonth"
        if (content.contains(workMath)){
            content = replaceWorkMonth(employee, content);
        }
        //替换“截至日期”
        if (deadline.equals("") && content.contains(deadline)){
            content = replaceDeadline(deadDate, content);
        }
        //替换“发送时间”
        if (content.contains(sendDate)){
            content = replaceSendDate(sendTime, content);
        }
        //返回结果
        return content;
    }

    private String replaceWorkMonth(Employee employee, String content) {
        //定义时间格式
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yy-MM-dd");
        //获取当前时间
        DateTime today = formatter.parseDateTime(DateTransferUtils.dateFormat(new Date(System.currentTimeMillis())));
        //获取入职时间
        DateTime entryDay = formatter.parseDateTime(DateTransferUtils.dateFormat(employee.getEntryDay()));
        //计算工作月份，向上取整
        int workMonths = Months.monthsBetween(entryDay,today).getMonths() + 1;
        content = content.replaceAll(workMath, String.valueOf(workMonths));
        return content;
    }

    private String replaceDeadline(String deadDate, String content){
        content = content.replaceAll(deadline, deadDate);
        return content;
    }

    private String replaceSendDate(String sendTime, String content){
        StringBuilder date = new StringBuilder();
        String[] time = sendTime.split(" ")[0].split("-");
        int year = Integer.valueOf(time[0]);
        int month = Integer.valueOf(time[1]);
        int day = Integer.valueOf(time[2]);
        date.append(year);
        date.append("年");
        date.append(month);
        date.append("月");
        date.append(day);
        date.append("日");
        content = content.replaceAll(sendDate, date.toString());
        return content;
    }

    private String formatFiled(String oldFiled){
        String[] params = oldFiled.split("_");
        if (params.length < 2)
            return oldFiled;
        StringBuffer newFiled = new StringBuffer();
        newFiled.append(params[0]);
        int i = 1;
        while (i < params.length){
            String tmp = params[i];
            newFiled.append(tmp.substring(0,1).toUpperCase() + tmp.substring(1,tmp.length()));
            i++;
        }
        return newFiled.toString();
    }
}
