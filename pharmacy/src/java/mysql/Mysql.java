package mysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
public class Mysql {
    public Connection connect;
    public PreparedStatement query;
    public ResultSet response;
    public ResultSetMetaData metadata;
    public Mysql(String server,String user,String password,String dataBase) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        this.connect=DriverManager.getConnection("jdbc:mysql://"+server+"/"+dataBase,user,password);
    }
    public void desconectar() throws SQLException{
        if(response!=null)
            response.close();
        query.close();
        connect.close();
    }
    public void consulta(String query) throws SQLException{
        this.query=connect.prepareStatement(query);
        this.response=this.query.executeQuery();
    }
    public void update(String update) throws SQLException{
        this.query=connect.prepareStatement(update);
        this.query.executeUpdate();
    }
}
