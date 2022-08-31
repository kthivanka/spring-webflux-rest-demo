package com.spring.webfluxrest.controllers;

import com.spring.webfluxrest.domain.Vendor;
import com.spring.webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux
                .just(Vendor.builder()
                                .firstName("Davy")
                                .lastName("Jones")
                                .build(),
                        Vendor.builder()
                                .firstName("Jack")
                                .lastName("Sparrow")
                                .build()
                ));
        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().firstName("Fred").lastName("Flintstones").build()));

        webTestClient.get().uri("/api/v1/vendors/foo")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    void testCreateVendor(){
        BDDMockito.given((vendorRepository.saveAll(any(Publisher.class))))
                .willReturn(Flux.just(Vendor.builder().firstName(anyString()).lastName(anyString()).build()));

        Mono<Vendor> vendorMonoToSave = Mono.just(Vendor.builder().firstName("jack").lastName("sparrow").build());

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMonoToSave, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void testUpdateVendor(){
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder().firstName("jack").lastName("sparrow").build());

        webTestClient.put()
                .uri("/api/v1/vendors/foo")
                .body(vendorMonoToUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}