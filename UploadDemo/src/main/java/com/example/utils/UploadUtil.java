package com.example.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author 高家鹏
 * @date 2019年4月28日
 * @title 文件上传
 */
@Component
public class UploadUtil {
	
	
	private static String path;//要保存的路径
	
	@Value("${localhost.file.path}")
	public void setPath(String path) {
		UploadUtil.path = path;
	}

	/**
	 * 
	 * @param files 要上传的文件
	 * @return
	 */
	public static List<String> FileUpload(MultipartFile[] files) {
		// 保存新生成的文件名
		List<String> paths = new ArrayList<>(files.length);

		StringBuilder sb = new StringBuilder(files.length);

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue;
			}

			// 获取上传文件的名字
			String filename = file.getOriginalFilename();

			// 新文件名称
			String newFileName = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));

			// 上传路径
			File upload = new File(path + newFileName);

			// 如果父路径不存在，新建路径
			if (!upload.getParentFile().exists()) {
				upload.getParentFile().mkdirs();
			}

			// 向指定路径写入文件
			try {
				file.transferTo(upload);
			} catch (IllegalStateException | IOException e) {
				System.out.println("写入失败...." + e.getMessage());
				e.printStackTrace();
			}
			// 返回新文件名称
			paths.add(newFileName);
			sb.append("[原文件名:" + filename + "  新文件名:" + newFileName + "]   ");
		}
		System.out.println(sb);
		return paths;
	}
}
