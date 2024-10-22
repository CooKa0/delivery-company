package com.solvd.laba.delivery.dao;

import com.solvd.laba.delivery.models.Company;

import java.util.List;

public interface ICompanyDAO extends IGenericDAO<Company, Long> {
    Company findCompanyDetails(Long companyId);
}
