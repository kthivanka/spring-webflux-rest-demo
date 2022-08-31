package com.spring.webfluxrest.controllers;

import com.spring.webfluxrest.domain.Vendor;
import com.spring.webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getVendorById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    Mono<Vendor> updateOne(@PathVariable String id, @RequestBody Vendor vendor){
        return vendorRepository.findById(id)
                .flatMap(foundVendor -> {
                    if(!foundVendor.getFirstName().equalsIgnoreCase(vendor.getFirstName())){
                        foundVendor.setFirstName(vendor.getFirstName());
                        return vendorRepository.save(foundVendor);
                    }
                    if(!foundVendor.getLastName().equalsIgnoreCase(vendor.getLastName())){
                        foundVendor.setLastName(vendor.getLastName());
                        return vendorRepository.save(foundVendor);
                    }
                    return Mono.just(foundVendor);
                } );
    }
}
