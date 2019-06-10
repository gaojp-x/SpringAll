package com.example.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.mapper.EasyMapper;
import com.example.model.LoggerModel;
import com.example.utils.ExcelUtils;

@Controller
public class EasyController {

	@Autowired
	EasyMapper easyMapper;

	@RequestMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
		List<LoggerModel> list = easyMapper.selectAll();
		ExcelUtils.exportExcel(list, "表头", "sheet1", LoggerModel.class, "日志记录", response);
	}

	@RequestMapping("/importExcel")
	@ResponseBody
	public List<LoggerModel> importExcel(@RequestParam("file") MultipartFile file) throws Exception {
		List<LoggerModel> list = ExcelUtils.importExcel(file, 1, 1, true, LoggerModel.class);
		System.out.println(list.size());
        list.forEach(System.out::println);
        return list;
	}

	
	
	
	
	
	
	
	
	
	
	
}
