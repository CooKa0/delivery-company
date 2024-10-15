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
        // Initialize DAO implementations
        ICompanyService companyService = new CompanyServiceImpl(new CompanyDAOImpl());
        ICustomerService customerService = new CustomerServiceImpl(new CustomerDAOImpl());
        IOrderService orderService = new OrderServiceImpl(new OrderDAOImpl());
        IInvoiceService invoiceService = new InvoiceServiceImpl(new InvoiceDAOImpl());
        IVehicleService vehicleService = new VehicleServiceImpl(new VehicleDAOImpl());

        Connection conn = null;

        try {
            // Create new companies
            Company company1 = new Company(null, "Tech Solutions", "New York", new Timestamp(System.currentTimeMillis()));
            Company company2 = new Company(null, "Logistics Corp", "Los Angeles", new Timestamp(System.currentTimeMillis()));
            companyService.create(company1);
            companyService.create(company2);

            // Get a connection from the pool
            conn = ConnectionPool.getInstance().getConnection();

            // Check for existing customers or create new ones
            Customer customer1 = customerService.findByEmail("john.doe@example.com");
            if (customer1 == null) {
                customer1 = new Customer(null, company1.getId(), "John", "Doe", "john.doe@example.com", "1234567890", new Timestamp(System.currentTimeMillis()));
                customerService.create(customer1);
            }

            Customer customer2 = customerService.findByEmail("jane.smith@example.com");
            if (customer2 == null) {
                customer2 = new Customer(null, company1.getId(), "Jane", "Smith", "jane.smith@example.com", "0987654321", new Timestamp(System.currentTimeMillis()));
                customerService.create(customer2);
            }

            // Create new orders
            Order order1 = new Order(null, customer1.getId(), new Timestamp(System.currentTimeMillis()), 200.00);
            Order order2 = new Order(null, customer2.getId(), new Timestamp(System.currentTimeMillis()), 150.00);
            orderService.create(order1);
            orderService.create(order2);

            // Create new invoices for the orders
            Invoice invoice1 = new Invoice(null, order1.getId(), new Timestamp(System.currentTimeMillis()), 200.00);
            Invoice invoice2 = new Invoice(null, order2.getId(), new Timestamp(System.currentTimeMillis()), 150.00);
            invoiceService.create(invoice1);
            invoiceService.create(invoice2);

            // Create new vehicles for the companies
            Vehicle vehicle1 = new Vehicle(null, company1.getId(), "ABC-124", "Truck", 1000.0);
            Vehicle vehicle2 = new Vehicle(null, company2.getId(), "XYZ-567", "Van", 800.0);

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
