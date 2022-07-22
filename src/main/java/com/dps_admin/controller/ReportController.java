package com.dps_admin.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dps_admin.utils.ReportGenerator;


@Controller
@RequestMapping("/admin")
public class ReportController {
	
	@Autowired
	private ReportGenerator reportGenerator;
	
	
	@GetMapping("/report")
	public ResponseEntity<InputStreamResource> generateReport() throws IOException{
		System.err.println("DashboardReport::::");
		try {
			String filename="DPS";
			ByteArrayInputStream in=reportGenerator.adminDashboardReport();
			InputStreamResource file = new InputStreamResource(in);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename +".xlsx")
					.contentType(MediaType.parseMediaType("application/csv")).body(file);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
