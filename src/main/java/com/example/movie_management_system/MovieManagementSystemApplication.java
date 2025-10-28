package com.example.movie_management_system;

import com.example.movie_management_system.model.Movie;
import com.example.movie_management_system.repository.MovieRepository;
import com.example.movie_management_system.repository.Repository;
import com.example.movie_management_system.service.MovieService;
import com.example.movie_management_system.service.Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieManagementSystemApplication.class, args);
	}




}
