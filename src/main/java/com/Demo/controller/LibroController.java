package com.Demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Demo.models.Libro;
import com.Demo.service.LibroLoanManager;
import com.Demo.service.LibroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private LibroLoanManager loanManager;

    @PostMapping("/registrar")
    public ResponseEntity<Map<String, Object>> registrarLibro(@Valid @RequestBody Libro libro) {
        return libroService.agregarLibro(libro);
    }


    @GetMapping("/buscar/{isbn}")
    public ResponseEntity<Map<String, Object>> buscarPorISBN(@PathVariable String isbn) {
        return libroService.buscarPorIsbn(isbn);
    }


    @DeleteMapping("/eliminar/{isbn}")
    public ResponseEntity<Map<String, Object>> eliminarPorISBN(@PathVariable String isbn) {
        return libroService.eliminarPorIsbn(isbn);
    }


    @GetMapping("/libros")
    public ResponseEntity<Map<String, Object>> listarLibros(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String autor,
        @RequestParam(required = false) String isbn) {

        return libroService.listarLibros(titulo, autor, isbn);
    }
    
    @PostMapping("/prestar/{isbn}")
    public ResponseEntity<Map<String, Object>> prestarLibro(@PathVariable String isbn) {
        return loanManager.prestarLibro(isbn);
    }

   
    @PostMapping("/devolver/{isbn}")
    public ResponseEntity<Map<String, Object>> devolverLibro(@PathVariable String isbn) {
        return loanManager.devolverLibro(isbn);
    }
}