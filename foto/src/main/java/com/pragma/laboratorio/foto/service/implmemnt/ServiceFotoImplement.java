package com.pragma.laboratorio.foto.service.implmemnt;

import com.pragma.laboratorio.foto.dao.IFotoDao;
import com.pragma.laboratorio.foto.dto.FotoDto;
import com.pragma.laboratorio.foto.entity.Foto;
import com.pragma.laboratorio.foto.service.ServiceFoto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class ServiceFotoImplement implements ServiceFoto {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IFotoDao fotoDao;

    @Override
    public ResponseEntity<FotoDto> findById(String id) {
            Foto foto=this.fotoDao.findById(id).orElse(null);
            if(foto==null){
                return ResponseEntity.noContent().build();
            }
        return ResponseEntity.ok(modelMapper.map(foto,FotoDto.class));
    }

    @Override
    public boolean deletedById(String id) {
        try{
            this.fotoDao.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public ResponseEntity<List<FotoDto>> listAll() {
        List<Foto> fotos= this.fotoDao.findAll();
        List<FotoDto> fotoDtos=new ArrayList<>();
        for (Foto f:fotos) {
            fotoDtos.add(modelMapper.map(f,FotoDto.class));
        }

        return ResponseEntity.ok(fotoDtos);
    }

    @Override
    public ResponseEntity<FotoDto> save(FotoDto fotoDto) {
        Foto foto=modelMapper.map(fotoDto,Foto.class);
        try{
            foto=this.fotoDao.save(foto);
            fotoDto=modelMapper.map(foto,FotoDto.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();

        }
        return ResponseEntity.ok(fotoDto);
    }

    @Override
    public ResponseEntity<FotoDto> update(FotoDto fotoDto,String id) {
        Foto foto=this.fotoDao.findById(id).orElse(null);

        if(foto==null){
            return ResponseEntity.notFound().build();
        }
        try {
            foto.setContent(fotoDto.getContent());
            this.fotoDao.save(foto);
            fotoDto=modelMapper.map(foto,FotoDto.class);
            return ResponseEntity.ok(fotoDto);

        }catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }



    }
}
