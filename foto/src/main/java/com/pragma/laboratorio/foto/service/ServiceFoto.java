package com.pragma.laboratorio.foto.service;

import com.pragma.laboratorio.foto.dto.FotoDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceFoto {

    public ResponseEntity<FotoDto> findById(String id);
    public boolean deletedById(String id);
    public ResponseEntity<List<FotoDto>> listAll();
    public ResponseEntity<FotoDto> save(FotoDto foto);
    public ResponseEntity<FotoDto> update(FotoDto fotoDto,String id);
    public ResponseEntity<List<FotoDto>> findByIdIn(List<String> ids);


}
