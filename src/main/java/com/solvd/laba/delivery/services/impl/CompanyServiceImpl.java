package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.ICompanyDAO;
import com.solvd.laba.delivery.models.Company;
import com.solvd.laba.delivery.services.ICompanyService;
import com.solvd.laba.delivery.services.ICustomerService;
import com.solvd.laba.delivery.services.IVehicleService;

import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    private final ICompanyDAO companyDAO;
    private final ICustomerService customerService;
    private final IVehicleService vehicleService;

    public CompanyServiceImpl(ICompanyDAO companyDAO, ICustomerService customerService, IVehicleService vehicleService) {
        this.companyDAO = companyDAO;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    @Override
    public void create(Company company) {
        companyDAO.create(company);
    }

    @Override
    public Company findById(Long id) {
        Company company = companyDAO.findById(id);
        if (company != null) {
            company.setCustomers(customerService.findByCompanyId(id));
            company.setVehicles(vehicleService.findByCompanyId(id));
        }
        return company;
    }

    @Override
    public void update(Company company) {
        companyDAO.update(company);
    }

    @Override
    public void delete(Long id) {
        companyDAO.delete(id);
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = companyDAO.findAll();

        for (Company company : companies) {
            company.setCustomers(customerService.findByCompanyId(company.getId()));
            company.setVehicles(vehicleService.findByCompanyId(company.getId()));
        }

        return companies;
    }

    @Override
    public Company findCompanyDetails(Long companyId) {
        return companyDAO.findCompanyDetails(companyId);
    }
}
