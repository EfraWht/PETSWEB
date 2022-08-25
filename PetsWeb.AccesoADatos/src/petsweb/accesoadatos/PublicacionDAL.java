
package petsweb.accesoadatos;

import java.util.*;
import java.sql.*;
import petsweb.entidadesdenegocio.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar las entidades de Rol y Usuario
import java.time.LocalDate;

public class PublicacionDAL {
    
    // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Usuario
    static String obtenerCampos() {
        return "p.Id, p.IdUsuario, p.Fecha, p.Titulo, p.Desacripcion, p.Imagen,";
    }
     private static String obtenerSelect(Publicacion pPublicacion) {
        String sql;
        sql = "SELECT ";
        if (pPublicacion.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pPublicacion.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Publicacion p");
        return sql;
    }   
     private static String agregarOrderBy(Publicacion pPublicacion) {
        String sql = " ORDER BY u.Id DESC";
        if (pPublicacion.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pPublicacion.getTop_aux() + " ";
        }
        return sql;
    }
     
     // Metodo para poder insertar un nuevo registro en la tabla de Usuario
    public static int crear(Publicacion pPublicacion) throws Exception {
        int result;
        String sql;
        
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Publicacion(IdUsuario,fecha,Titulo,Descripcion,Imagen) VALUES(?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setInt(1, pPublicacion.getIdUsuario()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setString(2, pPublicacion.getTitulo()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(3, pPublicacion.getDescripcion()); // agregar el parametro a la consulta donde estan el simbolo "?" #4 
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
    //*************************************************************************************************************************************************************************************
      public static int modificar(Publicacion pPublicacion) throws Exception {
        int result;
        String sql;
          try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Publicacion(IdUsuario,fecha,Titulo,Descripcion,Imagen) VALUES(?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setInt(1, pPublicacion.getIdUsuario()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setString(2, pPublicacion.getTitulo()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(3, pPublicacion.getDescripcion());// agregar el parametro a la consulta donde estan el simbolo "?" #4 
                    ps.setString(4, pPublicacion.getImagen()); // agregar el parametro a la consulta donde estan el simbolo "?" #6 
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
    public static int eliminar(Publicacion pPublicacion) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            sql = "DELETE FROM Usuario WHERE Id=?"; //definir la consulta DELETE a la tabla de Usuario utilizando el simbolo ? para enviar parametros
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {  // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPublicacion.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
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
    static int asignarDatosResultSet(Publicacion pPublicacion, ResultSet pResultSet, int pIndex) throws Exception {
        //  SELECT u.Id(indice 1), u.IdRol(indice 2), u.Nombre(indice 3), u.Apellido(indice 4), u.Login(indice 5), 
        // u.Estatus(indice 6), u.FechaRegistro(indice 7) * FROM Usuario
        pIndex++;
        pPublicacion.setId(pResultSet.getInt(pIndex)); // index 1
        pIndex++;
        pPublicacion.setIdUsuario(pResultSet.getInt(pIndex)); // index 2
        pIndex++;
        pPublicacion.setTitulo(pResultSet.getString(pIndex)); // index 3
        pIndex++;
        pPublicacion.setFecha(pResultSet.getDate(pIndex).toLocalDate()); // index 7
        pIndex++;
        pPublicacion.setDescripcion(pResultSet.getString(pIndex)); // index 5
        pIndex++;
        pPublicacion.setImagen(pResultSet.getString(pIndex)); // index 5
        pIndex++;
        return pIndex;
    }
    
    //Metodo para  ejecutar el ResultSet de la consulta SELECT a la tabla de Usuario
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Publicacion> pPublicacion) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario
                Publicacion publicacion = new Publicacion() ;
                // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                asignarDatosResultSet(publicacion, resultSet, 0);
                pPublicacion.add(publicacion); // agregar la entidad Usuario al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex;// enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
    
      private static void obtenerDatosIncluirUsuario(PreparedStatement pPS, ArrayList<Publicacion> pPublicacion) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { // obtener el ResultSet desde la clase ComunDB
            HashMap<Integer, Usuario> usuarioMap = new HashMap(); //crear un HashMap para automatizar la creacion de instancias de la clase Rol
            while (resultSet.next()) { // Recorrer cada una de la fila que regresa la consulta  SELECT de la tabla Usuario JOIN a la tabla de Rol
                Publicacion publicacion = new Publicacion();
                 // Llenar las propiedaddes de la Entidad Usuario con los datos obtenidos de la fila en el ResultSet
                 
                 
                int index = asignarDatosResultSet(publicacion, resultSet, 0);
                
                if (usuarioMap.containsKey(publicacion.getIdUsuario()) == false) { // verificar que el HashMap aun no contenga el Rol actual
                    Usuario usuario = new Usuario();
                    // en el caso que el Rol no este en el HashMap se asignara
                    UsuarioDAL.asignarDatosResultSet(usuario, resultSet, index);
                    usuarioMap.put(usuario.getId(), usuario); // agregar el Rol al  HashMap
                    publicacion.setUsuario(usuario); // agregar el Rol al Usuario
                } else {
                    // En el caso que el Rol existe en el HashMap se agregara automaticamente al Usuario
                    publicacion.setUsuario(usuarioMap.get(publicacion.getIdUsuario())); 
                }
                pPublicacion.add(publicacion); // Agregar el Usuario de la fila actual al ArrayList de Usuario
            }
            resultSet.close(); // cerrar el ResultSet
        } catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener ResultSet de la clase ComunDB   en el caso que suceda 
        }
    }
      
    // Metodo para obtener por Id un registro de la tabla de Usuario 
    public static Publicacion obtenerPorId(Publicacion pPublicacion) throws Exception {
        Publicacion publicacion = new Publicacion();
        ArrayList<Publicacion> publicaciones = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pPublicacion); // obtener la consulta SELECT de la tabla Usuario
             // Concatenar a la consulta SELECT de la tabla Usuario el WHERE  para comparar el campo Id
            sql += " WHERE p.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                ps.setInt(1, pPublicacion.getId()); // agregar el parametro a la consulta donde estan el simbolo ? #1 
                obtenerDatos(ps, publicaciones); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        if (publicaciones.size() > 0) { // verificar si el ArrayList de Usuario trae mas de un registro en tal caso solo debe de traer uno
            publicacion = publicaciones.get(0); // Si el ArrayList de Usuario trae un registro o mas obtenemos solo el primero
        }
        return publicacion; // devolver el Usuario encontrado por Id 
    }

     // Metodo para obtener todos los registro de la tabla de Usuario
    public static ArrayList<Publicacion> obtenerTodos() throws Exception {
        ArrayList<Publicacion> publicaciones;
        publicaciones = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(new Publicacion()); // obtener la consulta SELECT de la tabla Usuario
            sql += agregarOrderBy(new Publicacion()); // concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                obtenerDatos(ps, publicaciones); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // cerrar la conexion a la base de datos
        }
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return publicaciones; // devolver el ArrayList de Usuario
    }

    static void querySelect(Publicacion pPublicacion, ComunDB.UtilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement(); // obtener el PreparedStatement al cual aplicar los parametros
        if (pPublicacion.getId() > 0) { // verificar si se va incluir el campo Id en el filtro de la consulta SELECT de la tabla de Usuario
            pUtilQuery.AgregarNumWhere(" p.Id=? "); // agregar el campo Id al filtro de la consulta SELECT y agregar el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Id a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pPublicacion.getId());
            }
        }
        // verificar si se va incluir el campo IdRol en el filtro de la consulta SELECT de la tabla de Usuario
        if (pPublicacion.getIdUsuario() > 0) {
            pUtilQuery.AgregarNumWhere(" p.IdUsuario=? "); // agregar el campo IdRol al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo IdRol a la consulta SELECT de la tabla de Usuario
                statement.setInt(pUtilQuery.getNumWhere(), pPublicacion.getIdUsuario());
            }
        }
        // verificar si se va incluir el campo Nombre en el filtro de la consulta SELECT de la tabla de Usuario
        if (pPublicacion.getTitulo() != null && pPublicacion.getTitulo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" p.Titulo LIKE ? "); // agregar el campo Nombre al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Nombre a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPublicacion.getTitulo() + "%");
            }
        }
        // Verificar si se va incluir el campo Apellido en el filtro de la consulta SELECT de la tabla de Usuario
        if (pPublicacion.getDescripcion() != null && pPublicacion.getDescripcion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" p.Descripcion LIKE ? "); // agregar el campo Apellido al filtro de la consulta SELECT y agregar en el WHERE o AND
            if (statement != null) {
                 // agregar el parametro del campo Apellido a la consulta SELECT de la tabla de Usuario
                statement.setString(pUtilQuery.getNumWhere(), "%" + pPublicacion.getDescripcion() + "%");
            }
        }
      
    }
    
     // Metodo para obtener todos los registro de la tabla de Usuario que cumplan con los filtros agregados 
     // a la consulta SELECT de la tabla de Usuario 
    public static ArrayList<Publicacion> buscar(Publicacion pPublicacion) throws Exception {
        ArrayList<Publicacion> publicaciones = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = obtenerSelect(pPublicacion); // obtener la consulta SELECT de la tabla Usuario
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pPublicacion, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPublicacion); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPublicacion, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatos(ps, publicaciones); // Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex; // Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } 
        catch (SQLException ex) {
            throw ex; // Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return publicaciones; // Devolver el ArrayList de Usuario
    }
   public static ArrayList<Publicacion> buscarIncluirUsuario(Publicacion pPublicacion) throws Exception {
        ArrayList<Publicacion> publicaciones = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
            String sql = "SELECT "; // Iniciar la variables para el String de la consulta SELECT
            if (pPublicacion.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pPublicacion.getTop_aux() + " "; // Agregar el TOP en el caso que se este utilizando SQL SERVER
            }
            sql += obtenerCampos(); // Obtener los campos de la tabla de Usuario que iran en el SELECT
            sql += ",";
            sql += RolDAL.obtenerCampos(); // Obtener los campos de la tabla de Rol que iran en el SELECT
            sql += " FROM Publicacion p";
            sql += " JOIN Usuario u on (p.IdUsuario=u.Id)"; // agregar el join para unir la tabla de Usuario con Rol
            ComunDB comundb = new ComunDB();
            ComunDB.UtilQuery utilQuery = comundb.new UtilQuery(sql, null, 0);
            querySelect(pPublicacion, utilQuery); // Asignar el filtro a la consulta SELECT de la tabla de Usuario 
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pPublicacion); // Concatenar a la consulta SELECT de la tabla Usuario el ORDER BY por Id
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pPublicacion, utilQuery); // Asignar los parametros al PreparedStatement de la consulta SELECT de la tabla de Usuario
                obtenerDatosIncluirUsuario(ps, publicaciones);// Llenar el ArrayList de Usuario con las fila que devolvera la consulta SELECT a la tabla de Usuario
                ps.close(); // Cerrar el PreparedStatement
            } catch (SQLException ex) {
                throw ex;// Enviar al siguiente metodo el error al ejecutar PreparedStatement en el caso que suceda
            }
            conn.close(); // Cerrar la conexion a la base de datos
        } catch (SQLException ex) {
            throw ex;// Enviar al siguiente metodo el error al obtener la conexion  de la clase ComunDB en el caso que suceda
        }
        return publicaciones; // Devolver el ArrayList de Usuario
    }
}
