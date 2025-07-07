package com.Demo.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface LibroLoanManager {

	ResponseEntity<Map<String, Object>> prestarLibro(String isbn);

    ResponseEntity<Map<String, Object>> devolverLibro(String isbn);
}
