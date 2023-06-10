package ar.edu.itba.paw.webapp.utils;

public class DefaultBoolean {
    private boolean value;

    public DefaultBoolean(){
        this.value = false;
    }

    public DefaultBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value){
        this.value = value;
    }
}
