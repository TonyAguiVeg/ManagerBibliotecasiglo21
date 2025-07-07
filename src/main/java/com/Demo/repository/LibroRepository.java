package com.Demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Demo.models.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>{
	
	List<Libro> findByTituloContainingIgnoreCaseAndAutorContainingIgnoreCaseAndIsbnContainingIgnoreCase(
			String titulo,String autor,String isbn);
	
	Optional<Libro> findByIsbn(String isbn);
	
	void deleteByIsbn(String isbn);
	
	boolean existsByIsbn(String isbn);
}
