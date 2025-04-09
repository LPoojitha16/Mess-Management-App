package com.example.messmanagement.controller;

import com.example.messmanagement.model.Menu;
import com.example.messmanagement.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/items")
    @PreAuthorize("hasRole('Role_student') or hasRole('Role_admin')")
    public ResponseEntity<List<Menu>> getAllMenuItems() {
        List<Menu> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('Role_admin')")
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        Menu createdMenu = menuService.createMenu(menu);
        return ResponseEntity.ok(createdMenu);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Role_admin')")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        Menu menu = menuService.updateMenu(id, updatedMenu);
        return ResponseEntity.ok(menu);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Role_admin')")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok().build();
    }
}