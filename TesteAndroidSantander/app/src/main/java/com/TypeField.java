package com;

public enum TypeField {

    TEXT(1),
    TELNUMBER("telnumber"),
    EMAIL(3);

    private int valor;
    private String valorString;

    TypeField(int valor) {
        this.valor = valor;
    }

    TypeField(String valorString) {
        this.valorString = valorString;
    }

    public static TypeField returnaEnumPorValor(String i) {
        switch (i) {
            case "1":
                return TEXT;
            case "telnumber":
                return TELNUMBER;
            case "3":
                return EMAIL;
            default:
                break;
        }
        return null;
    }

}
