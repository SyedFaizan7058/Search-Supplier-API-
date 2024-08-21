package com.nit.restcontroller;

import com.nit.dto.SupplierQueryDTO;
import com.nit.entity.Supplier;
import com.nit.exception.SupplierNotFoundException;
import com.nit.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/supplier")//Url starts with this
public class SupplierRestController {

    private final SupplierService supplierService;

    //Constructor injection
    //Here @Autowired is not mandatory it is optional
    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    //Endpoint to register supplier
    @PostMapping(value = "/register", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addSupplier(@RequestBody @Valid Supplier supplier) {

        Boolean registeredSupplier = supplierService.registerSupplier(supplier);
        //Condition to check supplier is registered or not
        if (!registeredSupplier) {
            //Sending failed message with status code 400
            return new ResponseEntity<>("Supplier registration failed, please try again", HttpStatus.BAD_REQUEST);
        }
        //Sending success message with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body("Supplier registered successfully");
    }

    //Endpoint to get supplier based on given values
    @PostMapping(value = "/query", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Supplier>> querySuppliers(@RequestBody SupplierQueryDTO queryDTO) {

        List<Supplier> suppliers = supplierService.getSuppliers(queryDTO);
        //Condition to check suppliers are available or not
        if (suppliers.isEmpty()) {
            //Throwing SupplierNotFoundException exception
            throw new SupplierNotFoundException("Supplier not found");
        }
        //Sending the suppliers as a response with status code 200
        return ResponseEntity.ok().body(suppliers);
    }

}
