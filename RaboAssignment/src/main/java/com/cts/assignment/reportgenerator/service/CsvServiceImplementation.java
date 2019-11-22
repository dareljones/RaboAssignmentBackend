package com.cts.assignment.reportgenerator.service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.cts.assignment.reportgenerator.pojo.CsvEntity;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

@Service("CsvService")
public class CsvServiceImplementation implements CsvService {
	private static DecimalFormat roundOff = new DecimalFormat("0.00");

	public void uploadFile(String fileName) {

		List<CsvEntity> reportEntity = null;
		try (Reader reader = Files.newBufferedReader(Paths.get(fileName));) {
			CsvToBean<CsvEntity> csvToBean = new CsvToBeanBuilder<CsvEntity>(reader).withType(CsvEntity.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			reportEntity = csvToBean.parse();
			generateReport(reportEntity);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<Integer> findDuplicateReference(List<CsvEntity> reportEntity) {

		Set<Integer> reportTemp = new HashSet<Integer>();
		Set<Integer> duplicateReferences = new HashSet<Integer>();

		for (CsvEntity csv : reportEntity) {
			if (!reportTemp.add(csv.getReference())) {
				duplicateReferences.add(csv.getReference());
			}
		}
		return duplicateReferences;
	}

	public void generateReport(List<CsvEntity> allElements) {
		Writer writer = null;
		CSVWriter csvWriter = null;
		Set<Integer> duplicateReferences = findDuplicateReference(allElements);

		try {
			writer = Files.newBufferedWriter(Paths.get("./reportCSV.csv"));
			csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			csvWriter.writeNext(new String[] { "Duplicate Referenes", "" });
			csvWriter.writeNext(new String[] { "Reference", "Description" });
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Integer duplicateReference : duplicateReferences) {
			for (CsvEntity element : allElements) {
				if (element.getReference().equals(duplicateReference)) {
					csvWriter.writeNext(
							new String[] { Integer.toString(element.getReference()), element.getDescription() });

				}
			}
		}
		csvWriter.writeNext(new String[] { "Mismatched End Balance", "" });
		csvWriter.writeNext(new String[] { "Reference", "Description" });
		for (CsvEntity element : allElements) {
			if (Float.parseFloat(roundOff.format(element.getStartBalance() + element.getMutation())) != element
					.getEndBalance()) {
				csvWriter
						.writeNext(new String[] { Integer.toString(element.getReference()), element.getDescription() });
			}
		}

		try {
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
