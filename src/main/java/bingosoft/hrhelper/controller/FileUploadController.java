package bingosoft.hrhelper.controller;

import bingosoft.hrhelper.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bingosoft.hrhelper.common.*;

import java.io.IOException;

/**
 * @创建人 zhangyx
 * @功能描述 附件上传控制类
 * @创建时间 2018-08-04 15:06:06
 */
@RestController
@RequestMapping("file")
public class FileUploadController {

	@Autowired
	UploadService uploadService;

	/**
	 * 文件上传
	 * @param file
	 * @return 文件路径
	 */
	@PostMapping("/upload")
	public Result<String> upload(MultipartFile file){

		Result<String> result = uploadService.upLoadFile(file);
		return result;
	}
}
