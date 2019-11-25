package com.cts.assignment.reportgenerator.service;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cts.assignment.reportgenerator.pojo.Records;
import com.cts.assignment.reportgenerator.pojo.XmlEntity;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

@Service("XmlService")
public class XmlService {
	private static final DecimalFormat ROUND_OFF = new DecimalFormat("0.00");

	public String validateAndGenerateReport(String fileName) throws IOException, JAXBException {

		List<Records> reportEntity = uploadFile(fileName);
		Set<Integer> duplicateReferences = findDuplicateReference(reportEntity);
		generateReport(reportEntity, duplicateReferences);
		return "Success";

	}

	public List<Records> uploadFile(String fileName) throws JAXBException, IOException, UnmarshalException {
		List<Records> allElements = null;
		JAXBContext jaxbContext;
		jaxbContext = JAXBContext.newInstance(XmlEntity.class);
		List<XmlEntity> reportEntity = new ArrayList<XmlEntity>();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		reportEntity.add((XmlEntity) jaxbUnmarshaller.unmarshal(new File(fileName)));
		for (XmlEntity xml : reportEntity)
			allElements = xml.getRecord();
		return allElements;

	}

	public Set<Integer> findDuplicateReference(List<Records> reportEntity) {

		Set<Integer> reportTemp = new HashSet<Integer>();
		Set<Integer> duplicateReferences = new HashSet<Integer>();

		for (Records xml : reportEntity) {
			if (!reportTemp.add(xml.getReference())) {
				duplicateReferences.add(xml.getReference());
			}
		}
		return duplicateReferences;
	}

	public void generateReport(List<Records> allElements, Set<Integer> duplicateReferences) throws IOException {
		Writer writer = null;
		CSVWriter csvWriter = null;

		writer = Files.newBufferedWriter(Paths.get("./reportXML.csv"));
		csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
		csvWriter.writeNext(new String[] { "Duplicate Referenes" });
		csvWriter.writeNext(new String[] { "Reference", "Description" });

		for (Integer duplicateReference : duplicateReferences) {
			for (Records element : allElements) {
				if (element.getReference().equals(duplicateReference)) {
					csvWriter.writeNext(
							new String[] { Integer.toString(element.getReference()), element.getDescription() });

				}
			}
		}
		csvWriter.writeNext(new String[] { "Mismatched End Balance" });
		csvWriter.writeNext(new String[] { "Reference", "Description" });
		for (Records element : allElements) {
			if (Float.parseFloat(ROUND_OFF.format(element.getStartBalance() + element.getMutation())) != element
					.getEndBalance()) {
				csvWriter
						.writeNext(new String[] { Integer.toString(element.getReference()), element.getDescription() });
			}
		}
		csvWriter.close();

	}

}
