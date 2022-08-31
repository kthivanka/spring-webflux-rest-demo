package com.spring.webfluxrest.controllers;

import com.spring.webfluxrest.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
    private CategoryRepository vendorRepository;

    public CategoryController(CategoryRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
}
