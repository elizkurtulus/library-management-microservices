package com.turkcell.loan_services.enums;

public enum LoanStatus {
    OPEN, // Kitap hâlâ kullanıcıda
    CLOSED, // Zamanında veya geç iade edildi
    LATE // Geç iade (fine oluşabilir)
}
