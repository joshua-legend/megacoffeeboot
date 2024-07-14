package com.example.megacoffee.service;

import com.example.megacoffee.model.Admin;

public interface AdminService {
    Admin getAdminByAdminId(String adminId);
    void saveAdmin(Admin admin);
}
