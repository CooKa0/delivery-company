package com.solvd.laba.delivery.staxParser;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stax.StAXSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {

    public static void main(String[] args) {
        File xmlFile = new File("D:\\delivery-company\\delivery-company\\src\\main\\java\\com\\solvd\\laba\\delivery\\staxParser\\delivery_data.xml");
        File xsdFile = new File("D:\\delivery-company\\delivery-company\\src\\main\\java\\com\\solvd\\laba\\delivery\\staxParser\\delivery_data.xsd");


        // Validate XML against XSD
        validateXML(xmlFile, xsdFile);

        // Parse XML file
        List<Company> companies = parseXML(xmlFile);

        // Print parsed companies
        printCompanies(companies);
    }

    private static void validateXML(File xmlFile, File xsdFile) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(xmlFile))));
            System.out.println("XML is valid against the XSD schema.");
        } catch (XMLStreamException | SAXException | IOException e) {
            System.out.println("XML is NOT valid. " + e.getMessage());
        }
    }

    private static List<Company> parseXML(File xmlFile) {
        List<Company> companies = new ArrayList<>();
        Company currentCompany = null;
        Customer currentCustomer = null;
        Order currentOrder = null;
        Vehicle currentVehicle = null;
        Invoice currentInvoice = null;

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try (FileInputStream fis = new FileInputStream(xmlFile)) {
            XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(fis);

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamReader.START_ELEMENT) {
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case "company":
                            currentCompany = new Company();
                            String companyId = reader.getAttributeValue(null, "id");
                            if (companyId != null) {
                                currentCompany.setId(Long.parseLong(companyId));
                            }
                            break;
                        case "customer":
                            currentCustomer = new Customer();
                            String customerId = reader.getAttributeValue(null, "id");
                            if (customerId != null) {
                                currentCustomer.setId(Long.parseLong(customerId));
                            }
                            break;
                        case "order":
                            currentOrder = new Order();
                            String orderId = reader.getAttributeValue(null, "id");
                            if (orderId != null) {
                                currentOrder.setId(Long.parseLong(orderId));
                            }
                            break;
                        case "vehicle":
                            currentVehicle = new Vehicle();
                            String vehicleId = reader.getAttributeValue(null, "id");
                            if (vehicleId != null) {
                                currentVehicle.setId(Long.parseLong(vehicleId));
                            }
                            break;
                        case "invoice":
                            currentInvoice = new Invoice();
                            String invoiceId = reader.getAttributeValue(null, "id");
                            if (invoiceId != null) {
                                currentInvoice.setId(Long.parseLong(invoiceId));
                            }
                            break;
                        // Set fields for Company
                        case "name":
                            if (currentCompany != null) {
                                currentCompany.setName(reader.getElementText());
                            }
                            break;
                        case "location":
                            if (currentCompany != null) {
                                currentCompany.setLocation(reader.getElementText());
                            }
                            break;
                        case "createdAt":
                            if (currentCompany != null) {
                                currentCompany.setCreatedAt(Timestamp.valueOf(reader.getElementText().replace("T", " ")));
                            } else if (currentCustomer != null) {
                                currentCustomer.setCreatedAt(Timestamp.valueOf(reader.getElementText().replace("T", " ")));
                            }
                            break;
                        // Set fields for Customer
                        case "firstName":
                            if (currentCustomer != null) {
                                currentCustomer.setFirstName(reader.getElementText());
                            }
                            break;
                        case "lastName":
                            if (currentCustomer != null) {
                                currentCustomer.setLastName(reader.getElementText());
                            }
                            break;
                        case "email":
                            if (currentCustomer != null) {
                                currentCustomer.setEmail(reader.getElementText());
                            }
                            break;
                        case "phoneNumber":
                            if (currentCustomer != null) {
                                currentCustomer.setPhoneNumber(reader.getElementText());
                            }
                            break;
                        // Set fields for Order
                        case "quantity":
                            if (currentOrder != null) {
                                currentOrder.setQuantity(Integer.parseInt(reader.getElementText()));
                            }
                            break;
                        case "totalPrice":
                            if (currentOrder != null) {
                                currentOrder.setTotalPrice(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        case "orderDate":
                            if (currentOrder != null) {
                                currentOrder.setOrderDate(Timestamp.valueOf(reader.getElementText().replace("T", " ")));
                            }
                            break;
                        // Set fields for Vehicle
                        case "licensePlate":
                            if (currentVehicle != null) {
                                currentVehicle.setLicensePlate(reader.getElementText());
                            }
                            break;
                        case "vehicleType":
                            if (currentVehicle != null) {
                                currentVehicle.setVehicleType(reader.getElementText());
                            }
                            break;
                        case "capacity":
                            if (currentVehicle != null) {
                                currentVehicle.setCapacity(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        // Set fields for Invoice
                        case "amount":
                            if (currentInvoice != null) {
                                currentInvoice.setAmount(Double.parseDouble(reader.getElementText()));
                            }
                            break;
                        case "invoiceDate":
                            if (currentInvoice != null) {
                                currentInvoice.setInvoiceDate(Timestamp.valueOf(reader.getElementText().replace("T", " ")));
                            }
                            break;
                    }
                } else if (event == XMLStreamReader.END_ELEMENT) {
                    String elementName = reader.getLocalName();
                    switch (elementName) {
                        case "company":
                            companies.add(currentCompany);
                            currentCompany = null;
                            break;
                        case "customer":
                            addCustomerToCompany(currentCompany, currentCustomer);
                            currentCustomer = null;
                            break;
                        case "order":
                            addOrderToCustomer(currentCustomer, currentOrder);
                            currentOrder = null;
                            break;
                        case "vehicle":
                            addVehicleToCompany(currentCompany, currentVehicle);
                            currentVehicle = null;
                            break;
                        case "invoice":
                            if (currentOrder != null && currentInvoice != null) {
                                currentOrder.setInvoice(currentInvoice);
                                currentInvoice = null;
                            }
                            break;
                    }
                }
            }
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
        return companies;
    }


    private static void addCustomerToCompany(Company currentCompany, Customer currentCustomer) {
        if (currentCompany != null && currentCustomer != null) {
            currentCustomer.setCompanyId(currentCompany.getId());
            if (currentCompany.getCustomers() == null) {
                currentCompany.setCustomers(new ArrayList<>());
            }
            currentCompany.getCustomers().add(currentCustomer);
        }
    }

    private static void addOrderToCustomer(Customer currentCustomer, Order currentOrder) {
        if (currentCustomer != null && currentOrder != null) {
            currentOrder.setCustomerId(currentCustomer.getId());
            if (currentCustomer.getOrders() == null) {
                currentCustomer.setOrders(new ArrayList<>());
            }
            currentCustomer.getOrders().add(currentOrder);
        }
    }

    private static void addVehicleToCompany(Company currentCompany, Vehicle currentVehicle) {
        if (currentCompany != null && currentVehicle != null) {
            currentVehicle.setCompanyId(currentCompany.getId());
            if (currentCompany.getVehicles() == null) {
                currentCompany.setVehicles(new ArrayList<>());
            }
            currentCompany.getVehicles().add(currentVehicle);
        }
    }


    private static void printCompanies(List<Company> companies) {
        for (Company company : companies) {
            System.out.printf("Company: %s, ID: %d, Location: %s, Created At: %s%n",
                    company.getName(), company.getId(), company.getLocation(), company.getCreatedAt());

            if (company.getCustomers() != null) {
                for (Customer customer : company.getCustomers()) {
                    System.out.printf("\tCustomer: %s %s, Email: %s, Phone Number: %s%n",
                            customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhoneNumber());

                    if (customer.getOrders() != null) {
                        for (Order order : customer.getOrders()) {
                            System.out.printf("\t\tOrder: ID: %d, Quantity: %d, Total Price: %.2f%n",
                                    order.getId(), order.getQuantity(), order.getTotalPrice());

                            if (order.getInvoice() != null) {
                                System.out.printf("\t\t\tInvoice: ID: %d, Amount: %.2f, Date: %s%n",
                                        order.getInvoice().getId(), order.getInvoice().getAmount(), order.getInvoice().getInvoiceDate());
                            }
                        }
                    }
                }
            }

            if (company.getVehicles() != null) {
                for (Vehicle vehicle : company.getVehicles()) {
                    System.out.printf("\tVehicle: ID: %d, License Plate: %s, Type: %s, Capacity: %.2f%n",
                            vehicle.getId(), vehicle.getLicensePlate(), vehicle.getVehicleType(), vehicle.getCapacity());
                }
            }
        }
    }
}
