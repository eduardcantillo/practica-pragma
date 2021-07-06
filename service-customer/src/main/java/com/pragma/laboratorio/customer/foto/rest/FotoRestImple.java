package com.pragma.laboratorio.customer.foto.rest;

import com.pragma.laboratorio.customer.dto.FotoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class FotoRestImple implements FotoRest{
    @Override
    public ResponseEntity<List<FotoDto>> listAll() {
        return ResponseEntity.ok(Arrays.asList(new FotoDto("none",new byte[1])));
    }

    @Override
    public ResponseEntity<FotoDto> save(FotoDto foto) {
        return null;
    }

    @Override
    public ResponseEntity<FotoDto> findById(String id) {
        return ResponseEntity.ok(new FotoDto("none",new byte[1]));
    }

    @Override
    public ResponseEntity<Boolean> deleteById(String id) {
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<FotoDto> update(String id, FotoDto foto) {
        return ResponseEntity.ok(new FotoDto("none",new byte[1]));
    }
}
