package com.entities;

public class Componente {

    private int id;
    private int type;
    private String message;
    private String typefield;
    private boolean hidden;
    private double topSpacing;
    private String show;
    private boolean required;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypefield() {
        return typefield;
    }

    public void setTypefield(String typefield) {
        this.typefield = typefield;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public double getTopSpacing() {
        return topSpacing;
    }

    public void setTopSpacing(double topSpacing) {
        this.topSpacing = topSpacing;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
