package com.example.api.service;

import com.example.api.model.exceptions.ServiceException;
import com.example.api.model.meta.EnumMetaCatAtt;

public interface DemoService {
    String login(String user, String password) throws ServiceException;
    int persistAttributeIntoSolicitudDetalle(int idSolicitud, EnumMetaCatAtt idMetaCatAtt, String valor) throws ServiceException;
}
