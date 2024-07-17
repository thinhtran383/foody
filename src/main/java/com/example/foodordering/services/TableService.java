package com.example.foodordering.services;

import com.example.foodordering.entities.Table;
import com.example.foodordering.repositories.TableRepository;
import jakarta.persistence.Temporal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TableService {
    private final TableRepository tableRepository;

    @Transactional
    public void addNewTable(int numberTableAdd) {
        List<Table> tables = IntStream.range(0, numberTableAdd)
                .mapToObj(i -> {
                    new Table();
                    return Table.builder()
                            .status("FREE")
                            .build();
                })
                .toList();
        tableRepository.saveAll(tables);
    }

    @Transactional
    public void updateTableStatus(int tableId, boolean isFree) {
        Table table = tableRepository.findById(tableId).orElseThrow();
        table.setStatus(isFree ? "FREE" : "OCCUPIED");
        tableRepository.save(table);
    }

    @Transactional(readOnly = true)
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @Transactional
    public Optional<Table> getTableById(int tableId) {
        return tableRepository.findById(tableId);
    }
}
