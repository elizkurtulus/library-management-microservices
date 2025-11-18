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
import java.util.List;

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

    @Test
    void updateMember_Success() throws Exception {
        // Given
        MemberRequest request = new MemberRequest();
        request.firstName = "Mehmet";
        request.lastName = "Demir";
        request.email = "mehmet@example.com";
        request.phoneNumber = "5559876543";

        MemberResponse response = new MemberResponse();
        response.id = 1L;
        response.memberNumber = "MEM001";
        response.firstName = "Mehmet";
        response.lastName = "Demir";
        response.email = "mehmet@example.com";
        response.phoneNumber = "5559876543";
        response.status = "ACTIVE";

        when(memberService.updateMember(eq(1L), any(MemberRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/v1/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Mehmet"))
                .andExpect(jsonPath("$.lastName").value("Demir"));
    }

    @Test
    void getMemberByMemberNumber_Success() throws Exception {
        // Given
        MemberResponse response = new MemberResponse();
        response.id = 1L;
        response.memberNumber = "MEM001";
        response.firstName = "Ahmet";
        response.lastName = "Yılmaz";
        response.email = "ahmet@example.com";

        when(memberService.getMemberByMemberNumber("MEM001")).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/members/by-member-number/MEM001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.memberNumber").value("MEM001"))
                .andExpect(jsonPath("$.firstName").value("Ahmet"));
    }

    @Test
    void getAllMembers_Success() throws Exception {
        // Given
        MemberResponse response1 = new MemberResponse();
        response1.id = 1L;
        response1.memberNumber = "MEM001";
        response1.firstName = "Ahmet";
        response1.email = "ahmet@example.com";

        MemberResponse response2 = new MemberResponse();
        response2.id = 2L;
        response2.memberNumber = "MEM002";
        response2.firstName = "Ayşe";
        response2.email = "ayse@example.com";

        when(memberService.getAllMembers()).thenReturn(List.of(response1, response2));

        // When & Then
        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].memberNumber").value("MEM001"))
                .andExpect(jsonPath("$[1].memberNumber").value("MEM002"));
    }

    @Test
    void getAllMembers_EmptyList() throws Exception {
        // Given
        when(memberService.getAllMembers()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}

