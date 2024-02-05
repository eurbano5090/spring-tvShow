package com.bolsadeideas.springboot.app.models.entity;


import java.util.List;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="ratings")
public class Rating  {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rating_id")
	private Integer id;

	@Min(1)
	@Max(5)
	@Nullable
	private Integer rating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Usuario user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Show show;
	

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Rating() {
		super();
	}

	public Rating(Integer id, Integer rating) {
		super();
		this.id = id;
		this.rating = rating;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}


	
	public void setRating(int rating) {
		this.rating = rating;
	}

	
	
	

}
