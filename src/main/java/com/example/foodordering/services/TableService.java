package com.example.foodordering.services;

import com.example.foodordering.entities.Table;
import com.example.foodordering.repositories.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void updateTableStatus(int tableId) {
        Table table = tableRepository.findById(tableId).orElseThrow();
        if (table.getStatus().equals("FREE")) {
            table.setStatus("OCCUPIED");
        } else {
            table.setStatus("FREE");
        }
        tableRepository.save(table);
    }

    @Transactional
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }
}
