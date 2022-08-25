
package petsweb.entidadesdenegocio;

import java.time.LocalDate;

public class Mensajeprivado {
    
    private int id;
    private int idUsuarioR;
    private int idUsuarioD;
    private String mensaje;
    private LocalDate fecha;
    private Usuario usuarior;
    private Usuario usuariod;
    private int Top_Aux;

    public Mensajeprivado() {
    }

    public Mensajeprivado(int id, int idUsuarioR, int idUsuarioD, String mensaje, LocalDate fecha, Usuario usuarior, Usuario usuariod, int Top_Aux) {
        this.id = id;
        this.idUsuarioR = idUsuarioR;
        this.idUsuarioD = idUsuarioD;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuarior = usuarior;
        this.usuariod = usuariod;
        this.Top_Aux = Top_Aux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuarioR() {
        return idUsuarioR;
    }

    public void setIdUsuarioR(int idUsuarioR) {
        this.idUsuarioR = idUsuarioR;
    }

    public int getIdUsuarioD() {
        return idUsuarioD;
    }

    public void setIdUsuarioD(int idUsuarioD) {
        this.idUsuarioD = idUsuarioD;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuarior() {
        return usuarior;
    }

    public void setUsuarior(Usuario usuarior) {
        this.usuarior = usuarior;
    }

    public Usuario getUsuariod() {
        return usuariod;
    }

    public void setUsuariod(Usuario usuariod) {
        this.usuariod = usuariod;
    }

    public int getTop_Aux() {
        return Top_Aux;
    }

    public void setTop_Aux(int Top_Aux) {
        this.Top_Aux = Top_Aux;
    }
    
    

}