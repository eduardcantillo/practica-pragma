package com.pragma.laboratorio.foto.dao;

import com.pragma.laboratorio.foto.entity.Foto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFotoDao extends MongoRepository<Foto,String> {
    public List<Foto> findBy_idIn(List<String> ids);
}
