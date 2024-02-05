package com.bolsadeideas.springboot.app.models.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shows")
public class Show{



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "show_id", nullable = false, unique = true)
	private Integer id;


	private String showTitle;

	
	private String showNetwork;

	
	@OneToMany(mappedBy = "rating",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Rating> ratings;
	
	@Column
	private String foto;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="duracion")
	private String duracion;
	
	@Column
	private Integer destacado;
	
	@OneToOne
	@JoinColumn(name = "idCategoria")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Actor actor;
	
	public Show() {
		ratings=new ArrayList<Rating>();
	}

	
	public void addRating(Rating rating) {
		ratings.add(rating);
	}
	

	public void reset() {
		this.foto=null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public String getShowNetwork() {
		return showNetwork;
	}

	public void setShowNetwork(String showNetwork) {
		this.showNetwork = showNetwork;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Integer getDestacado() {
		return destacado;
	}

	public void setDestacado(Integer destacado) {
		this.destacado = destacado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	


}
