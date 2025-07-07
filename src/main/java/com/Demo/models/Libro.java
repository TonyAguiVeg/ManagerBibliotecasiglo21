package com.Demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.Data;


@Entity
@Data
@Table(name="tb_libro")
public class Libro {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_libro")
	private long idLibro;
	
	@Column(name="titulo", length=100)
	private String titulo;
	
	@Column(name="isbn",unique=true,length=15)
	private String isbn;
	
	@Column(name="autor", length=100)
	private String autor;
	
	@Min(0)
	@Column(name="anio_publ")
	private int anioPublic;
	
	@Column(name="disponible", nullable=false)
	private boolean disponible=true;
	
	
}
