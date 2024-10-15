package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.ICompanyDAO;
import com.solvd.laba.delivery.models.Company;
import com.solvd.laba.delivery.services.ICompanyService;

import java.util.List;

public class CompanyServiceImpl implements ICompanyService {
    private final ICompanyDAO companyDAO;

    public CompanyServiceImpl(ICompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public void create(Company company) {
        companyDAO.create(company);
    }

    @Override
    public Company findById(Long id) {
        return companyDAO.findById(id);
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
        return companyDAO.findAll();
    }
}
