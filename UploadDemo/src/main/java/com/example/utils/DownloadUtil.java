package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 
 * @author 高家鹏
 * @date 2019年4月28日
 * @title 文件下载
 */
public class DownloadUtil {
	/**
	 * 文件下载
	 * 
	 * @param response 浏览器响应
	 * @param fileName 设置文件名 例:图片
	 * @param filePath 要下载的资源路径 例:C:/images/XXX.jpg
	 * @return
	 */
	public static String FileDownLoad(HttpServletResponse response, String fileName, String filePath) {

		// 获取文件后缀
		String suffix = filePath.substring(filePath.lastIndexOf("."), filePath.length());

		// 设置要下载的资源路径
		File file = new File(filePath);

		// 设置强制下载不打开
		// response.setContentType("application/force-download");
		response.setContentType("application/octet-stream");

		try {// 设置文件名
			fileName = URLEncoder.encode(fileName + suffix, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
		} catch (UnsupportedEncodingException e1) {
			System.out.println();
			e1.printStackTrace();
		}

		// 判断文件是否存在
		if (!file.exists()) {
			return filePath + "文件不存在";
		}

		try (OutputStream os = response.getOutputStream(); FileInputStream in = new FileInputStream(file);) {
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
			return "下载成功";
		} catch (Exception e) {
			System.out.println("下载失败.....");
			e.printStackTrace();
		}
		return "下载失败";
	}
	/**
	 * 文件下载
	 * @param path 文件路径
	 * @param response
	 * @return
	 */
	public static ResponseEntity<byte[]> LocalDownLoad(String path, HttpServletResponse response) {
		HttpHeaders headers = new HttpHeaders();
		File file = new File(path);
		byte[] b = null;
		try (FileInputStream in = new FileInputStream(file)) {
			b = new byte[in.available()];
			in.read(b);
		} catch (IOException e) {
			System.out.print("下载失败.....");
			e.printStackTrace();
		}
		//headers.setContentDispositionFormData("attachment",path);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<>(b, headers, HttpStatus.OK);
	}
}
