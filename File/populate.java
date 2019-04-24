import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class populate {
	private Connection con;
	
	private void run(String[] file) {
        try {
            startConnection();
            deleteTables();
            for (int i = 0; i < file.length; i++) {
            	System.out.println("Checking file "+file[i]);
            	if(file[i].endsWith("Movies.dat")){
            		populateMovie(file[i]);
            	}
            	else if(file[i].endsWith("movie_genres.dat")){
            		populateMovieGenre(file[i]);
            	}
            	else if(file[i].endsWith("movie_countries.dat")){
            		populateMovieCountry(file[i]);
            	}
            	else if(file[i].endsWith("movie_tags.dat")){
            		populateMovieTag(file[i]);
            	}
            	else if(file[i].endsWith("tags.dat")){
            		populateTag(file[i]);
            	}

            	else if(file[i].endsWith("movie_actors.dat")){
            		populateActors(file[i]);
            	}
            	else if(file[i].endsWith("movie_directors.dat")){
            		populateDirectors(file[i]);
            	}
            	else if(file[i].endsWith("user_taggedmovies.dat")){
            		populateUserTaggedMovies(file[i]);
            	}     
            }
        } catch (Exception e) {
            System.err.println("Error occurs when populating data: " + e.getMessage());
        } finally {
            endConnection();
        }
    }

	private void deleteTables() throws SQLException {
		Statement stmt = con.createStatement();
		
		String query = "delete from Movie_tags";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Movie_genres";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Movie_countries";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Actors";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Directors";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Usertagmovies";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Tags";
		System.out.println(query);
		stmt.executeUpdate(query);
		
		query = "delete from Movies";
		System.out.println(query);
		stmt.executeUpdate(query);
		
	}

	private void populateUserTaggedMovies(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int uid = Integer.parseInt(lineData[0]);
            int mid = Integer.parseInt(lineData[1]);
            int tid = Integer.parseInt(lineData[2]);      

            String	query = "insert into Usertagmovies values (" + uid +  ", " + mid + ", " + tid + ")";
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       				
	}

	private void populateDirectors(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int mid = Integer.parseInt(lineData[0]);
            String directorid = lineData[1];
            String directorname = lineData[2]; 
            if(directorname.contains("'")){
            	directorname = directorname.replace("'", "''");
            	//System.out.println(actorname);
            }

            String	query = "insert into Directors values (" + mid +  ", '" + directorid + "', '" + directorname + "')";
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       	
	}

	private void populateActors(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int mid = Integer.parseInt(lineData[0]);
            String actorid = lineData[1];
            String actorname = lineData[2];  
            if(actorname.contains("'")){
            	actorname = actorname.replace("'", "''");
            	//System.out.println(actorname);
            }

            String	query = "insert into Actors values (" + mid +  ", '" + actorid + "', '" + actorname + "')";
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       		
	}

	private void populateMovieTag(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            //System.out.println(lineData.length);
            int mid = Integer.parseInt(lineData[0]);
            int tid = Integer.parseInt(lineData[1]);
            int tagWeight = Integer.parseInt(lineData[2]);
            String query = "";
            
            if (lineData[2].length() == 0) {
            	query = "insert into Movie_tags values (" + mid +  ", '" + tid + ", NULL)" ;
            } 
            else {
            	query = "insert into Movie_tags values (" + mid +  ", '" + tid + "', '" + tagWeight + "')" ;
            }
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       	
	}

	private void populateTag(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int id = Integer.parseInt(lineData[0]);
            String tag = lineData[1];
            
            String query = "";
            if (tag.length() == 0) {
            	query = "insert into Tags values (" + id +  ", NULL)";
            } 
            else {
            	query = "insert into Tags values (" + id +  ", '" + tag + "')";
            }
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       	
	}

	private void populateMovieCountry(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int mid = Integer.parseInt(lineData[0]);
            String country = "";
            if (lineData.length > 1) {
            	country = lineData[1];
            }
            
            String query = "";
            if (country.length() == 0) {
            	query = "insert into Movie_countries values (" + mid +  ", NULL)";
            } 
            else {
            	query = "insert into Movie_countries values (" + mid +  ",'" + country + "')";
            }
            
            System.out.println(query);
            stmt.executeUpdate(query); 
            
        }
       
        stmt.close();   
        br.close();       	
	}

	private void populateMovieGenre(String filename) throws IOException, SQLException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int mid = Integer.parseInt(lineData[0]);
            String genres = "";
            if (lineData.length > 1) {
            	genres = lineData[1];
            }
            
            String query = "";
            if (genres.length() == 0) {
            	query = "insert into Movie_genres values (" + mid +  ", NULL)";
            } 
            else {
            	query = "insert into Movie_genres values (" + mid +  ", '" + genres + "')";
            }
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       		
	}

	private void populateMovie(String filename) throws IOException, SQLException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";    
        br.readLine();
    
        Statement stmt = con.createStatement();
    
        while ((line = br.readLine()) != null) {
            String[] lineData = line.split("\\t");
            int mid = Integer.parseInt(lineData[0]);
            String title = lineData[1];
            if (title.contains("'")) {
            	title = title.replace("'", "''");
            }
            int year = Integer.parseInt(lineData[5]);
           	double rating = 0.0;
			int rtNo = 0;
			String query = "insert into Movies values (" + mid +  ",'" + title + "'," + year + ",";
			if (!lineData[19].equals("\\N")){
				rating = Double.parseDouble(lineData[17]);
			}
			if (!lineData[18].equals("\\N")){
				rtNo = Integer.parseInt(lineData[18]);
			}
			if (lineData[19].equals("\\N")||lineData[18].equals("\\N")){
				query += "NULL,NULL)";
			}
			else {
				query += rating + "," + rtNo + ")";
			}

            
            
            System.out.println(query);
            stmt.executeUpdate(query); 
        }
       
        stmt.close();   
        br.close();       		
	}

	private void startConnection() throws SQLException, ClassNotFoundException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			} catch (ClassNotFoundException cnfe) {
			System.out.println("can not find class: " + cnfe);
			} catch (InstantiationException e) {
			System.out.println("can not instantiate class " + e);	
			} catch (IllegalAccessException e) {
			System.out.println(("illegal access " + e));
			}
        //DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String host = "localhost";
        String port = "1521";
        String dbName = "orcl";
        String username = "system";
        String password = "123";
        
        String oracleURL = "jdbc:oracle:thin:@" + host +":" + port + ":" + dbName; 
                    
        con=DriverManager.getConnection(oracleURL,username,password);
        System.out.println("Connection started!");
    }
	
	private void endConnection() {
		try {
			con.close();
			System.out.println("Connection ended!");
		} catch (SQLException e) {
			System.err.println("Cannot close connection: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		populate p = new populate();
		String[] s = new String[8];
		s[0] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/Movies.dat";
		s[1] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/movie_genres.dat";
		s[2] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/movie_actors.dat";
		s[3] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/movie_directors.dat";
		s[4] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/movie_countries.dat";
		s[5] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/tags.dat";
		s[6] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/movie_tags.dat";
		s[7] = "D:/STUDY/Courses/Database System/hetrec2011-movielens-2k-v2/user_taggedmovies.dat";
 		if(args.length==0){
			p.run(s);
		}
 		else{p.run(args);};
	}

}
