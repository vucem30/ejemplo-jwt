package com.example.api.service;

import com.example.api.model.exceptions.ServiceException;

public interface DemoService {
    String login(String user, String password) throws ServiceException;
    int insertIntoSolicitudDetalle(int idSolicitud, int idMetaCatAtt, String valor);
}
