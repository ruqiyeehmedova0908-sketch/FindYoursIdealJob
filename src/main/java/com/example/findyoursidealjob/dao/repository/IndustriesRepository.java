package com.example.findyoursidealjob.dao.repository;

import com.example.findyoursidealjob.dao.entity.Categories;
import com.example.findyoursidealjob.dao.entity.Companies;
import com.example.findyoursidealjob.dao.entity.Industries;
import com.example.findyoursidealjob.dao.entity.Vacancies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndustriesRepository extends JpaRepository<Industries,Long>, JpaSpecificationExecutor<Industries> {
    Optional<Industries> findByName(String name);

}
