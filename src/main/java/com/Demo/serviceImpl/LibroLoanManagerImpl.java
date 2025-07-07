package com.Demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Demo.models.Libro;
import com.Demo.repository.LibroRepository;
import com.Demo.service.LibroLoanManager;

@Service
public class LibroLoanManagerImpl implements LibroLoanManager  {
	
	@Autowired
    private LibroRepository libroRepository;

	@Override
    public ResponseEntity<Map<String, Object>> prestarLibro(String isbn) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            Optional<Libro> libroOpt = libroRepository.findByIsbn(isbn);
            if (libroOpt.isEmpty()) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "Libro no encontrado.");
                response.put("data", null);
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Libro libro = libroOpt.get();
            if (!libro.isDisponible()) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "El libro ya está prestado.");
                response.put("data", libro);
                response.put("status", HttpStatus.CONFLICT.value());
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            libro.setDisponible(false);
            libroRepository.save(libro);

            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Libro prestado correctamente.");
            response.put("data", libro);
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Error al prestar el libro.");
            response.put("data", null);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> devolverLibro(String isbn) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            Optional<Libro> libroOpt = libroRepository.findByIsbn(isbn);
            if (libroOpt.isEmpty()) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "Libro no encontrado.");
                response.put("data", null);
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Libro libro = libroOpt.get();
            if (libro.isDisponible()) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "El libro ya está disponible (no está prestado).");
                response.put("data", libro);
                response.put("status", HttpStatus.CONFLICT.value());
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            libro.setDisponible(true);
            libroRepository.save(libro);

            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Libro devuelto correctamente.");
            response.put("data", libro);
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Error al devolver el libro.");
            response.put("data", null);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
