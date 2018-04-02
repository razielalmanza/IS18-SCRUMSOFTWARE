package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase que representa la entidad usuarios.usuario.
 * @author Marco Olea
 */
@Entity
@Table(catalog = "forociencias", schema = "usuarios", name="usuario")
public class Usuario implements Serializable {
    
    /* El identificador único del usuario. */
    @Id
    @Column(name="id_usuario")
    private Long idUsuario;
    
    /* El nombre del usuario. */
    @Column(name="nombre_usuario")
    private String nombreUsuario;
    
    /* El correo del usuario. */
    @Column(name="correo")
    private String correo;
    
    /* La contraseña del usuario. */
    @Column(name="contrasenia")
    private String contrasenia;
    
    /* El sexo del usuario. */
    @Column(name="sexo")
    private Character sexo;
    
    /* La fecha de nacimiento del usuario. */
    @Column(name="fecha_nacimiento")
    private Date fechaNacimiento;
    
    /**
     * Regresa el identificador de este usuario.
     * @return un Long que representa el identificador.
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * Fija un nuevo identificador para este usuario.
     * @param idUsuario el nuevo identificador.
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Regresa el nombre de este usuario.
     * @return un String que representa el nombre.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Fija un nuevo nombre para este usuario.
     * @param nombreUsuario el nuevo nombre.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Regresa el correo de este usuario.
     * @return un String que representa el correo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Fija un nuevo correo para este usuario.
     * @param correo el nuevo correo.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Regresa la contraseña de este usuario.
     * @return un String que representa la contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Fija una nueva contraseña para este usuario.
     * @param contrasenia la nueva contraseña.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * Regresa el sexo de este usuario.
     * @return un Character que representa el sexo.
     */
    public Character getSexo() {
        return sexo;
    }

    /**
     * Fija el sexo este usuario.
     * @param sexo el sexo.
     */
    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    /**
     * Regresa la fecha de nacimiento de este usuario.
     * @return un java.sql.Date que representa la fecha de nacimiento.
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Fija una nueva fecha de nacimiento para este usuario.
     * @param fechaNacimiento la nueva fecha de nacimiento.
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
}
