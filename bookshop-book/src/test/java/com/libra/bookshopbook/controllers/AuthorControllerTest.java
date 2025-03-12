package com.libra.bookshopbook.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libra.bookshopbook.services.AuthorService;
import com.libra.bookshopmodel.dto.author.AuthorCreateRequest;
import com.libra.bookshopmodel.dto.author.AuthorDto;
import com.libra.bookshopmodel.dto.author.AuthorUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthorController.class)
@ContextConfiguration(classes = TestApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorDto authorDto;
    private AuthorCreateRequest authorCreateRequest;
    private AuthorUpdateRequest authorUpdateRequest;

    @BeforeEach
    void setUp() {
        authorDto = new AuthorDto(1L, "John", "Doe", Collections.emptyList());
        authorCreateRequest = new AuthorCreateRequest("John", "Doe", Collections.emptyList());
        authorUpdateRequest = new AuthorUpdateRequest("John", "Doe", Collections.emptyList());
    }

    @Test
    void findAllAuthors() throws Exception {
        List<AuthorDto> authors = Collections.singletonList(authorDto);

        when(authorService.findAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(authorDto.getId()))
                .andExpect(jsonPath("$[0].firstName").value(authorDto.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(authorDto.getLastName()));
    }

    @Test
    void findById() throws Exception {
        when(authorService.findAuthorById(1L)).thenReturn(authorDto);

        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.firstName").value(authorDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(authorDto.getLastName()));
    }

    @Test
    void createAuthor() throws Exception {
        when(authorService.createAuthor(any(AuthorCreateRequest.class))).thenReturn(authorDto);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.firstName").value(authorDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(authorDto.getLastName()));
    }

    @Test
    void updateAuthor() throws Exception {
        when(authorService.updateAuthor(eq(1L), any(AuthorUpdateRequest.class))).thenReturn(authorDto);

        mockMvc.perform(put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorDto.getId()))
                .andExpect(jsonPath("$.firstName").value(authorDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(authorDto.getLastName()));
    }

    @Test
    void deleteAuthor() throws Exception {
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isOk());

        verify(authorService).deleteAuthor(1L);
    }
}