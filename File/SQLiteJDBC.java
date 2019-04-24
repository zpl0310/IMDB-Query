import java.sql.*;

public class SQLiteJDBC
{
    Connection c = null;
    Statement stmt = null;
    String sql = null;


  public SQLiteJDBC(String dbName) throws SQLException, ClassNotFoundException {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");

  }

  public void createTable() throws SQLException{
    stmt = c.createStatement();
      String sql = "CREATE TABLE COMPANY " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " NAME           TEXT    NOT NULL, " + 
                   " AGE            INT     NOT NULL, " + 
                   " ADDRESS        CHAR(50), " + 
                   " SALARY         REAL)"; 
      stmt.executeUpdate(sql);
      c.commit();
      System.out.println("created table");
  }
  public void insertData() throws SQLException{
    stmt = c.createStatement();
      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                   "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
      stmt.executeUpdate(sql);

      sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
            "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
      stmt.executeUpdate(sql);
      c.commit();
      System.out.println("Inserted Data successfully");
  }

  public void updateData() throws SQLException{
     stmt = c.createStatement();
      sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
      stmt.executeUpdate(sql);
      c.commit();
      System.out.println("Updated data");

  }
  public void close() throws SQLException{
    stmt.close();
      c.close();
  }

  public void readData() throws SQLException {

      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
      while ( rs.next() ) {
         int id = rs.getInt("id");
         String  name = rs.getString("name");
         int age  = rs.getInt("age");
         String  address = rs.getString("address");
         float salary = rs.getFloat("salary");
         System.out.println( "ID = " + id );
         System.out.println( "NAME = " + name );
         System.out.println( "AGE = " + age );
         System.out.println( "ADDRESS = " + address );
         System.out.println( "SALARY = " + salary );
         System.out.println();
      }
      rs.close();
  }
  public static void main( String args[] )
  {
    try {
      
      SQLiteJDBC sqlite = new SQLiteJDBC(args[0]);

      sqlite.createTable();
      sqlite.insertData();
      sqlite.readData();
      sqlite.updateData();
      sqlite.readData();

      sqlite.close();
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
  }
}
