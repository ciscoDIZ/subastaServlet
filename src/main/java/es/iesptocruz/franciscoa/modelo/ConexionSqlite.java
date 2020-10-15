package es.iesptocruz.franciscoa.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSqlite {
    //tablas
    public static final String TOPERACIONES = "operaciones";
    //registros tabla resultados
    public static final String TOPERACIONES_ID_OPERACION = "id_operacion";
    public static final String TOPERACIONES_OPERANDO_A = "operando_a";
    public static final String TOPERACIONES_OPERANDO_B = "operando_b";
    public static final String TOPERACIONES_RESULTADO = "resultado";
    public static final String TOPERACIONES_OPERADOR = "operador";
    //parámetros conexión
    public String jdbcUrl;

    //atributos de clase;
    private Connection conn;

    private static ConexionSqlite conexion = null;

    private ConexionSqlite(String db) throws SQLException {
        jdbcUrl = "jdbc:sqlite:" + db;
        cargarDriverSqlite();
        sqlite();
    }

    public static ConexionSqlite getConexion() throws Exception {
        if (conexion == null) {
            throw new Exception("no hay ninguna conexión creada");
        } else {
            return conexion;
        }
    }

    public static ConexionSqlite setNewConexion(String ddbb) throws SQLException {
        if (conexion != null) {
            if (conexion.getConn() != null && !conexion.getConn().isClosed()) {
                conexion.getConn().close();
            }

        }
        conexion = new ConexionSqlite(ddbb);
        return conexion;
    }

    public static void cargarDriverSqlite() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("no carga el driver");
            System.exit(1);
        }
    }

    private Connection sqlite() throws SQLException {
        return mysql(jdbcUrl);
    }

    private Connection mysql(String url) throws SQLException {
        setConn(null);
        if (url != null && !url.isEmpty()) {
            jdbcUrl = url;
        }
        Connection con = DriverManager.getConnection(jdbcUrl);
        setConn(con);
        return getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
