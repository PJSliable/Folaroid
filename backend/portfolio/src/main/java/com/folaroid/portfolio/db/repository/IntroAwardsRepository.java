package com.folaroid.portfolio.db.repository;


import com.folaroid.portfolio.db.entity.IntroAwards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroAwardsRepository extends JpaRepository<IntroAwards, Long> {

}