package com.spring.webfluxrest.controllers;

import com.spring.webfluxrest.domain.Category;
import com.spring.webfluxrest.repositories.CategoryRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
        given(categoryRepository.findAll())
                .willReturn(Flux.just(
                        Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()
                ));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById() {
        given(
                categoryRepository
                        .findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat1").build()));

        webTestClient.get().uri("/api/v1/categories/foo")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreateCategory(){
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryToSavedMono = Mono.just(Category.builder().description("Cat1").build());
        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryToSavedMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateCategory(){
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToUpdateMono = Mono.just(Category.builder().description("Cat1").build());
        webTestClient.put()
                .uri("/api/v1/categories/foo")
                .body(categoryToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void testPatchWithChanges(){
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("").build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("").build()));

        Mono<Category> categoryToUpdateMono = Mono.just(Category.builder().description("Cat1").build());
        webTestClient.patch()
                .uri("/api/v1/categories/foo")
                .body(categoryToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        verify(categoryRepository).save(any());
    }

    @Test
    public void testPatchNoChanges() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("").build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("").build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("").build());

        webTestClient.patch()
                .uri("/api/v1/categories/asdfgh")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());
    }
}