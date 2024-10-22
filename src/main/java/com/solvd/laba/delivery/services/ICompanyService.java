package com.solvd.laba.delivery.services;

import com.solvd.laba.delivery.models.Company;

import java.util.List;

public interface ICompanyService {
    void create(Company company);
    Company findById(Long id);
    void update(Company company);
    void delete(Long id);
    List<Company> findAll();
    Company findCompanyDetails(Long companyId);
}
