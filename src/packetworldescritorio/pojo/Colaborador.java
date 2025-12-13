/** @authores  Pipe, Kevin, champ */

package packetworldescritorio.pojo;

import java.util.Objects;

public class Colaborador {
    
    private int idColaborador;
    private String noPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String password;
    private String curp;
    private int idSucursal;
    private String nombreSucursal;
    private int idRol;
    private String rol;
    private byte[] foto;
    private String fotoBase64;
    private String numLicencia;

    public Colaborador() {
    }

    public Colaborador(int idColaborador, String noPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String password, String curp, int idSucursal, String nombreSucursal, int idRol, String rol, byte[] foto, String fotoBase64, String numLicencia) {
        this.idColaborador = idColaborador;
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.password = password;
        this.curp = curp;
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.idRol = idRol;
        this.rol = rol;
        this.foto = foto;
        this.fotoBase64 = fotoBase64;
        this.numLicencia = numLicencia;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public String getNumLicencia() {
        return numLicencia;
    }

    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }

    @Override
    public String toString() {
        return "Colaborador{" + "idColaborador=" + idColaborador + ", noPersonal=" + noPersonal + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", correo=" + correo + ", password=" + password + ", curp=" + curp + ", idSucursal=" + idSucursal + ", nombreSucursal=" + nombreSucursal + ", idRol=" + idRol + ", rol=" + rol + ", foto=" + foto + ", fotoBase64=" + fotoBase64 + ", numLicencia=" + numLicencia + '}';
    }

public boolean equalsForm(Colaborador otro) {
    if (otro == null) {
        return false;
    }

    return Objects.equals(this.nombre, otro.nombre) &&
           Objects.equals(this.apellidoPaterno, otro.apellidoPaterno) &&
           Objects.equals(this.apellidoMaterno, otro.apellidoMaterno) &&
           Objects.equals(this.curp, otro.curp) &&
           Objects.equals(this.correo, otro.correo) &&
           this.idSucursal == otro.idSucursal &&
           Objects.equals(this.numLicencia, otro.numLicencia);
}
      
}
