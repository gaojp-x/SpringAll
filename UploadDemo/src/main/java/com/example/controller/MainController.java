package com.example.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.utils.DownloadUtil;
import com.example.utils.UploadUtil;

@RestController
public class MainController {

	@RequestMapping("/Upload")
	public ModelAndView update(@RequestParam Map<String, Object> map, @RequestParam("file") MultipartFile[] files,
			HttpSession session) {
		// String LoaclPath = session.getServletContext().getRealPath("/");
		ModelAndView mv = new ModelAndView();
		List<String> list = UploadUtil.FileUpload(files);
		// list.forEach(System.out::println);

		mv.addObject("map", map);
		mv.addObject("list", list);
		Map<String, Object> model = mv.getModel();
		System.out.println(model);
		mv.setViewName("main");
		return mv;
	}

	@RequestMapping("/Download")
	public String download(HttpServletResponse response) {
		return DownloadUtil.FileDownLoad(response, "下载", "C:\\Users\\Administrator\\Desktop\\test.jpg");
	}

	@RequestMapping("Download/{name}")
	public ResponseEntity<byte[]> download(@PathVariable String name, HttpServletResponse response,HttpServletRequest request) {
		//return DownloadUtil.LocalDownLoad("C:\\Users\\Administrator\\Desktop\\test.jpg", response);
		System.out.println(request.getServletContext().getRealPath("/static/")+name);
		return DownloadUtil.LocalDownLoad(name, response);
	}

}
