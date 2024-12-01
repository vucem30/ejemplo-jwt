package net.ultrasist.api.model.meta;

public enum EnumMetaCatAtt {
    OJOS(1),
    PIEL(2),
    PELO(3),
    ESTUDIOS(4),
    COLOR(5),
    ESTATURA(6),
    PESO(7),
    EXTRANJERO(8),
    APROBADO(9),
    PRECIO(10),
    NACIMIENTO(11),
    SLOGAN(12),
    FRASE_FAVORITA(13),
    SITIO_WEB(14),
    FOTO_PRINCIPAL(15),
    FOTO_FONDO(16),
    COLORES_PREFERIDOS(17);

    private final int value;

    EnumMetaCatAtt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
