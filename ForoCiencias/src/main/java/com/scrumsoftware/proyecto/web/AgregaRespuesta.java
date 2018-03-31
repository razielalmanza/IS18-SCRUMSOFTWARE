package com.scrumsoftware.proyecto.web;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ximenalezama
 */
public class AgregaRespuesta {

    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }

    public List<String> guardaRespuesta(String query) {
        List<String> respuesta = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            respuesta.add(query + i);
        }
        return respuesta;
    }

}
