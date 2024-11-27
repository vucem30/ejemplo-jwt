package com.example.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.api.mappings.DemoMapper;
import com.example.api.model.User;
import com.example.api.model.exceptions.ServiceException;
import com.example.api.model.meta.EnumMetaCatAtt;
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

    private int convertToInt(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return -Integer.MAX_VALUE;
        }
    }
    private double convertToDouble(String s){
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return -Double.MAX_VALUE;
        }
    }
    private boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Este método se encarga de insertar o actualizar un atributo en la tabla 'solicitud_detalle'.
     * Si el atributo no existe, entonces se inserta.
     * Si el atributo ya existe, entonces se actualiza.
     * En ambos casos, se retorna el número de filas afectadas.
     * Si no se pudo insertar o actualizar, entonces se lanza una excepción.
     * Si el valor no es un valor permitido para el atributo, entonces se lanza una excepción.
     * Si el valor es un valor permitido para el atributo, entonces se inserta o actualiza.
     *
     * @param idSolicitud Integer que representa el ID de la solicitud
     * @param att EnumMetaCatAtt que representa el ID del atributo a persistir
     * @param valor String que representa el valor del atributo a persistir
     * 
     * @throws ServiceException se dispara en caso de que el atributo no se haya podido insertar o actualizar
     * @return Integer que representa el número de filas afectadas, que debería ser siempre 1 en caso de éxito.
     */
    @Override
    public int persistAttributeIntoSolicitudDetalle(
            int idSolicitud, 
            EnumMetaCatAtt att, 
            String valor) throws ServiceException {
        int attribute = att.getValue();
        // En la variable 'rangoCatalogo' (que proviene del Enum 'att') tengo el número de catálogo de la tabla 'meta_cat_att'. 
        // Por ejemplo, 'ojos', 'piel', 'pelo', 'estudios', 'color', 'estatura', 'peso', 'extranjero', 'aprobado', 'precio', 'nacimiento', 'slogan', 'frase_favorita', 'sitio_web', 'foto_principal', 'foto_fondo', 'colores_preferidos'
        // algunos de estos, son de tipo 1 que significa que son catálogos y por lo tanto, sus valores DEBEN estar en la tabla 'meta_cat_att' en el rango adecuado.
        // si esto NO ocurre, entonces se lanza una excepción.
        List<Integer> rangoCatalogo = this.dm.valoresDelCatalogo(attribute);
        if(!rangoCatalogo.isEmpty() && !rangoCatalogo.contains(convertToInt(valor))) {
            throw new ServiceException("El valor "+valor+" no es un valor permitido para el atributo "+att);
        }
        if(!this.validaRango(att, valor)){
            throw new ServiceException("El valor "+valor+" no está en el rango permitido para el atributo "+att);
        }
        int result = 
            this.dm.buscaEnLaTablaSolicitudDetalle(idSolicitud, attribute)<1?
            this.dm.insertIntoSolicitudDetalle(idSolicitud, attribute, valor):
            this.dm.updateIntoSolicitudDetalle(idSolicitud, attribute, valor);
        if(result<1) {
            throw new ServiceException("No se pudo insertar (o actualizar) el valor en la tabla solicitud_detalle");
        }
        if(result>1) {
            throw new ServiceException("Se insertó (o actualizó) más de una fila en la tabla solicitud_detalle");
        }
        return result;
    }

    // para la tabla de rangos:
    private boolean validaRango(EnumMetaCatAtt att, String valor) {
        // si NO hay un registro que determine el rengo de un atributo, entonces no hay restricciones.
        if(this.dm.hasRange(att.getValue())<1) {
            return true;
        }

        // si existe un registro, pero el valor no es un double, entonces no está en rango.
        Double converted = convertToDouble(valor);
        if(converted==-Double.MAX_VALUE) {
            return false;
        }

        // si existe el registro y el valor es un double, entonces informa si está en rango o no.
        Double min = this.dm.minValue(att.getValue());
        Double max = this.dm.maxValue(att.getValue());
        return converted>=min && converted<=max;
    }

}
