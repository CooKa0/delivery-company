package com.solvd.laba.delivery.jaxbParser;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class JAXBParser {
    public static void main(String[] args) {
        File xmlFile = new File("src/main/java/com/solvd/laba/delivery/jaxbParser/delivery_data.xml");
        File outputXmlFile = new File("src/main/resources/output_company.xml");

        try {
            // Unmarshal (XML to Java Object)
            Company company = unmarshall(Company.class, xmlFile);
            System.out.println("Unmarshalled Company: " + company);

            // Marshal (Java Object to XML)
            marshall(company, outputXmlFile);
            System.out.println("Company data written to output XML: " + outputXmlFile.getAbsolutePath());

        } catch (JAXBException e) {
            throw new RuntimeException("Error occurred during JAXB operation: " + e.getMessage(), e);
        }
    }

    // Method to unmarshal XML to Java object
    private static <T> T unmarshall(Class<T> clazz, File xmlFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(xmlFile);
    }

    // Method to marshal Java object to XML
    private static <T> void marshall(T object, File outputFile) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(object, outputFile);
    }
}
