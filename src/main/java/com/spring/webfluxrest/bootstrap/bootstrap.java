package com.spring.webfluxrest.bootstrap;

import com.spring.webfluxrest.domain.Category;
import com.spring.webfluxrest.domain.Vendor;
import com.spring.webfluxrest.repositories.CategoryRepository;
import com.spring.webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("#### LOADING DATA ####");
        if (categoryRepository.count().block() == 0) {
            categoryRepository.save(Category.builder().description("Fruits").build()).block();
            categoryRepository.save(Category.builder().description("Nuts").build()).block();
            categoryRepository.save(Category.builder().description("Breads").build()).block();
            categoryRepository.save(Category.builder().description("Meats").build()).block();
            categoryRepository.save(Category.builder().description("Eggs").build()).block();
            System.out.println("Loaded Categories: " + categoryRepository.count().block());
        }
        if (vendorRepository.count().block() == 0) {
            vendorRepository.save(Vendor.builder().firstName("Kaveen").lastName("Prabodhya").build()).block();
            vendorRepository.save(Vendor.builder().firstName("John").lastName("Cooper").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Rick").lastName("Johnson").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Chris").lastName("Warner").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Jennie").lastName("Lopez").build()).block();
            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }
    }
}
