package com.example.megacoffee.service;

import com.example.megacoffee.model.Admin;
import com.example.megacoffee.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminByAdminId(String adminId) {
        return adminRepository.findByAdminId(adminId);
    }

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
