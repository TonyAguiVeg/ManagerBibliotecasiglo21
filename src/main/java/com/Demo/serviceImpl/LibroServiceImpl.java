package com.Demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Demo.models.Libro;
import com.Demo.repository.LibroRepository;
import com.Demo.service.LibroService;

@Service
public class LibroServiceImpl implements LibroService{

	@Autowired
	private LibroRepository libroRepository;
	
	public ResponseEntity<Map<String, Object>> listarLibros(String titulo, String autor, String isbn) {
	    Map<String, Object> response = new LinkedHashMap<>();
	    try {
	        
	        titulo = titulo != null ? titulo : "";
	        autor = autor != null ? autor : "";
	        isbn = isbn != null ? isbn : "";

	        List<Libro> libros = libroRepository
	                .findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCaseAndIsbnContainingIgnoreCase(titulo, autor, isbn);

	        response.put("fecha", LocalDateTime.now());
	        response.put("mensaje", libros.isEmpty() ? "No hay libros registrados." : "Listado de libros.");
	        response.put("data", libros);
	        response.put("status", HttpStatus.OK.value());
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	        response.put("fecha", LocalDateTime.now());
	        response.put("mensaje", "Error al listar los libros.");
	        response.put("data", null);
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@Override
    public ResponseEntity<Map<String, Object>> buscarPorIsbn(String isbn) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            Optional<Libro> libro = libroRepository.findByIsbn(isbn);
            if (libro.isPresent()) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "Libro encontrado.");
                response.put("data", libro.get());
                response.put("status", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "Libro no encontrado.");
                response.put("data", null);
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Error al buscar el libro.");
            response.put("data", null);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@Override
	public ResponseEntity<Map<String, Object>> agregarLibro(Libro libro) {
	    Map<String, Object> response = new LinkedHashMap<>();
	    try {
	        
	        if (libroRepository.existsByIsbn(libro.getIsbn())) {
	            response.put("fecha", LocalDateTime.now());
	            response.put("mensaje", "El ISBN ya está registrado.");
	            response.put("data", null);
	            response.put("status", HttpStatus.CONFLICT.value());
	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	        }

	        Libro guardado = libroRepository.save(libro);

	        response.put("fecha", LocalDateTime.now());
	        response.put("mensaje", "Libro registrado correctamente.");
	        response.put("data", guardado);
	        response.put("status", HttpStatus.CREATED.value());
	        return new ResponseEntity<>(response, HttpStatus.CREATED);

	    } catch (Exception e) {
	        response.put("fecha", LocalDateTime.now());
	        response.put("mensaje", "Error al registrar el libro.");
	        response.put("data", null);
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@Override
    public ResponseEntity<Map<String, Object>> eliminarPorIsbn(String isbn) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            if (!libroRepository.existsByIsbn(isbn)) {
                response.put("fecha", LocalDateTime.now());
                response.put("mensaje", "Libro no encontrado con el ISBN proporcionado.");
                response.put("data", null);
                response.put("status", HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            libroRepository.deleteByIsbn(isbn);
            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Libro eliminado correctamente.");
            response.put("data", null);
            response.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("fecha", LocalDateTime.now());
            response.put("mensaje", "Error al eliminar el libro.");
            response.put("data", null);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	

	

	@Override
	public boolean existeIsbn(String isbn) {
		
		return libroRepository.existsByIsbn(isbn);
	}

}
