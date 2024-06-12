package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Refresh;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRepository extends CrudRepository<Refresh, String> {
    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
