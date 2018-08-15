package bingosoft.hrhelper.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadUtil {

    @Value("${SystemConfig.uploadPath}")
    // 上传根目录
    private String uploadPath;

	public String fileUpload(MultipartFile file) throws IOException{
        String oldFileName = file.getOriginalFilename();
        // 获取文件名（不带后缀）
        String fileName = oldFileName.substring(0, oldFileName.lastIndexOf("."));
        // 获取文件扩展名
        String extName = oldFileName.substring(oldFileName.lastIndexOf("."));
        // 构建新文件名
        String newFileName = fileName + "-" + System.currentTimeMillis()+extName;
        // 复制文件到上传目录
        FileUtils.copyInputStreamToFile(file.getInputStream(),new File(uploadPath,newFileName));
        // 返回上传文件路径
        return  uploadPath + newFileName;
    }
}
