package com.example.api.mappings;

/* */
import org.apache.ibatis.exceptions.PersistenceException;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
/* */
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.example.api.model.User;

@Repository
public interface DemoMapper {
    String USUARIO = " usuario ";
    String AUXILIAR_UUID = " auxiliar_uuid ";

    /** Constant <code>CAMPOS_USER=" id, correo, clave, creado, activo, acc"{trunked}</code> */
    String CAMPOS_USER = " id, correo, clave, rol, creado, activo, acceso_negado_contador, instante_bloqueo, " +
       "instante_ultimo_acceso, instante_ultimo_cambio_clave, regenera_clave_token, regenera_clave_instante, nick, uuid ";

    /**
     * Obtiene una lista de objetos de tipo 'usuario'.
     *
     * @return Lista de obetos de tipo usuario
     * @throws java.sql.PersistenceException Se dispara en caso de que ocurra un error en esta operación desde la base de datos.
     * @param id a int.
     */
    @Results(id="UsuarioMap", value = {
        @Result(property = "id",     column = "id"),
        @Result(property = "correo", column = "correo"),
        @Result(property = "clave",  column = "clave"),
        @Result(property = "rol",    column = "rol"),
        @Result(property = "creado", column = "creado"),
        @Result(property = "activo", column = "activo"),
        @Result(property = "accesoNegadoContador",      column = "acceso_negado_contador"),
        @Result(property = "instanteBloqueo",           column = "instante_bloqueo"),
        @Result(property = "instanteUltimoAcceso",      column = "instante_ultimo_acceso"),
        @Result(property = "instanteUltimoCambioClave", column = "instante_ultimo_cambio_clave"),
        @Result(property = "regeneraClaveToken",        column = "regenera_clave_token"),
        @Result(property = "nick",                      column = "nick"),
        @Result(property = "regeneraClaveInstante",     column = "regenera_clave_instante"),
        @Result(property = "uuid",  column = "uuid")
        })
    @Select("SELECT " + CAMPOS_USER + " FROM "+USUARIO+" WHERE correo = #{correo} AND clave= #{clave}")
    User login(String correo, String clave) throws PersistenceException;

    @Insert("INSERT INTO solicitud_detalle (id_solicitud, id_meta_cat_att, valor) VALUES (#{idSolicitud}, #{idMetaCatAtt}, #{valor})")
    Integer insertIntoSolicitudDetalle(int idSolicitud, int idMetaCatAtt, String valor);

    @Select("select count(*) from solicitud_detalle where id_solicitud = #{idSolicitud} and id_meta_cat_att = #{idMetaCatAtt}")       
    Integer buscaEnLaTablaSolicitudDetalle(int idSolicitud, int idMetaCatAtt);

    @Update("update solicitud_detalle set valor = #{valor} where id_solicitud = #{idSolicitud} and id_meta_cat_att = #{idMetaCatAtt}")
    Integer updateIntoSolicitudDetalle(int idSolicitud, int idMetaCatAtt, String valor);

    @Select("select id from meta_cat_val where id_meta_cat_att = #{idAtt}") 
    List<Integer> valoresDelCatalogo(int idAtt);

    @Select("select min_value from meta_cat_range where id_meta_cat_att=#{att} ")
    Double minValue(int att);

    @Select("select max_value from meta_cat_range where id_meta_cat_att=#{att} ")
    Double maxValue(int att);

    @Select("select count(*) from meta_cat_range where id_meta_cat_att=#{att} ")
    Integer hasRange(int value);
}
