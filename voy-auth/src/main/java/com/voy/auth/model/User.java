package com.voy.auth.model;

import javax.persistence.*;

/**
 * Created by Bozo on 29/10/2015.
 */

@Entity(name = "person")
public class User {


    @Id
    @Column(name = "id_person")
    private Long idPerson;
    private String name;
    private String login;
    /**Categoria do Usuario*/
    @Enumerated(EnumType.STRING)
    private TypeUser type;
    private Boolean inactive;

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    private String passwordWeb;

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public TypeUser getType() {
        return type;
    }

    public void setType(TypeUser type) {
        this.type = type;
    }

    public String getPasswordWeb() {
        return passwordWeb;
    }



    public void setPasswordWeb(String passwordWeb) {
        this.passwordWeb = passwordWeb;
    }

    public enum TypeUser {
        ALO, PRODUTOR, PRODUTOR_SHOW, PROMOTER,//SGA SGV
        GESTOR, PDV, TOTEM, VALIDADOR_ONLINE, //SGA
        VALIDADOR, CAIXA, TECNICO, ADMIN, CAIXA_ESTACIONAMENTO, CAIXA_LISTA_VIP; //SGV

        public static TypeUser getByType(final String value) {

            if("ALO".equals(value)) {
                return ALO;
            } else if("PRODUTOR".equals(value)) {
                return PRODUTOR;
            } else if("PRODUTOR_SHOW".equals(value)) {
                return PRODUTOR_SHOW;
            } else if("GESTOR".equals(value)) {
                return GESTOR;
            } else if("PDV".equals(value)) {
                return PDV;
            } else if("VALIDADOR".equals(value)) {
                return VALIDADOR;
            } else if("CAIXA".equals(value)) {
                return CAIXA;
            } else if("TECNICO".equals(value)) {
                return TECNICO;
            } else if("ADMIN".equals(value)) {
                return ADMIN;
            } else if("PROMOTER".equals(value)) {
                return PROMOTER;
            } else if("CAIXA_ESTACIONAMENTO".equals(value)) {
                return CAIXA_ESTACIONAMENTO;
            } else if("CAIXA_LISTA_VIP".equals(value)) {
                return CAIXA_LISTA_VIP;
            } else if("TOTEM".equals(value)) {
                return TOTEM;
            } else if("VALIDADOR_ONLINE".equals(value)) {
                return VALIDADOR_ONLINE;
            }

            return null;
        }
    }
}
