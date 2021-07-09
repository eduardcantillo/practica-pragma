package com.pragma.laboratorio.customer.foto.rest;

import com.pragma.laboratorio.customer.dto.FotoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "service-foto",path = "/foto",decode404 = true,fallback = FotoRestImple.class)

public interface FotoRest {

    @GetMapping()
    public ResponseEntity<List<FotoDto>> listAll();
    @PostMapping()
    public ResponseEntity<FotoDto> save(FotoDto foto);
    @GetMapping("/{id}")
    public ResponseEntity<FotoDto> findById(@PathVariable String id);
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String id);
    @PutMapping("/{id}")
    public ResponseEntity<FotoDto> update(@PathVariable String id,@RequestBody FotoDto foto);
    @PostMapping("/by-ids")
    public ResponseEntity<List<FotoDto>> findByIds(@RequestBody List<String> ids);
}
