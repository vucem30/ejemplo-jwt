package net.ultrasist.api.model.meta;

public enum EnumMetaCatTipoDato {
    CATALOGO(1),
    ENTERO(2),
    DECIMAL(3),
    BOLEANO(4),
    ENTERO_LARGO(5),
    CADENA(6),
    URL(7),
    MULTI_CATALOGO(8),
    CATALOGO_EDITABLE(9);

    private final int value;

    EnumMetaCatTipoDato(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
