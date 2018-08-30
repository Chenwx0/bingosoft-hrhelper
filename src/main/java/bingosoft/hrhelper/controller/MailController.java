package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.common.ExcelUtil;
import bingosoft.hrhelper.common.Result;
import bingosoft.hrhelper.common.TipMessage;
import bingosoft.hrhelper.form.MailListForm;
import bingosoft.hrhelper.form.MailQueryFilter;
import bingosoft.hrhelper.model.Mail;
import bingosoft.hrhelper.service.MailSendService;
import bingosoft.hrhelper.service.MailService;

import leap.web.annotation.Path;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人 chenwx
 * @功能描述 邮件展示控制类
 * @创建时间 2018-08-04 15:06:06
 */
@RestController
@RequestMapping("mail")
@CrossOrigin
public class MailController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MailService mailService;
    @Autowired
    MailSendService mailSendService;
    @Autowired
    ExcelUtil excelUtil;

    /**
     * 分页获取邮件列表
     * @param mailQueryFilter
     * @return
     */
    @PostMapping("/list")
    public Result pageQueryMailList(@RequestBody MailQueryFilter mailQueryFilter){
        Result result = mailService.pageQueryMailList(mailQueryFilter);
        return result;
    }

    /**
     * 删除邮件
     * @param mailId
     * @return 操作结果
     */
    @DeleteMapping("/del")
    public Result deleteMail(String mailId){
        Result result = mailService.deleteMail(mailId);
        return result;
    }

    /**
     * 批量删除邮件
     * @param mailIds
     * @return 操作结果
     */
    @DeleteMapping("/patch_del")
    public Result patchDeleteMail(String[] mailIds){
        Result result = mailService.patchDeleteMail(mailIds);
        return result;
    }

    /**
     * 更新待发送邮件信息
     * @param mail
     * @return 更新结果
     */
    @PatchMapping("/update")
    public Result updateMail(@RequestBody Mail mail){
        Result result = mailService.updateMail(mail);
        return result;
    }

    /**
     * 取消发送邮件
     * @param mailId
     */
    @GetMapping(path = "/cancel")
    public Result cancelSendMail(String mailId){
    	Result result = mailService.cancelSend(mailId);
    	return result;
    }

    /**
     * 批量取消发送邮件
     * @param mailIds
     */
    @GetMapping(path = "/patch_cancel")
    public Result patchCancelSendMail(String[] mailIds){
        Result result = mailService.patchCancelSend(mailIds);
        return result;
    }

    /**
     * 邮件列表导出
     * @param mailQueryFilter
     * @param resp
     * @return
     */
    @GetMapping("/export")
    public Result exportMail(MailQueryFilter mailQueryFilter, HttpServletResponse resp){

        // 根据条件获取数据
        Result<List<MailListForm>> result = mailService.queryMailList(mailQueryFilter);
        if (result.isSuccess()){
            String operationId = mailQueryFilter.getOperationId();
            Integer status = mailQueryFilter.getStatus();
            // 构建exccel文件
            Result<String> result1 = excelUtil.buildExcel(operationId,status,result.getResultEntity());
            if (result1.isSuccess()){
                Result result2 = new Result();
                // 获取文件路径
                String filePath = result1.getResultEntity();
                // 构建文件对象
                File file = new File(filePath);
                // 构建文件名
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = simpleDateFormat.format(new Date());
                String fileName = "邮件数据导出-" + time + ".xls";
                // 构建响应对象
                resp.setContentType("application/vnd.ms-excel");
                resp.setCharacterEncoding("utf-8");
                resp.setContentLength((int) file.length());
                try {
                    resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(TipMessage.EXPORT_FAIL,e);
                    result2.setSuccess(false);
                    result2.setMessage(TipMessage.EXPORT_FAIL );
                    return result2;
                }
                // 文件输出
                byte[] buff = new byte[1024];
                BufferedInputStream bis = null;
                try {
                    OutputStream os = resp.getOutputStream();
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int i = 0;
                    while ((i = bis.read(buff)) != -1) {
                        os.write(buff, 0, i);
                        os.flush();
                    }
                } catch (IOException e) {
                    logger.error(TipMessage.EXPORT_FAIL,e);
                    result2.setSuccess(false);
                    result2.setMessage(TipMessage.EXPORT_FAIL);
                    return result2;
                } finally {
                    try {
                        bis.close();
                        file.delete();
                    } catch (IOException e) {
                        logger.error(e.getMessage(),e);
                    }
                }
            }
            return result1;
        }
        return result;
    }
}
