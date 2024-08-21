package com.nit.service;

import com.nit.dto.SupplierQueryDTO;
import com.nit.entity.Supplier;

import java.util.List;

public interface SupplierService {
    Boolean registerSupplier(Supplier supplier);

    List<Supplier> getSuppliers(SupplierQueryDTO queryDTO);
}
