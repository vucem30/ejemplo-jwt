package net.ultrasist.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import net.ultrasist.api.mappings.DemoMapper;
import net.ultrasist.api.model.ParAttValor;
import net.ultrasist.api.model.Tramite;
import net.ultrasist.api.model.User;
import net.ultrasist.api.model.exceptions.ServiceException;
import net.ultrasist.api.model.meta.EnumMetaCatAtt;
import net.ultrasist.api.utils.JwtFunctios;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Vucem30ServiceImpl implements Vucem30Service {
    private DemoMapper dm;

    public Vucem30ServiceImpl(DemoMapper dm) {
        this.dm = dm;
    }

    public String login(String user, String password) throws ServiceException {
        User u = dm.login(user,password);
        if(u==null) {
            throw new ServiceException("Usuario o contraseña incorrectos");
        }
        return JwtFunctios.getInstance().createJwtFromString(u);
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
    
    /** {@inheritDoc} */
    @Override
    public int persistAttributeIntoSolicitudDetalle(
            int idSolicitud, 
            EnumMetaCatAtt att, 
            String valor) throws ServiceException {
        int attribute = att.getValue();


        // primero valida si el atributo le pertenece al tramite
        // luego valida si el trámite le pertenece a la organización


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

    @Override
    public int deleteSolicitudDetalle(int idSolicitud, EnumMetaCatAtt idMetaCatAtt) throws ServiceException {
        try {
            return this.dm.deleteSolicitudDetalle(idSolicitud, idMetaCatAtt.getValue());
        } catch (Exception e) {
            throw new ServiceException("error al eliminar el atributo de la solicitud");
        }
    }

    @Override
    public Map<String, String> getTramiteAllAtt(int idSolicitud) {
        Map<String, String> result = new HashMap<>();
        try {
            Tramite t = this.getTramite(idSolicitud);
            result.put("id", t.getId()+"");
            result.put("descripcion", t.getDescripcion());
            result.put("campo001", t.getCampo001());
            result.put("campo002", t.getCampo002());
            result.put("campo003", t.getCampo003());
            result.put("campo004", t.getCampo004());
            result.put("etc", t.getEtc());
            
            List<ParAttValor> par = this.dm.getTramiteDetalle(idSolicitud);
            for(ParAttValor p: par) {
                result.put(p.getNombre(), p.getValor());
            }

            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            return result;
        }
    }

    @Override
    public Tramite getTramite(int id) {
        return this.dm.getTramite(id);
    }

}
