package com.example.HackNITR.Repository;

import com.example.HackNITR.Entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

}
