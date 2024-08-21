package com.nit.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "Supplier_Details")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer id;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(name = "company_name")
    private String companyName;

    @NotEmpty
    private String website;

//    @Valid
    @JsonManagedReference
    @OneToOne(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Location location;

    @NotEmpty
    @Column(name = "nature_of_business")
    private String  natureOfBusiness;

    @NotEmpty
    @Column(name = "manufacturing_processes")
    private String manufacturingProcesses;
}
