package com.turkcell.member_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.member_services.dto.MemberRequest;
import com.turkcell.member_services.dto.MemberResponse;
import com.turkcell.member_services.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMember_Success() throws Exception {
        // Given
        MemberRequest request = new MemberRequest();
        request.memberNumber = "MEM001";
        request.firstName = "Ahmet";
        request.lastName = "Yılmaz";
        request.email = "ahmet@example.com";
        request.phoneNumber = "5551234567";

        MemberResponse response = new MemberResponse();
        response.id = 1L;
        response.memberNumber = "MEM001";
        response.firstName = "Ahmet";
        response.lastName = "Yılmaz";
        response.email = "ahmet@example.com";
        response.phoneNumber = "5551234567";
        response.status = "ACTIVE";
        response.createdAt = LocalDateTime.now();

        when(memberService.createMember(any(MemberRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.memberNumber").value("MEM001"))
                .andExpect(jsonPath("$.email").value("ahmet@example.com"));
    }

    @Test
    void createMember_ValidationError() throws Exception {
        // Given
        MemberRequest request = new MemberRequest();
        // Eksik alanlar - validation hatası

        // When & Then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMemberById_Success() throws Exception {
        // Given
        MemberResponse response = new MemberResponse();
        response.id = 1L;
        response.memberNumber = "MEM001";
        response.firstName = "Ahmet";
        response.email = "ahmet@example.com";

        when(memberService.getMemberById(1L)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.memberNumber").value("MEM001"));
    }

    @Test
    void deleteMember_Success() throws Exception {
        // Given
        doNothing().when(memberService).deleteMember(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/members/1"))
                .andExpect(status().isNoContent());
    }
}

