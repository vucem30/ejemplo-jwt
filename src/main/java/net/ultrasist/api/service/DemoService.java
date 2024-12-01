package net.ultrasist.api.service;

import java.util.Map;

import net.ultrasist.api.model.Tramite;
import net.ultrasist.api.model.exceptions.ServiceException;
import net.ultrasist.api.model.meta.EnumMetaCatAtt;

public interface DemoService {

    /**
     * Autentica a un usuario con el nombre de usuario y contraseña proporcionados.
     *
     * @param user el nombre de usuario del usuario que intenta iniciar sesión.
     * @param password la contraseña del usuario que intenta iniciar sesión.
     * 
     * @throws ServiceException si ocurre un error durante la autenticación.
     * @return un token de autenticación si el inicio de sesión es exitoso.
     */
    String login(String user, String password) throws ServiceException;

    /**
     * Inserta o actualiza un atributo en la tabla 'solicitud_detalle'.
     * 
     * Si el atributo no existe, se inserta; si ya existe, se actualiza. Retorna 
     * el número de filas afectadas (1 en caso de éxito). Si ocurre un error o 
     * el valor no es permitido para el atributo, se lanza una excepción.
     *
     * @param idSolicitud Integer que representa el ID de la solicitud.
     * @param att EnumMetaCatAtt que representa el atributo a persistir.
     * @param valor String que representa el valor del atributo. Debe cumplir con 
     *              las reglas de validación asociadas al atributo.
     * 
     * @throws ServiceException si ocurre un error durante la operación o el 
     *                          valor no es válido para el atributo.
     * @return Integer que representa el número de filas afectadas (1 en caso de éxito).
     */    
    int persistAttributeIntoSolicitudDetalle(int idSolicitud, EnumMetaCatAtt idMetaCatAtt, String valor) throws ServiceException;

    /**
     * Elimina un atributo de la tabla 'solicitud_detalle'.
     * 
     * Retorna el número de filas afectadas (1 en caso de éxito). Si ocurre un error, 
     * se lanza una excepción.
     *
     * @param idSolicitud Integer que representa el ID de la solicitud.
     * @param att EnumMetaCatAtt que representa el atributo a eliminar.
     * 
     * @throws ServiceException si ocurre un error durante la operación.
     * @return Integer que representa el número de filas afectadas (1 en caso de éxito).
     */
    int deleteSolicitudDetalle(int idSolicitud, EnumMetaCatAtt idMetaCatAtt) throws ServiceException;

    /**
     * Obtiene un atributo de la tabla 'solicitud_detalle'.
     * 
     * Retorna un mapa con el valor del atributo solicitado. Si ocurre un error, 
     * se lanza una excepción.
     *
     * @param idSolicitud Integer que representa el ID de la solicitud.
     * @param att EnumMetaCatAtt que representa el atributo a obtener.
     * 
     * @throws ServiceException si ocurre un error durante la operación.
     * @return Map<String, String> que representa el valor del atributo solicitado.
     */
    Map<String, String> getTramiteAllAtt(int idSolicitud);

    /**
     * Obtiene todos los valores un trámite de la base de datos de la tabla padre.
     * 
     * Retorna un objeto de tipo Tramite con la información del trámite solicitado. 
     *
     * @param id Integer que representa el ID del trámite a obtener.
     * 
     * @return Tramite que representa el trámite solicitado.
     */
    Tramite getTramite(int id); 
}
