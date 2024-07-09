package com.example.tripmingle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tripmingle.entity.CountryImage;

@Repository
public interface CountryImageRepository extends JpaRepository<CountryImage, Long> {
}
