package com.example.megacoffee.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminId(String adminId);
}
