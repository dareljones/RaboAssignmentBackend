package com.cts.assignment.reportgenerator.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import com.cts.assignment.reportgenerator.service.CsvServiceImplementation;
import com.cts.assignment.reportgenerator.service.XmlServiceImplementation;

@RestController
public class ReportGeneratorController {

	@Autowired
	CsvServiceImplementation csvService;
	@Autowired
	XmlServiceImplementation xmlService;

	@PostMapping()
	public String uploadFile(@RequestBody String filePath) {

		String fileType = FilenameUtils.getExtension(filePath);

		if (fileType.equals("csv")) {
			csvService.uploadFile(filePath);
		} else if (fileType.equals("xml")) {
			xmlService.uploadFile(filePath);
		} else {
			return ("Invalid file type");
		}
		return ("Success");
	}

}
