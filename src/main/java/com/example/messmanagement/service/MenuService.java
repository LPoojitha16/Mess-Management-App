package com.example.messmanagement.service;

import com.example.messmanagement.model.Menu;
import com.example.messmanagement.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu updateMenu(Long id, Menu updatedMenu) {
        return menuRepository.findById(id)
                .map(menu -> {
                    menu.setItemName(updatedMenu.getItemName());
                    menu.setPrice(updatedMenu.getPrice());
                    return menuRepository.save(menu);
                })
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}