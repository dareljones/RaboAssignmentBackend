package com.cts.assignment.reportgenerator.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.cts.assignment.reportgenerator.pojo.CsvEntity;

class CsvServiceTest {

	@Test
	void testValidateAndGenerateReport() throws IOException {
		CsvService test = new CsvService();
		String actual = test.validateAndGenerateReport("src/main/resources/records.csv");
		String expected = "Success";
		assertEquals(expected, actual);

	}

	@Test
	void testUploadFileFail() {
		CsvService test = new CsvService();
		assertThrows(NoSuchFileException.class, () -> {
			test.uploadFile("/src/main/resources/records.csv");
		});

	}

	@Test
	void testfindDuplicateReferencePass() {
		CsvService test = new CsvService();
		List<CsvEntity> reportEntity = new ArrayList<CsvEntity>();
		reportEntity.add(new CsvEntity(194261, "NL91RABO0315273637", "Clothes from Bakke", 21.6f, 41.83f, 20.23f));
		reportEntity.add(new CsvEntity(194262, "NL91RABO0315273636", "Clothes from Jan Bakke", 21.6f, 41.83f, 20.23f));
		reportEntity.add(new CsvEntity(194263, "NL91RABO0315273635", "Clothes from Jan", 21.6f, 41.83f, 20.23f));

		Set<Integer> actual = test.findDuplicateReference(reportEntity);
		Set<Integer> expected = new HashSet<Integer>();
		assertEquals(expected, actual);
	}

	@Test
	void testfindDuplicateReferenceFail() {
		CsvService test = new CsvService();
		List<CsvEntity> reportEntity = new ArrayList<CsvEntity>();
		reportEntity.add(new CsvEntity(194261, "NL91RABO0315273637", "Clothes from Bakke", 21.6f, 41.83f, 20.23f));
		reportEntity.add(new CsvEntity(194262, "NL91RABO0315273636", "Clothes from Jan Bakke", 21.6f, 41.83f, 20.23f));
		reportEntity.add(new CsvEntity(194261, "NL91RABO0315273635", "Clothes from Jan", 21.6f, 41.83f, 20.23f));

		Set<Integer> actual = test.findDuplicateReference(reportEntity);
		Set<Integer> expected = new HashSet<Integer>();
		expected.add(194261);
		assertEquals(expected, actual);
	}

}
