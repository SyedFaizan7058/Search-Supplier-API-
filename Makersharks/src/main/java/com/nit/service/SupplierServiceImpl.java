package com.nit.service;

import com.nit.dto.SupplierQueryDTO;
import com.nit.entity.Location;
import com.nit.entity.Supplier;
import com.nit.repository.SupplierRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    //Constructor injection
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Boolean registerSupplier(Supplier supplier) {

        //Setting the supplier id in location entity
        supplier.getLocation().setSupplier(supplier);
        //Adding the supplier to the database
        Supplier savedSupplier = supplierRepository.save(supplier);
        //Condition to check generated value is not null
        return savedSupplier.getId() != null;
    }

    @Override
    public List<Supplier> getSuppliers(SupplierQueryDTO queryDTO) {

        Supplier supplier = new Supplier();
        Location location = new Location();

        location.setCity(queryDTO.getCity());
        supplier.setLocation(location);
        supplier.setNatureOfBusiness(queryDTO.getNatureOfBusiness());
        supplier.setManufacturingProcesses(queryDTO.getManufacturingProcesses());

        //Fetching the data using query by example
        Example<Supplier> supplierExample = Example.of(supplier);
        return supplierRepository.findAll(supplierExample);
    }
}
