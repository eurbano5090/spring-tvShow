package com.bolsadeideas.springboot.app.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Usuario;

   @Repository
   public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
   Usuario findByUsername(String username);
   List<Usuario> findByFechaRegistroNotNull();

   @Modifying
   @Query("UPDATE Usuario u SET u.estatus=0 WHERE u.id = ?1")
   int lock(@Param("id") int id);

   @Modifying
   @Query("UPDATE Usuario u SET u.estatus=1 WHERE u.id = ?1")
    int unlock(@Param("id") int id); 
}
