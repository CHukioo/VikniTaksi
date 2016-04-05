package com.example.marko.viknitaksi;

/**
 * Created by Marko on 4/4/2016.
 */
public class Info {

    private String id;
    private String grad;
    private String ime;
    private String broj;

    public Info(){

    }
    public Info(String id, String grad,String ime,String broj){
        this.id=id;
        this.grad=grad;
        this.ime=ime;
        this.broj=broj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }
}
