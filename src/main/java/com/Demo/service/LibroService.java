package com.Demo.service;


import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.Demo.models.Libro;

public interface LibroService {

	ResponseEntity<Map<String, Object>> agregarLibro(Libro libro);

	ResponseEntity<Map<String, Object>> eliminarPorIsbn(String isbn);

    ResponseEntity<Map<String, Object>> buscarPorIsbn(String isbn);

    ResponseEntity<Map<String, Object>> listarLibros(String titulo, String autor, String isbn);

    boolean existeIsbn(String isbn);
}
