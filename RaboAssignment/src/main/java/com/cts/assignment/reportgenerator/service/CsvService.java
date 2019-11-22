package com.cts.assignment.reportgenerator.service;

import java.util.List;
import java.util.Set;

import com.cts.assignment.reportgenerator.pojo.CsvEntity;

public interface CsvService {

	public void uploadFile(String fileName);

	public Set<Integer> findDuplicateReference(List<CsvEntity> reportEntity);

	public void generateReport(List<CsvEntity> allElements);
}
