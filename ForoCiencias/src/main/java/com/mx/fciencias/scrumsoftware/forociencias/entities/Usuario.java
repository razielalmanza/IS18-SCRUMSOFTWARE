package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "forociencias", schema = "usuarios", name="usuario")
public class Usuario implements Serializable {
    
    @Id
    @Column(name="id_usuario")
    private Long idUsuario;
    
    @Column(name="nombre_usuario")
    private String nombreUsuario;
    
    @Column(name="correo")
    private String correo;
    
    @Column(name="contrasenia")
    private String contrasenia;
    
    @Column(name="sexo")
    private Character sexo;
    
    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
}
