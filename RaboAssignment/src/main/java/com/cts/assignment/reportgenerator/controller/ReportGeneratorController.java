package com.cts.assignment.reportgenerator.controller;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.cts.assignment.reportgenerator.exception.UnknownFileException;
import com.cts.assignment.reportgenerator.service.CsvService;
import com.cts.assignment.reportgenerator.service.XmlService;

@RestController
public class ReportGeneratorController {

	private CsvService csvService;
	private XmlService xmlService;

	@Autowired
	public ReportGeneratorController(CsvService csvService, XmlService xmlService) {
		super();
		this.csvService = csvService;
		this.xmlService = xmlService;
	}

	@PostMapping()
	public ResponseEntity<Object> validateAndGenerateReport(@RequestBody String filePath)
			throws IOException, JAXBException {

		String fileType = FilenameUtils.getExtension(filePath);

		if (fileType.equals("csv")) {
			csvService.validateAndGenerateReport(filePath);
		} else if (fileType.equals("xml")) {
			xmlService.validateAndGenerateReport(filePath);
		} else
			throw new UnknownFileException();

		return new ResponseEntity<>("Report Generated Successfully", HttpStatus.OK);
	}

}
