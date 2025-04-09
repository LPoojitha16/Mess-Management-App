package com.example.messmanagement.service;

import com.example.messmanagement.dto.PermissionDTO;
import com.example.messmanagement.model.Permission;
import com.example.messmanagement.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    // Create a new permission
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission(
                permissionDTO.getRegNo(),
                permissionDTO.getReason(),
                "pending" // Initial status
        );
        Permission savedPermission = permissionRepository.save(permission);
        return new PermissionDTO(
                savedPermission.getId(),
                savedPermission.getRegNo(),
                savedPermission.getReason(),
                savedPermission.getStatus()
        );
    }

    // Get all permissions
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permission -> new PermissionDTO(
                        permission.getId(),
                        permission.getRegNo(),
                        permission.getReason(),
                        permission.getStatus()
                ))
                .collect(Collectors.toList());
    }

    // Get a permission by ID
    public PermissionDTO getPermissionById(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            return new PermissionDTO(
                    permission.getId(),
                    permission.getRegNo(),
                    permission.getReason(),
                    permission.getStatus()
            );
        }
        throw new RuntimeException("Permission not found with ID: " + id);
    }

    // Approve a permission
    public PermissionDTO approvePermission(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setStatus("approved");
            Permission updatedPermission = permissionRepository.save(permission);
            return new PermissionDTO(
                    updatedPermission.getId(),
                    updatedPermission.getRegNo(),
                    updatedPermission.getReason(),
                    updatedPermission.getStatus()
            );
        }
        throw new RuntimeException("Permission not found with ID: " + id);
    }

    // Disapprove a permission
    public PermissionDTO disapprovePermission(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            permission.setStatus("disapproved");
            Permission updatedPermission = permissionRepository.save(permission);
            return new PermissionDTO(
                    updatedPermission.getId(),
                    updatedPermission.getRegNo(),
                    updatedPermission.getReason(),
                    updatedPermission.getStatus()
            );
        }
        throw new RuntimeException("Permission not found with ID: " + id);
    }
}