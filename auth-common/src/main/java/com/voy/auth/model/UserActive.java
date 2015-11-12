package com.voy.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Bozo on 05/11/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserActive {

    public Boolean isActive;

    public UserActive(){}
    public UserActive(Boolean b){this.isActive = b;}
}
