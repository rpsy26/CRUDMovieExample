package com.appinventive.SpringCRUDPOC.repository;

import com.appinventive.SpringCRUDPOC.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,String> {
}
