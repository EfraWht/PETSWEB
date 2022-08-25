
package petsweb.entidadesdenegocio;

import java.time.LocalDate;


public class Comentario {
     
    private int id;
    private int idPublicacion;
    private int idUsuario;
    private String comentario;
    private Publicacion publicacion;
    private LocalDate fecha;
    private int Top_Aux;
    private Usuario usuario; 

    public Comentario() {
    }

    public Comentario(int id, int idPublicacion, int idUsuario, String comentario, Publicacion publicacion, LocalDate fecha, int Top_Aux, Usuario usuario) {
        this.id = id;
        this.idPublicacion = idPublicacion;
        this.idUsuario = idUsuario;
        this.comentario = comentario;
        this.publicacion = publicacion;
        this.fecha = fecha;
        this.Top_Aux = Top_Aux;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getTop_Aux() {
        return Top_Aux;
    }

    public void setTop_Aux(int Top_Aux) {
        this.Top_Aux = Top_Aux;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
