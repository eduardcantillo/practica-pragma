package com.pragma.laboratorio.foto.controllers;

import com.pragma.laboratorio.foto.dao.IFotoDao;
import com.pragma.laboratorio.foto.dto.FotoDto;
import com.pragma.laboratorio.foto.entity.Foto;
import com.pragma.laboratorio.foto.service.ServiceFoto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/foto")
public class FotoController {

    @Autowired
   private ModelMapper modelMapper;


    @Autowired
    private ServiceFoto serviceFoto;

    @PostMapping
    public ResponseEntity<FotoDto>save(@RequestBody FotoDto foto){

        return this.serviceFoto.save(foto);
    }

    @GetMapping
    public ResponseEntity<List<FotoDto>> listAll(){

        return this.serviceFoto.listAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable String id){
        return (this.serviceFoto.deletedById(id))? ResponseEntity.ok(true):ResponseEntity.ok(false);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoDto> findById(@PathVariable String id){
        return this.serviceFoto.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FotoDto> update(@PathVariable String id,@RequestBody FotoDto foto){
        return this.serviceFoto.update(foto,id);
    }
}
