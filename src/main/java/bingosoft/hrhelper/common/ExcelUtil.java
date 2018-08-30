package bingosoft.hrhelper.common;

import bingosoft.hrhelper.form.MailListForm;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @创建人 chenwx
 * @功能描述
 * @创建时间 2018-08-29
 */
@Component
public class ExcelUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${SystemConfig.tempPath}")
    // 临时目录
    private String tempPath;

    /**
     * 构建excel文件
     * @param operationId 业务ID
     * @param list 表格数据
     * @return 文件路径
     */
    public Result<String> buildExcel(String operationId, Integer status, List<MailListForm> list){

        Result<String> result = new Result<>();
        // 参数校验
        if (operationId == null || operationId.length() == 0){
            result.setSuccess(false);
            result.setMessage("业务Id不能为空");
            return result;
        }
        if (status == null || status != 0 && status != 1){
            status = 0;
        }
        if (list == null || list.size() == 0){
            result.setSuccess(false);
            result.setMessage("数据不能为空");
            return result;
        }

        String fileName = "邮件信息" + Calendar.getInstance().getTimeInMillis() + ".xls";

        // 实例化workbook对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("邮件信息");
        // 插入表头
        insertTitle(workbook,sheet,operationId,status);
        // 插入数据
        insertData(workbook,sheet,list,operationId,status);
        //生成excel文件
        try {
            String filePath = buildExcelFile(fileName, workbook);
            result.setResultEntity(filePath);
        } catch (IOException e) {
            logger.error("创建excel文件失败",e);
            result.setSuccess(false);
            result.setMessage("创建excel文件失败");
        }

        return result;
    }

    /**
     * 插入表头
     * @param workbook
     * @param sheet
     * @param operationId
     */
    private void insertTitle(HSSFWorkbook workbook, HSSFSheet sheet, String operationId, Integer status){
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1,12*256);
        sheet.setColumnWidth(2,17*256);
        sheet.setColumnWidth(5,17*256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        if (operationId.equals("1")){// 试用期转正
            cell = row.createCell(0);
            cell.setCellValue("员工名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("入职时间");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("拟转正时间");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("邮件主题");
            cell.setCellStyle(style);


            cell = row.createCell(5);
            cell.setCellValue("收件人");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("抄送人");
            cell.setCellStyle(style);

            if (status==1){
                cell = row.createCell(7);
                cell.setCellValue("审核状态");
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue("发送时间");
                cell.setCellStyle(style);
            }else{
                cell = row.createCell(7);
                cell.setCellValue("发送时间");
                cell.setCellStyle(style);
            }

        }else if (operationId.equals("2")){// 合同续签
            cell = row.createCell(0);
            cell.setCellValue("员工名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("入职时间");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("合同结束时间");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("邮件主题");
            cell.setCellStyle(style);


            cell = row.createCell(5);
            cell.setCellValue("收件人");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("抄送人");
            cell.setCellStyle(style);

            if (status==1){
                cell = row.createCell(7);
                cell.setCellValue("审核状态");
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue("发送时间");
                cell.setCellStyle(style);
            }else{
                cell = row.createCell(7);
                cell.setCellValue("发送时间");
                cell.setCellStyle(style);
            }

        }else if (operationId.equals("3")){// 绩效表填写
            cell = row.createCell(0);
            cell.setCellValue("员工名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("入职时间");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("招聘类型");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("邮件主题");
            cell.setCellStyle(style);


            cell = row.createCell(5);
            cell.setCellValue("收件人");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("抄送人");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("发送时间");
            cell.setCellStyle(style);
        }else if (operationId.equals("4")){// 入职提醒
            cell = row.createCell(0);
            cell.setCellValue("员工名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("入职时间");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("邮件主题");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("收件人");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("抄送人");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("发送时间");
            cell.setCellStyle(style);
        }else if (operationId.equals("5")){// 年限贺卡
            cell = row.createCell(0);
            cell.setCellValue("员工名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("所属部门");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("入职时间");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("拟转正时间");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("邮件主题");
            cell.setCellStyle(style);


            cell = row.createCell(5);
            cell.setCellValue("收件人");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("抄送人");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("发送时间");
            cell.setCellStyle(style);
        }
    }

    /**
     * 插入数据
     * @param workbook
     * @param sheet
     * @param list
     * @param operationId
     */
    private void insertData(HSSFWorkbook workbook, HSSFSheet sheet, List<MailListForm> list, String operationId, Integer status){
        // 设置日期格式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置居中
        int rowNum=1;
        if (operationId.equals("1")){// 试用期转正
            for(MailListForm mailListForm: list){
                HSSFRow row = sheet.createRow(rowNum);
                // 员工姓名
                row.createCell(0).setCellValue(mailListForm.getEmployeeName());
                // 所属部门
                row.createCell(1).setCellValue(mailListForm.getDepartment());
                // 入职时间
                HSSFCell cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellValue(mailListForm.getEntryDay());
                // 拟转正时间
                HSSFCell cell2 = row.createCell(3);
                cell2.setCellStyle(style);
                cell2.setCellValue(mailListForm.getPlanFullmenberDay());
                // 邮件主题
                row.createCell(4).setCellValue(mailListForm.getMailName());
                // 收件人
                row.createCell(5).setCellValue(mailListForm.getRecipient());
                // 抄送人
                row.createCell(6).setCellValue(mailListForm.getCopyPeople());

                if (status == 1){
                    // 审核状态
                    row.createCell(7).setCellValue(mailListForm.getApproveStatus());
                    // 发送时间
                    HSSFCell cell3 = row.createCell(8);
                    cell3.setCellStyle(style);
                    if (mailListForm.getSendTime()!=null){
                        cell3.setCellValue(mailListForm.getSendTime());
                    }else {
                        cell3.setCellValue(mailListForm.getPlanSendTime());
                    }
                }else {
                    // 发送时间
                    HSSFCell cell3 = row.createCell(7);
                    cell3.setCellStyle(style);
                    if (mailListForm.getSendTime()!=null){
                        cell3.setCellValue(mailListForm.getSendTime());
                    }else {
                        cell3.setCellValue(mailListForm.getPlanSendTime());
                    }
                }
                rowNum++;
            }
        }else if (operationId.equals("2")){// 合同续签
            for(MailListForm mailListForm: list){
                HSSFRow row = sheet.createRow(rowNum);
                // 员工姓名
                row.createCell(0).setCellValue(mailListForm.getEmployeeName());
                // 所属部门
                row.createCell(1).setCellValue(mailListForm.getDepartment());
                // 入职时间
                HSSFCell cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellValue(mailListForm.getEntryDay());
                // 合同结束时间
                HSSFCell cell2 = row.createCell(3);
                cell2.setCellStyle(style);
                cell2.setCellValue(mailListForm.getContractDay());
                // 邮件主题
                row.createCell(4).setCellValue(mailListForm.getMailName());
                // 收件人
                row.createCell(5).setCellValue(mailListForm.getRecipient());
                // 抄送人
                row.createCell(6).setCellValue(mailListForm.getCopyPeople());

                if (status == 1){
                    // 审核状态
                    row.createCell(7).setCellValue(mailListForm.getApproveStatus());
                    // 发送时间
                    HSSFCell cell3 = row.createCell(8);
                    cell3.setCellStyle(style);
                    if (mailListForm.getSendTime()!=null){
                        cell3.setCellValue(mailListForm.getSendTime());
                    }else {
                        cell3.setCellValue(mailListForm.getPlanSendTime());
                    }
                }else {
                    // 发送时间
                    HSSFCell cell3 = row.createCell(7);
                    cell3.setCellStyle(style);
                    if (mailListForm.getSendTime()!=null){
                        cell3.setCellValue(mailListForm.getSendTime());
                    }else {
                        cell3.setCellValue(mailListForm.getPlanSendTime());
                    }
                }
                rowNum++;
            }
        }else if (operationId.equals("3")){// 绩效表填写
            for(MailListForm mailListForm: list){
                HSSFRow row = sheet.createRow(rowNum);
                // 员工姓名
                row.createCell(0).setCellValue(mailListForm.getEmployeeName());
                // 所属部门
                row.createCell(1).setCellValue(mailListForm.getDepartment());
                // 入职时间
                HSSFCell cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellValue(mailListForm.getEntryDay());
                // 招聘类型
                row.createCell(3).setCellValue(mailListForm.getMailName());
                // 邮件主题
                row.createCell(4).setCellValue(mailListForm.getMailName());
                // 收件人
                row.createCell(5).setCellValue(mailListForm.getRecipient());
                // 抄送人
                row.createCell(6).setCellValue(mailListForm.getCopyPeople());
                // 发送时间
                HSSFCell cell3 = row.createCell(7);
                cell3.setCellStyle(style);
                if (mailListForm.getSendTime()!=null){
                    cell3.setCellValue(mailListForm.getSendTime());
                }else {
                    cell3.setCellValue(mailListForm.getPlanSendTime());
                }
                rowNum++;
            }
        }else if (operationId.equals("4")){// 入职提醒
            for(MailListForm mailListForm: list){
                HSSFRow row = sheet.createRow(rowNum);
                // 员工姓名
                row.createCell(0).setCellValue(mailListForm.getEmployeeName());
                // 所属部门
                row.createCell(1).setCellValue(mailListForm.getDepartment());
                // 入职时间
                HSSFCell cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellValue(mailListForm.getEntryDay());
                // 邮件主题
                row.createCell(3).setCellValue(mailListForm.getMailName());
                // 收件人
                row.createCell(4).setCellValue(mailListForm.getRecipient());
                // 抄送人
                row.createCell(5).setCellValue(mailListForm.getCopyPeople());
                // 发送时间
                HSSFCell cell3 = row.createCell(6);
                cell3.setCellStyle(style);
                if (mailListForm.getSendTime()!=null){
                    cell3.setCellValue(mailListForm.getSendTime());
                }else {
                    cell3.setCellValue(mailListForm.getPlanSendTime());
                }
                rowNum++;
            }
        }else if (operationId.equals("5")){// 年限贺卡
            for(MailListForm mailListForm: list){
                HSSFRow row = sheet.createRow(rowNum);
                // 员工姓名
                row.createCell(0).setCellValue(mailListForm.getEmployeeName());
                // 所属部门
                row.createCell(1).setCellValue(mailListForm.getDepartment());
                // 入职时间
                HSSFCell cell = row.createCell(2);
                cell.setCellStyle(style);
                cell.setCellValue(mailListForm.getEntryDay());
                // 邮件主题
                row.createCell(3).setCellValue(mailListForm.getMailName());
                // 收件人
                row.createCell(4).setCellValue(mailListForm.getRecipient());
                // 抄送人
                row.createCell(5).setCellValue(mailListForm.getCopyPeople());
                // 发送时间
                HSSFCell cell3 = row.createCell(6);
                cell3.setCellStyle(style);
                if (mailListForm.getSendTime()!=null){
                    cell3.setCellValue(mailListForm.getSendTime());
                }else {
                    cell3.setCellValue(mailListForm.getPlanSendTime());
                }
                rowNum++;
            }
        }

    }
    /**
     * 生成excel文件
     * @param filename
     * @param workbook
     * @throws Exception
     */
    private String buildExcelFile(String filename,HSSFWorkbook workbook) throws IOException{

        File file = new File(tempPath);
        if (!file.exists()){
            file.mkdir();
        }
        // 构建文件路径
        String filePath = tempPath + filename;
        // 文件输出
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.flush();
        fos.close();

        return filePath;
    }
}
