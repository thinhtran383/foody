package com.example.foodordering.controllers;

import com.example.foodordering.entities.Table;
import com.example.foodordering.services.TableService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.v1.prefix}/tables")
@RequiredArgsConstructor
@Tag(name = "TableController", description = "Operations pertaining to tables in Food Ordering System")
public class TableController {
    private final TableService tableService;

    @PostMapping("/add")
    public ResponseEntity<?> addTables(@RequestParam @Valid int numberOfTables) {
        tableService.addNewTable(numberOfTables);
        return ResponseEntity.ok().body(numberOfTables + " tables added successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTableStatus(@RequestParam @Valid int tableId) {
        tableService.updateTableStatus(tableId);
        return ResponseEntity.ok().body("Table status updated successfully");
    }

    @ApiResponse(content = @Content(schema = @Schema(implementation = Table.class)))
    @GetMapping("/all")
    public ResponseEntity<?> getAllTables() {
        return ResponseEntity.ok().body(tableService.getAllTables());
    }
}