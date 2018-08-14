package bingosoft.hrhelper.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;


public class FileUploadUtil {

	public static String fileUpload(MultipartFile file,String DIR,String URL){
        String oldFileName = file.getOriginalFilename();
        String extName = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName = System.currentTimeMillis()+extName;
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(DIR,newFileName));
            return "{\"error\":0,\"url\":\"" + URL + newFileName + "\"}";
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\":1}";
        }
    }
}
