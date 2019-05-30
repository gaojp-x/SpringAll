package com.example.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.mapper.EasyMapper;
import com.example.model.LoggerModel;

@Controller
public class EasyController {

	@Autowired
	EasyMapper easyMapper;

	@RequestMapping("selectAll")
	public void selectAll(HttpServletResponse response) throws Exception {
		List<LoggerModel> list = easyMapper.selectAll();
		//list.forEach(System.out::print);
		System.out.println("\n###############################selectAll");

		try (ServletOutputStream out = response.getOutputStream();) {
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("测试.xlsx", "UTF-8"));
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
			Sheet sheet = new Sheet(1, 0,LoggerModel.class);
			sheet.setSheetName("sheet1");
            writer.write(list,sheet);
			writer.finish();
			out.flush();
		}
		
	}

	@RequestMapping("selectMaster")
	public void selectMaster(HttpServletResponse response) throws Exception {
		List<Map<String, Object>> list = easyMapper.selectMaster();
		list.forEach(System.out::print);
		System.out.println("\n###############################selectMaster");
		
		try (ServletOutputStream out = response.getOutputStream();) {
			response.setContentType("multipart/form-data");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("测试.xlsx", "UTF-8"));
			ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
			Sheet sheet = new Sheet(1, 0);
			sheet.setSheetName("sheet1");
			Table table = new Table(0);
			List<List<String>> head = new ArrayList<>();
			head.add(Arrays.asList("编号"));
			head.add(Arrays.asList("类型"));
			head.add(Arrays.asList("信息"));
			head.add(Arrays.asList("类信息"));
			head.add(Arrays.asList("时间"));
			table.setHead(head);
			List<List<String>> data = new ArrayList<>();
			
			list.forEach(
				iteam->{
					Map<String,Object> map = iteam;
					data.add(Arrays.asList(
							map.get("id").toString(),
							map.get("type").toString(),
							map.get("information").toString(),
							map.get("classInfo").toString(),
							map.get("datetime")==null?"--------":map.get("datetime").toString()
							)
					);
				}
			);
			
            writer.write0(data, sheet, table);
            
            //可以分批次从数据库查询，此处重复使用上面的数据
            writer.write0(data, sheet, table);
			
            writer.finish();
			out.flush();
		}
		
	}

	@RequestMapping("selectDS1")
	@ResponseBody
	public List<Map<String, Object>> selectDS1() {
		List<Map<String, Object>> list = easyMapper.selectDS1();
		list.forEach(System.out::print);
		System.out.println("\n###############################selectDS1");
		return list;
	}

	@RequestMapping("selectDS2")
	@ResponseBody
	public List<Map<String, Object>> selectDS2() {
		List<Map<String, Object>> list = easyMapper.selectDS2();
		list.forEach(System.out::print);
		System.out.println("\n###############################selectDS2");
		return list;
	}

	
}
