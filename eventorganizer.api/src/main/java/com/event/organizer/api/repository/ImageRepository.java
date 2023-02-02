package com.event.organizer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.event.organizer.api.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
