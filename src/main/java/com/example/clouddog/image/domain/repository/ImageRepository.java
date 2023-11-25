package com.example.clouddog.image.domain.repository;

import com.example.clouddog.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageByConvertImageNameContaining(String fileName);
}
