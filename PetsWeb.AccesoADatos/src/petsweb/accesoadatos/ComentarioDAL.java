package petsweb.accesoadatos;

import java.util.*;
import java.sql.*;
import petsweb.entidadesdenegocio.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar las entidades de Rol y Usuario
import java.time.LocalDate;
import static petsweb.accesoadatos.UsuarioDAL.asignarDatosResultSet;

public class ComentarioDAL {
    
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Usuario
    static String obtenerCampos() {
        return "p.Id, p.IdUsuario, idPublicacion, p.Fecha, p.comentario";
    }
     private static String obtenerSelect(Comentario pComentario) {
        String sql;
        sql = "SELECT ";
        if (pComentario.getTop_Aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pComentario.getTop_Aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Publicacion p");
        return sql;
    }   
     private static String agregarOrderBy(Comentario pComentario) {
        String sql = " ORDER BY u.Id DESC";
        if (pComentario.getTop_Aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pComentario.getTop_Aux() + " ";
        }
        return sql;
    }
     
     // Metodo para poder insertar un nuevo registro en la tabla de Usuario
    public static int crear(Comentario pComentario) throws Exception {
        int result;
        String sql;
        
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Publicacion(IdUsuario,idPublicacion,fecha,comentario) VALUES(?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setInt(1, pComentario.getIdUsuario()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setInt(2, pComentario.getIdPublicacion()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(3, pComentario.getComentario());// agregar el parametro a la consulta donde estan el simbolo "?" #4 
                    //ps.setByte(4, pPublicacion.getImagen()); // agregar el parametro a la consulta donde estan el simbolo "?" #6 
                    ps.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // agregar el parametro a la consulta donde estan el simbolo "?" #7 
                    result = ps.executeUpdate(); // ejecutar la consulta INSERT en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close();
            } // Handle any errors that may have occurred.
            catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda
            }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }
    public static int modificar(Comentario pComentario) throws Exception {
        int result;
        String sql;
          try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Publicacion(IdUsuario,idPublicacion,fecha,comentario) VALUES(?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setInt(1, pComentario.getIdUsuario()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setInt(2, pComentario.getIdPublicacion()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(3, pComentario.getComentario());// agregar el parametro a la consulta donde estan el simbolo "?" #4 
                    //ps.setByte(4, pPublicacion.getImagen()); // agregar el parametro a la consulta donde estan el simbolo "?" #6 
                    ps.setDate(5, java.sql.Date.valueOf(LocalDate.now())); // agregar el parametro a la consulta donde estan el simbolo "?" #7 
                    result = ps.executeUpdate(); // ejecutar la consulta INSERT en la base de datos
                    ps.close(); // cerrar el PreparedStatement
                } catch (SQLException ex) {
                    throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda 
                }
                conn.close();
            } // Handle any errors that may have occurred.
            catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al obtener la conexion en el caso que suceda
            }
        return result; // Retornar el numero de fila afectadas en el INSERT en la base de datos 
    }
    
    // Metodo para poder eliminar un registro en la tabla de Usuario
    public static int eliminar(Usuario pUsuario) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Usuario WHERE Id=?"; //definir la consulta DELETE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pUsuario.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                result = ps.executeUpdate(); // ejecutar la consulta DELETE en la base de datos
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex;  // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda 
        }
        return result; // Retornar el numero de fila afectadas en el DELETE en la base de datos 
    }
    
    // el metodo nos ayudara a no preocuparlos por los indices al momento de obtener los valores del ResultSet
    static int asignarDatosResultSet(Comentario pComentario, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM Usuario
        pIndex++;
        pComentario.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pComentario.setIdUsuario(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pComentario.setIdPublicacion(pResultSet.getInt(pIndex)); // index 3
        pIndex++;
        pComentario.setFecha(pResultSet.getDate(pIndex).toLocalDate()); // index 7
        pIndex++;
        pComentario.setComentario(pResultSet.getString(pIndex)); // index 5
        pIndex++;
        return pIndex;
    }
    
     // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Usuario
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Comentario> pComentario) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario
                Comentario comentario = new Comentario();
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(comentario, resultSet, 0);
                pComentario.add(comentario); // agregar la entidad Usuario al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    // Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Usuario y JOIN a la tabla de Rol
    private static void obtenerDatosIncluirP_U(PreparedStatement pPS, ArrayList<Comentario> pComentario) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            
            HashMap<Integer, Publicacion> publicacionMap = new HashMap();
            HashMap<Integer, Usuario> usuarioMap = new HashMap();
            
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario JOIN a la tabla de Rol
                Comentario comentario = new Comentario();
                 // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                int index = asignarDatosResultSet(comentario, resultSet, 0);
                
                if (publicacionMap.containsKey(comentario.getIdPublicacion()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Publicacion publicacion = new Publicacion();
                    // en el caso que el Rol no este en el HashMap se asignara
                    PublicacionDAL.asignarDatosResultSet(publicacion, resultSet, index);
                    publicacionMap.put(publicacion.getId(), publicacion); // agregar el Rol al  HashMap
                    comentario.setPublicacion(publicacion); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    comentario.setPublicacion(publicacionMap.get(comentario.getIdPublicacion())); 
                }
                
                if (usuarioMap.containsKey(comentario.getIdUsuario()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Usuario usuario = new Usuario();
                    // en el caso que el Rol no este en el HashMap se asignara
                    UsuarioDAL.asignarDatosResultSet(usuario, resultSet, index);
                    usuarioMap.put(usuario.getId(), usuario); // agregar el Rol al  HashMap
                    comentario.setUsuario(usuario); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    comentario.setUsuario(usuarioMap.get(comentario.getIdUsuario())); 
                }
                
                pComentario.add(comentario); // Agregar el Usuario de la fila actual al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
     // Metodo para obtener por Id un registro de la tabla de Usuario 
    public static Comentario obtenerPorId(Comentario pComentario) throws Exception {
        Comentario comentario = new Comentario();
        ArrayList<Comentario> comentarios = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pComentario); // obtener la consulta SELECT de la tabla Usuario
             // Concatenar a la consulta SELECT de la tabla Usuario el WHERE  para comparar el campo Id
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pComentario.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, comentarios); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (comentarios.size() > 0) { // verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            comentario = comentarios.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero
        }
        return comentario; // devolver el Usuario encontrado por Id 
    }

     // Metodo para obtener todos los registro de la tabla de Usuario
    public static ArrayList<Comentario> obtenerTodos() throws Exception {
        ArrayList<Comentario> comentarios;
        comentarios = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Comentario()); // obtener la consulta SELECT de la tabla Usuario
            sql += agregarOrderBy(new Comentario()); // concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, comentarios); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return comentarios; // devolver el ArrayList de Usuario
    }

    
    
}
