package com.xib.assessment.controller;

import com.xib.assessment.model.Manager;
import com.xib.assessment.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController("/manager")
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createManager(@RequestBody Manager manager) {
        try {
            manager = managerRepository.save(manager);
            return ResponseEntity.ok(manager);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getManager/{id}")
    public ResponseEntity<?> getManager(@PathVariable("id") Long id) {
        Manager manager = null;

        try {
            manager = managerRepository.findById(id).orElse(null);

            if (Objects.isNull(manager)) return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(manager);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getManagers() {

        List<Manager> managers;

        try {
            managers = managerRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(managers);
    }
}
