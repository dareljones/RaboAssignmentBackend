package com.cts.assignment.reportgenerator.service;

import java.util.List;
import java.util.Set;

import com.cts.assignment.reportgenerator.pojo.Records;

public interface XmlService {
	public void uploadFile(String fileName);

	public Set<Integer> findDuplicateReference(List<Records> reportEntity);

	public void generateReport(List<Records> allElements);
}
