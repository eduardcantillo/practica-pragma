package com.pragma.laboratorio.foto.dao;

import com.pragma.laboratorio.foto.entity.Foto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFotoDao extends MongoRepository<Foto,String> {
}
