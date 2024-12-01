/*
 * Licencia:    Este  código y cualquier  derivado  de  el, es  propiedad de la
 *              empresa Metasoft SA de CV y no debe, bajo ninguna circunstancia
 *              ser copiado, donado,  cedido, modificado, prestado, rentado y/o
 *              mostrado  a ninguna persona o institución sin el permiso expli-
 *              cito  y  por  escrito de  la empresa Metasoft SA de CV, que es,
 *              bajo cualquier criterio, el único dueño de la totalidad de este
 *              código y cualquier derivado de el.
 *              ---------------------------------------------------------------
 * Autor:       Gustavo Adolfo Arellano (GAA)
 * Correo:      gustavo.arellano@metasoft.com.mx
 * Versión:     0.0.1-SNAPSHOT
 *
 * Creación:    29 de sep 2022 @ 22:53
 */
package net.ultrasist.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String correo;
    private String clave;
    private long creado;
    private boolean activo;
    private int accesoNegadoContador;
    private long instanteBloqueo;
    private long instanteUltimoAcceso;
    private long instanteUltimoCambioClave;
    private String regeneraClaveToken;
    private long regeneraClaveInstante;
    private String nick;
    private String rol;
    private String uuid;

    public User() {
    }

    public User(int id, String correo, String clave, String nick, String uuid) {
        this(id, correo, clave, System.currentTimeMillis(), true, 0, 0, 0, 0, "NA", 0, nick, "REGULAR", uuid);
    }
    
    public User(int id, String correo, String clave, String uuid) {
        this(id, correo, clave, "usuario", uuid);
    }

    public User(
            int id,
            String correo,
            String clave,
            long creado,
            boolean activo,
            int accesoNegadoContador,
            long instanteBloqueo,
            long instanteUltimoAcceso,
            long instanteUltimoCambioClave,
            String regeneraClaveToken,
            long regeneraClaveInstante,
            String nick, 
            String rol, 
            String uuid) {
        this.id = id;
        this.correo = correo;
        this.clave = clave;
        this.creado = creado;
        this.activo = activo;
        this.accesoNegadoContador = accesoNegadoContador;
        this.instanteBloqueo = instanteBloqueo;
        this.instanteUltimoAcceso = instanteUltimoAcceso;
        this.instanteUltimoCambioClave = instanteUltimoCambioClave;
        this.regeneraClaveToken = regeneraClaveToken;
        this.regeneraClaveInstante = regeneraClaveInstante;
        this.nick = nick;
        this.rol = rol;
        this.uuid = uuid;
    }

    /**
     * Método especial (y adicional) de soporte al proceso de pruebas de regresión.
     *
     * @return a long.
     */
    public long getHash() {
        return this.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "User ["+
                "  id=" + id + 
                ", correo=" + correo + 
                ", clave=" + clave + 
                ", creado=" + creado + 
                ", activo=" + activo + 
                ", accesoNegadoContador=" + accesoNegadoContador + 
                ", instanteBloqueo=" + instanteBloqueo + 
                ", instanteUltimoAcceso=" + instanteUltimoAcceso + 
                ", instanteUltimoCambioClave=" + instanteUltimoCambioClave + 
                ", regeneraClaveToken=" + regeneraClaveToken + 
                ", regeneraClaveInstante=" + regeneraClaveInstante + 
                ", nick=" + nick + 
                ", rol=" + rol + 
                ", uuid=" + uuid + 
                "]";
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + accesoNegadoContador;
        result = prime * result + (activo ? 1231 : 1237);
        result = prime * result + ((clave == null) ? 0 : clave.hashCode());
        result = prime * result + ((nick == null) ? 0 : nick.hashCode());
        result = prime * result + ((rol == null) ? 0 : rol.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((correo == null) ? 0 : correo.hashCode());
        result = prime * result + (int) (creado ^ (creado >>> 32));
        result = prime * result + id;
        result = prime * result + (int) (instanteBloqueo ^ (instanteBloqueo >>> 32));
        result = prime * result + (int) (instanteUltimoAcceso ^ (instanteUltimoAcceso >>> 32));
        result = prime * result + (int) (instanteUltimoCambioClave ^ (instanteUltimoCambioClave >>> 32));
        result = prime * result + (int) (regeneraClaveInstante ^ (regeneraClaveInstante >>> 32));
        result = prime * result + ((regeneraClaveToken == null) ? 0 : regeneraClaveToken.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User usuario = (User) o;
        return id == usuario.id && 
               creado == usuario.creado && 
               activo == usuario.activo &&
               accesoNegadoContador == usuario.accesoNegadoContador && 
               instanteBloqueo == usuario.instanteBloqueo &&
               instanteUltimoAcceso == usuario.instanteUltimoAcceso &&
               instanteUltimoCambioClave == usuario.instanteUltimoCambioClave &&
               regeneraClaveInstante == usuario.regeneraClaveInstante && 
               Objects.equals(correo, usuario.correo) &&
               Objects.equals(uuid, usuario.uuid) &&
               Objects.equals(nick, usuario.nick) &&
               Objects.equals(rol, usuario.rol) &&
               Objects.equals(clave, usuario.clave) && 
               Objects.equals(regeneraClaveToken, usuario.regeneraClaveToken);
    }

}

