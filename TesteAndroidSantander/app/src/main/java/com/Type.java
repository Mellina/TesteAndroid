package com;

public enum Type {

    FIELD(1),
    TEXT(2),
    IMAGE(3),
    CHECKBOX(4),
    BUTTON(5);

    private int valor;

    Type(int valor) {
        this.valor = valor;
    }

    public static Type returnaEnumPorValor(int i) {
        switch (i) {
            case 1:
                return FIELD;
            case 2:
                return TEXT;
            case 3:
                return IMAGE;
            case 4:
                return CHECKBOX;
            case 5:
                return BUTTON;
            default:
                break;
        }
        return null;
    }

}
