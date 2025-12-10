package com.example.findyoursidealjob.dao.repository;

import com.example.findyoursidealjob.dao.entity.VacanciesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacanciesDetailRepository extends JpaRepository<VacanciesDetail,Long> {
    
    @Query("SELECT vd FROM VacanciesDetail vd WHERE vd.vacancies.companies.id = :companyId")
    List<VacanciesDetail> findByCompanyId(@Param("companyId") Long companyId);
    
    @Query("SELECT vd FROM VacanciesDetail vd WHERE vd.vacancies.categories.id = :categoryId")
    List<VacanciesDetail> findByCategoryId(@Param("categoryId") Long categoryId);
    
    @Query("SELECT vd FROM VacanciesDetail vd WHERE vd.vacancies.industries.id = :industryId")
    List<VacanciesDetail> findByIndustryId(@Param("industryId") Long industryId);
}
