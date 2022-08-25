package petsweb.accesoadatos;

import java.util.*;
import java.sql.*;
import petsweb.entidadesdenegocio.*; // Agregar la referencia al proyecto de entidades de negocio y poder utilizar las entidades de Rol y Usuario
import java.time.LocalDate;

public class MensajeprivadoDAL {
    
      // Metodo para obtener los campos a utilizar en la consulta SELECT de la tabla de Usuario
    static String obtenerCampos() {
        return "p.Id, p.IdUsuarioR, p.IdUsuarioD, p.Mensaje, p.Fecha";
    }
    private static String obtenerSelect(Mensajeprivado pMensajeprivado) {
        String sql;
        sql = "SELECT ";
        if (pMensajeprivado.getTop_Aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             // Agregar el TOP a la consulta SELECT si el gestor de base de datos es SQL SERVER y getTop_aux es mayor a cero
            sql += "TOP " + pMensajeprivado.getTop_Aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Mensajeprivado m");
        return sql;
    }
    
     private static String agregarOrderBy(Mensajeprivado pMensajeprivado) {
        String sql = " ORDER BY u.Id DESC";
        if (pMensajeprivado.getTop_Aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            // Agregar el LIMIT a la consulta SELECT de la tabla de Usuario en el caso que getTop_aux() sea mayor a cero y el gestor de base de datos
            // sea MYSQL
            sql += " LIMIT " + pMensajeprivado.getTop_Aux() + " ";
        }
        return sql;
    }
     
      public static int crear(Mensajeprivado pMensajeprivado) throws Exception {
        int result;
        String sql;
        
        try (Connection conn = ComunDB.obtenerConexion();) { // Obtener la conexion desde la clase ComunDB y encerrarla en try para cierre automatico
                 //Definir la consulta INSERT a la tabla de Usuario utilizando el simbolo "?" para enviar parametros
                sql = "INSERT INTO Mensajeprivado(IdUsuarioR,IdUsuarioD,Mensaje, fecha) VALUES(?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) { // Obtener el PreparedStatement desde la clase ComunDB
                    ps.setInt(1, pMensajeprivado.getIdUsuarioR()); // Agregar el parametro a la consulta donde estan el simbolo "?" #1  
                    ps.setInt(2, pMensajeprivado.getIdUsuarioD()); // agregar el parametro a la consulta donde estan el simbolo "?" #3 
                    ps.setString(3, pMensajeprivado.getMensaje());// agregar el parametro a la consulta donde estan el simbolo "?" #4 
                    ps.setDate(4, java.sql.Date.valueOf(LocalDate.now())); // agregar el parametro a la consulta donde estan el simbolo "?" #7 
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
      
}
