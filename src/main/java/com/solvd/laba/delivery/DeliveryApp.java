package com.solvd.laba.delivery;

import com.solvd.laba.delivery.connections.ConnectionPool;
import com.solvd.laba.delivery.dao.impl.CompanyDAOImpl;
import com.solvd.laba.delivery.dao.impl.CustomerDAOImpl;
import com.solvd.laba.delivery.dao.impl.OrderDAOImpl;
import com.solvd.laba.delivery.dao.impl.InvoiceDAOImpl;
import com.solvd.laba.delivery.dao.impl.VehicleDAOImpl;
import com.solvd.laba.delivery.models.Company;
import com.solvd.laba.delivery.models.Customer;
import com.solvd.laba.delivery.models.Order;
import com.solvd.laba.delivery.models.Invoice;
import com.solvd.laba.delivery.models.Vehicle;
import com.solvd.laba.delivery.services.ICompanyService;
import com.solvd.laba.delivery.services.ICustomerService;
import com.solvd.laba.delivery.services.IOrderService;
import com.solvd.laba.delivery.services.IInvoiceService;
import com.solvd.laba.delivery.services.IVehicleService;
import com.solvd.laba.delivery.services.impl.CompanyServiceImpl;
import com.solvd.laba.delivery.services.impl.CustomerServiceImpl;
import com.solvd.laba.delivery.services.impl.OrderServiceImpl;
import com.solvd.laba.delivery.services.impl.InvoiceServiceImpl;
import com.solvd.laba.delivery.services.impl.VehicleServiceImpl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class DeliveryApp {
    public static void main(String[] args) {

        // Initialize implementations
        IInvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl());
        IOrderService orderService = new OrderServiceImpl(new OrderDAOImpl(), invoiceService);
        ICustomerService customerService = new CustomerServiceImpl(new CustomerDAOImpl(), orderService);
        IVehicleService vehicleService = new VehicleServiceImpl(new VehicleDAOImpl());
        ICompanyService companyService = new CompanyServiceImpl(new CompanyDAOImpl(), customerService, vehicleService);

        Connection conn = null;

        try {
            // Create new companies
            Company company1 = Company.builder()
                    .name("Tech Solutions")
                    .location("New York")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            Company company2 = Company.builder()
                    .name("Logistics Corp")
                    .location("Los Angeles")
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            companyService.create(company1);
            companyService.create(company2);

            // Get a connection from the pool
            conn = ConnectionPool.getInstance().getConnection();

            // Check for existing customers or create new ones
            Customer customer1 = customerService.findByEmail("john.doe@example.com");
            if (customer1 == null) {
                customer1 = Customer.builder()
                        .companyId(company1.getId())
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .phoneNumber("1234567890")
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .build();
                customerService.create(customer1);
            }

            Customer customer2 = customerService.findByEmail("jane.smith@example.com");
            if (customer2 == null) {
                customer2 = Customer.builder()
                        .companyId(company1.getId())
                        .firstName("Jane")
                        .lastName("Smith")
                        .email("jane.smith@example.com")
                        .phoneNumber("0987654321")
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .build();
                customerService.create(customer2);
            }

            // Create new orders
            Order order1 = Order.builder()
                    .customerId(customer1.getId())
                    .companyId(customer1.getCompanyId())
                    .orderDate(new Timestamp(System.currentTimeMillis()))
                    .totalPrice(200.00)
                    .quantity(2)
                    .build();

            Order order2 = Order.builder()
                    .customerId(customer2.getId())
                    .companyId(customer2.getCompanyId())
                    .orderDate(new Timestamp(System.currentTimeMillis()))
                    .totalPrice(150.00)
                    .quantity(1)
                    .build();

            orderService.create(order1);
            orderService.create(order2);


            // Create new invoices for the orders
            Invoice invoice1 = Invoice.builder()
                    .orderId(order1.getId())
                    .invoiceDate(new Timestamp(System.currentTimeMillis()))
                    .amount(200.00)
                    .build();

            Invoice invoice2 = Invoice.builder()
                    .orderId(order2.getId())
                    .invoiceDate(new Timestamp(System.currentTimeMillis()))
                    .amount(150.00)
                    .build();

            invoiceService.create(invoice1);
            invoiceService.create(invoice2);

            // Create new vehicles for the companies
            Vehicle vehicle1 = Vehicle.builder()
                    .companyId(company1.getId())
                    .licensePlate("ABC-128")
                    .vehicleType("Truck")
                    .capacity(1000.0)
                    .build();

            Vehicle vehicle2 = Vehicle.builder()
                    .companyId(company2.getId())
                    .licensePlate("XYZ-568")
                    .vehicleType("Van")
                    .capacity(800.0)
                    .build();

            vehicleService.create(vehicle1);
            System.out.println("Vehicle created: " + vehicle1);
            vehicleService.create(vehicle2);
            System.out.println("Vehicle created: " + vehicle2);

            // Retrieve and print all companies
            List<Company> companies = companyService.findAll();
            System.out.println("Companies:");
            companies.forEach(System.out::println);

            // Retrieve and print all customers
            List<Customer> customers = customerService.findAll();
            System.out.println("Customers:");
            customers.forEach(System.out::println);

            // Retrieve and print all orders
            List<Order> orders = orderService.findAll();
            System.out.println("Orders:");
            orders.forEach(System.out::println);

            // Retrieve and print all invoices
            List<Invoice> invoices = invoiceService.findAll();
            System.out.println("Invoices:");
            invoices.forEach(System.out::println);

            // Retrieve and print all vehicles
            List<Vehicle> vehicles = vehicleService.findAll();
            System.out.println("Vehicles:");
            vehicles.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        } finally {
            // Release the connection back to the pool
            if (conn != null) {
                ConnectionPool.getInstance().releaseConnection(conn);
            }
        }
    }
}
