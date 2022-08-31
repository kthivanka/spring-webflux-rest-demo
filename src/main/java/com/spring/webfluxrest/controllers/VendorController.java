package com.spring.webfluxrest.controllers;

import com.spring.webfluxrest.repositories.VendorRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
}
