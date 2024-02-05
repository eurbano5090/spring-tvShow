package com.bolsadeideas.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.bolsadeideas.springboot.app.models.entity.Show;
import com.bolsadeideas.springboot.app.repositories.ShowRepository;
import com.bolsadeideas.springboot.app.service.IUploadFileService;


@SpringBootApplication
public class SpringBootDataJpaApplication  {

	 private static final Logger log= LoggerFactory.getLogger(SpringBootDataJpaApplication.class);
		
		public static void main(String[] args) {
		
			SpringApplication.run(SpringBootDataJpaApplication.class);
	

	}

}
