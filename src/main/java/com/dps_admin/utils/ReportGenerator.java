package com.dps_admin.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dps_admin.model.Teacher;
import com.dps_admin.repository.NotificationRepository;
import com.dps_admin.repository.RoleRepository;
import com.dps_admin.repository.StudentRepository;
import com.dps_admin.repository.TeacherRepository;

@Component
public class ReportGenerator {
		
	@Autowired
	TeacherRepository teacherRepo;
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	NotificationRepository notificationRepo;
	@Autowired
	RoleRepository roleRepo;


	public ByteArrayInputStream adminDashboardReport() throws IOException {
		String [] dashboardColumns = { "Total Teachers", "Total Students", "Total Notifications", "Total Roles" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet dashboardSheet = workbook.createSheet("Dashboard Report");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.DARK_GREEN.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			Row headerRow = dashboardSheet.createRow(0);
			for(int col=0;col<dashboardColumns.length; col++) {
				Cell cell =headerRow.createCell(col);
				cell.setCellValue(dashboardColumns[col]);
				cell.setCellStyle(headerCellStyle);
			}
			Row row = dashboardSheet.createRow(1);
			row.createCell(0).setCellValue(teacherRepo.count());
			row.createCell(1).setCellValue(studentRepo.count());
			row.createCell(2).setCellValue(notificationRepo.count());
			row.createCell(3).setCellValue(roleRepo.count());
			
			
			String [] teacherColumns = { "Sl.No", "Name", "Email", "Class Teacher", "Mobile No.", "Created At" };
			Sheet teacherSheet = workbook.createSheet("Teacher Report");
			Font teacherFont = workbook.createFont();
			teacherFont.setBold(true);
			teacherFont.setColor(IndexedColors.DARK_GREEN.getIndex());
			CellStyle teacherCellStyle=workbook.createCellStyle();
			teacherCellStyle.setFont(teacherFont);
			Row teacherHeaderRow = teacherSheet.createRow(0);
			for(int col=0;col<teacherColumns.length;col++) {
				Cell cell = teacherHeaderRow.createCell(col);
				cell.setCellValue(teacherColumns[col]);
				cell.setCellStyle(teacherCellStyle);
				
			}
			List<Teacher> teacherData=teacherRepo.findAll();
			if(!teacherData.isEmpty()) {
				int RowIndx1=1;
				int i=1;
				for(Teacher list:teacherData) {
					Row teacherRow = teacherSheet.createRow(RowIndx1++);
					teacherRow.createCell(0).setCellValue(i++);
					teacherRow.createCell(1).setCellValue(list.getName());
					teacherRow.createCell(2).setCellValue(list.getEmail());
					teacherRow.createCell(3).setCellValue(list.getClassTeacher());
					teacherRow.createCell(4).setCellValue(list.getMobileNo());
					teacherRow.createCell(5).setCellValue(list.getCreatedAt());
				}
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
			
		}
		
		
	}


