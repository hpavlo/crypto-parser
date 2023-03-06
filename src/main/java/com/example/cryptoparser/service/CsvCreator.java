package com.example.cryptoparser.service;

import jakarta.servlet.http.HttpServletResponse;

public interface CsvCreator {
    void createResponse(HttpServletResponse response);
}
