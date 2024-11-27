package com.example.api.service;

import org.springframework.stereotype.Service;
import com.example.api.mappings.DemoMapper;
import com.example.api.model.User;
import com.example.api.model.exceptions.ServiceException;
import com.example.api.utils.JwtFunctios;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {
    private DemoMapper dm;

    public DemoServiceImpl(DemoMapper dm) {
        this.dm = dm;
    }

    public String login(String user, String password) throws ServiceException {
        User u = dm.login(user,password);
        if(u==null) {
            throw new ServiceException("Usuario o contraseña incorrectos");
        }
        return new JwtFunctios().createJwtFromString(u);
    }

    @Override
    public int insertIntoSolicitudDetalle(int idSolicitud, int idMetaCatAtt, String valor) {
        return dm.insertIntoSolicitudDetalle(idSolicitud, idMetaCatAtt, valor);
    }
}
