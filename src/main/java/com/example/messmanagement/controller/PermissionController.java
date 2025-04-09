package com.example.messmanagement.controller;

import com.example.messmanagement.dto.PermissionDTO;
import com.example.messmanagement.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // Create a new permission (accessible to students)
    @PostMapping("/create")
    @PreAuthorize("hasRole('student')")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        PermissionDTO createdPermission = permissionService.createPermission(permissionDTO);
        return ResponseEntity.ok(createdPermission);
    }

    // Get all permissions (accessible to admins)
    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        List<PermissionDTO> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    // Get a permission by ID (accessible to students)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('student')")
    public ResponseEntity<PermissionDTO> getPermissionById(@PathVariable Long id) {
        PermissionDTO permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    // Approve a permission (accessible to admins)
    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<PermissionDTO> approvePermission(@PathVariable Long id) {
        PermissionDTO updatedPermission = permissionService.approvePermission(id);
        return ResponseEntity.ok(updatedPermission);
    }

    // Disapprove a permission (accessible to admins)
    @PatchMapping("/{id}/disapprove")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<PermissionDTO> disapprovePermission(@PathVariable Long id) {
        PermissionDTO updatedPermission = permissionService.disapprovePermission(id);
        return ResponseEntity.ok(updatedPermission);
    }
}