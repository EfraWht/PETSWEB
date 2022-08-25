
package petsweb.entidadesdenegocio;

import java.time.LocalDate;

public class Publicacion {
    
    private int Id;
    private LocalDate fecha;
    private int idUsuario;
    private String Titulo;
    private String Descripcion; 
    private String Imagen;
    private int top_aux;
    private Usuario usuario;

    public Publicacion() {
    }

    public Publicacion(int Id, LocalDate fecha, int idUsuario, String Titulo, String Descripcion, String Imagen, int top_aux, Usuario usuario) {
        this.Id = Id;
        this.fecha = fecha;
        this.idUsuario = idUsuario;
        this.Titulo = Titulo;
        this.Descripcion = Descripcion;
        this.Imagen = Imagen;
        this.top_aux = top_aux;
        this.usuario = usuario;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}