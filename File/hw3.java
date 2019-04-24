import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class hw3 extends JFrame{
	private Connection con;
	
	private JPanel genreP;
	private JPanel countryP;
	private JPanel castP;
	private JPanel tagP;
	private JPanel serchP;
	private JPanel queryP;
	private JPanel tagContainer;
	private JPanel genreContainer;
	private JPanel directorP;
	private JPanel movieP;
	private JPanel bottom;
	private JPanel userresultP;
	
	private JScrollPane genreSp;
	private JScrollPane countrySp;
	private JScrollPane castSp;
	private JScrollPane tagSp;
	private JScrollPane movieresultSp;
	private JScrollPane userresultSp;
	private JScrollPane querySp;
	
	private JButton moviequeryBt;
	private JButton userqueryBt;
	
	private JSpinner spinner1;
	private JSpinner spinner2;
	
	private JLabel actorL;
	private JLabel directorL;
	
	private JTextArea queryTa;
	private JTextArea userresultTa;
	private JTextField valueTf;
	
	private JComboBox<String> actorCB1;
	private JComboBox<String> actorCB2;
	private JComboBox<String> actorCB3;
	private JComboBox<String> actorCB4;
	private JComboBox<String> directorCB;
	private JComboBox<String> compareCb;
	
	private JComboBox<String> andorCb;
	
	private Color titlec = new Color(0, 102, 204);
	
	private ArrayList<String> chooseGenres = new ArrayList<String>();
	private ArrayList<String> chooseCountries = new ArrayList<String>();
	private ArrayList<Integer> chooseTags = new ArrayList<Integer>();
	private ArrayList<String> chooseActors = new ArrayList<String>();
	private ArrayList<String> chooseDirectors = new ArrayList<String>();
	private ArrayList<Integer> chooseMovies = new ArrayList<Integer>();
	
	private String showQuery;
	
	public hw3(){
		
		
		genreP = new JPanel();
		genreSp = new JScrollPane(genreP);		
		countryP = new JPanel();
		countrySp = new JScrollPane(countryP);
		castP = new JPanel();
		tagP = new JPanel();

		JPanel yearP = new JPanel();
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("to");
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, 2000);
        Date date = calendar.getTime();
        SpinnerDateModel model1 = new SpinnerDateModel();
        model1.setValue(date);
        SpinnerDateModel model2 = new SpinnerDateModel();
        model2.setValue(date);
        spinner1 = new JSpinner(model1);
        spinner2 = new JSpinner(model2);
		spinner1.setEditor(new JSpinner.DateEditor(spinner1, "yyyy"));
		spinner2.setEditor(new JSpinner.DateEditor(spinner2, "yyyy"));
		yearP.setLayout(new GridLayout(0,2));
		yearP.add(from);
		yearP.add(spinner1);
		yearP.add(to);
		yearP.add(spinner2);
		TitledBorder gtitle;		
		gtitle = BorderFactory.createTitledBorder("Genres");
		gtitle.setTitleJustification(TitledBorder.CENTER);
		genreP.setBorder(gtitle);
		TitledBorder ytitle;		
		ytitle = BorderFactory.createTitledBorder("Years");
		ytitle.setTitleJustification(TitledBorder.CENTER);
		yearP.setBorder(ytitle);
		genreContainer = new JPanel();
		genreContainer.setLayout(new BorderLayout());
		genreSp.setPreferredSize(new Dimension(160,270));
		genreContainer.add(genreSp, BorderLayout.CENTER);
		genreContainer.add(yearP, BorderLayout.SOUTH);
		genreContainer.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
				
		
		TitledBorder ctitle;		
		ctitle = BorderFactory.createTitledBorder("Countries");
		ctitle.setTitleJustification(TitledBorder.CENTER);
		countryP.setBorder(ctitle);
		countrySp = new JScrollPane(countryP);
		
		castP = new JPanel();
		castSp = new JScrollPane(castP);
		castP.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		castP.setLayout(new GridLayout(7,0));
		actorL = new JLabel("Actor/Actress");
		directorL = new JLabel("Director");
		TitledBorder catitle;		
		catitle = BorderFactory.createTitledBorder("Cast");
		catitle.setTitleJustification(TitledBorder.CENTER);
		castP.setBorder(catitle);
		String[] load = {"Choose genre and country first"};
		actorCB1 = new JComboBox<String>(load);
		actorCB2 = new JComboBox<String>(load);
		actorCB3 = new JComboBox<String>(load);
		actorCB4 = new JComboBox<String>(load);
		directorCB = new JComboBox<String>(load);
		directorP = new JPanel();
		castP.add(actorL);
		castP.add(actorCB1);
		castP.add(actorCB2);
		castP.add(actorCB3);
		castP.add(actorCB4);
		directorP.add(directorL);
		directorP.add(directorCB);
		directorP.setLayout(new GridLayout(0,1));
		castP.add(directorP);
		
		tagP = new JPanel();
		tagSp = new JScrollPane(tagP);
		//tagSp.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		TitledBorder ttitle;		
		ttitle = BorderFactory.createTitledBorder("Tag Ids and Values");
		ttitle.setTitleJustification(TitledBorder.CENTER);
		tagSp.setBorder(ttitle);
		JPanel tagS = new JPanel();		
		tagS.setLayout(new GridLayout(0,2));
		JLabel weightL = new JLabel("Tag Weight: ");
		JLabel valueL = new JLabel("Value: ");
		String[] compare = { "", "=", "<", ">", "<=", ">=" };
		compareCb = new JComboBox<String>(compare);
		compareCb.setSelectedIndex(0);
		valueTf = new JTextField();
		tagS.add(weightL);
		tagS.add(compareCb);
		tagS.add(valueL);
		tagS.add(valueTf);
		tagContainer = new JPanel();
		tagContainer.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		tagContainer.setLayout(new BorderLayout());
		tagContainer.add(tagSp, BorderLayout.CENTER);
		tagContainer.add(tagS, BorderLayout.SOUTH);
		
		movieP = new JPanel();
		movieresultSp = new JScrollPane(movieP);
		
	
		serchP = new JPanel();
		JLabel serchL = new JLabel("Serch Between Attributs Values: ");
		String[] andor = { "AND", "OR" };
		andorCb = new JComboBox<String>(andor);
		andorCb.setSelectedIndex(0);
		serchP.add(serchL);
		serchP.add(andorCb);

		queryP = new JPanel();
		queryP.setLayout(new BorderLayout());
		queryTa = new JTextArea();
		querySp = new JScrollPane(queryTa);
		moviequeryBt = new JButton("Execute Movie Query");
		moviequeryBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					runmoviesQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		});
		userqueryBt = new JButton("Execute User Query");
		userqueryBt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					runuserQuery();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}			
		});
		bottom = new JPanel();
		bottom.add(moviequeryBt);
		bottom.add(userqueryBt);
		bottom.setBackground(new Color(230, 255, 255));
		JLabel queryL = new JLabel("Movie Query");
		queryL.setFont(new Font("Courier New", Font.BOLD, 20));
		queryL.setForeground(titlec);
		queryP.add(queryL, BorderLayout.NORTH);
		queryP.add(querySp, BorderLayout.CENTER);
		queryP.add(bottom, BorderLayout.SOUTH);
		
		userresultSp = new JScrollPane();
		userresultTa = new JTextArea();
		userresultTa.setPreferredSize(new Dimension(300,200));
		userresultSp = new JScrollPane(userresultTa);
		JLabel userresultL = new JLabel("User Result");
		userresultL.setFont(new Font("Courier New", Font.BOLD, 20));
		userresultL.setForeground(titlec);
		userresultP = new JPanel();
		userresultP.setLayout(new BorderLayout());
		userresultP.add(userresultL, BorderLayout.NORTH);
		userresultP.add(userresultSp, BorderLayout.CENTER);
	}
	
	private void run() {
        try {
            startConnection();
            System.out.println("Connection started!");
            showGUI();
            showGenresResultSet();
            
        } catch (SQLException e) {
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
        } catch (ClassNotFoundException e) { 
        	System.err.println("Cannot find the database driver"); 
        }
    }
	
	private void showGenresResultSet() throws SQLException {		
		Statement stmt = con.createStatement();
		String queryGenres = "select distinct G.genres from Movie_genres G order by G.genres";
		ResultSet genresResult = stmt.executeQuery(queryGenres);
		
		while (genresResult.next()) {   
			JCheckBox genres = new JCheckBox(genresResult.getString(1));
			genreP.add(genres);
			
			genres.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (genres.isSelected()) {
						try {
							showCountriesResultSet();
							showCastResultSet();
							showTagResultSet();
						} catch (SQLException e) {
							System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
						}
					}
				}
			});	
		}
		genreP.setLayout(new GridLayout(0, 1));
		genreSp.validate();
		genreSp.repaint();		
	}
	
	private void showCountriesResultSet() throws SQLException {
		DateFormat df = new SimpleDateFormat("yyyy");
		String reportDate = df.format(spinner1.getValue());
		String reportDate1 = df.format(spinner2.getValue()); 
		Integer yearF = Integer.parseInt(reportDate);
		Integer yearT = Integer.parseInt(reportDate1);
		updatechooseGenres();
		String queryCountries = "select distinct C.country "
				+ "\nfrom Movie_countries C, Movies M "
				+ "\nwhere M.mid = C.mid and M.myear>=" 
				+ yearF + " and M.myear<=" + yearT ;
		String order = "\norder by country";
		if (andorCb.getSelectedIndex()==0){
			String subquery = "\nand M.mid in (select G.mid from Movie_genres G where G.genres = '";
			for (String genre:chooseGenres){
				queryCountries += subquery + genre + "')";
			}
		}
		else {
			String subquery = "\nand M.mid in (select G.mid from Movie_genres G where G.genres in (";
			for (String genre:chooseGenres){
				subquery += "'" + genre + "',";
			}
			subquery= subquery.substring(0,subquery.length()-1)+"))";
			queryCountries += subquery;
		}
		queryCountries = queryCountries + order;
		
		Statement stmt = con.createStatement();
		ResultSet countriesResult = stmt.executeQuery(queryCountries);
		//System.out.println(queryCountries);
		countryP.removeAll();
		while (countriesResult.next()) { 
			JCheckBox countries = new JCheckBox(countriesResult.getString(1));
			countryP.add(countries);
			
			countries.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (countries.isSelected()) {
						try {
							showCastResultSet();
							showTagResultSet();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
		countryP.setLayout(new GridLayout(0, 1));
		countrySp.validate();
		countrySp.repaint();
	}
	
	public void showCastResultSet() throws SQLException{
		ArrayList<String> actorList = new ArrayList<String>();
		ArrayList<String> directorList = new ArrayList<String>();
		DateFormat df = new SimpleDateFormat("yyyy");
		String date = df.format(spinner1.getValue());
		String date1 = df.format(spinner2.getValue()); 
		Integer yearF = Integer.parseInt(date);
		Integer yearT = Integer.parseInt(date1);
		String queryActor = "select distinct A.actor_name\nfrom Actors a,Movies m\nwhere "
				+ "a.mid=m.mid and m.myear>=" 
				+ yearF + " and m.myear<=" + yearT ;
		String queryDirector = "select distinct D.director_name\nfrom Directors d,Movies m\nwhere "
				+ "d.mid=m.mid and m.myear>=" 
				+ yearF + " and m.myear<=" + yearT ;
		String ordera = "\norder by actor_name";
		String orderd = "\norder by director_name";
		updatechooseGenres();
		updatechooseCountries();
		if (andorCb.getSelectedIndex()==0){
			String subqueryg = "\nand m.mid in (select g.mid from Movie_genres g where g.genres = '";
			for (String genre:chooseGenres){
				queryActor += subqueryg + genre + "')";
				queryDirector += subqueryg + genre + "')";
			}
			String subqueryc = "\nand m.mid in (select c.mid from Movie_countries c where c.country = '";
			for (String country:chooseCountries){
				queryActor += subqueryc + country + "')";
				queryDirector += subqueryc + country + "')";
			}
		}
		else {
			String subqueryg = "\nand m.mid in (select g.mid from Movie_genres g where g.genres in (";
			for (String genre:chooseGenres){
				subqueryg += "'" + genre + "',";
			}
			subqueryg = subqueryg.substring(0,subqueryg.length()-1)+"))";
			queryActor += subqueryg;
			queryDirector += subqueryg;			
			String subqueryc = "\nand m.mid in (select c.mid from Movie_countries c where c.country in (";
			if (!chooseCountries.isEmpty()){
				for (String country:chooseCountries){
					subqueryc += "'" + country + "',";
				}
				subqueryc = subqueryc.substring(0,subqueryc.length()-1)+"))";
				queryActor += subqueryc;
				queryDirector += subqueryc;
			}
		}
		queryActor += ordera;
		queryDirector += orderd;
		//System.out.println(queryActor);
		//System.out.println(queryDirector);
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		ResultSet actorsResult = stmt.executeQuery(queryActor);
		ResultSet directorsResult = stmt1.executeQuery(queryDirector);
		while (actorsResult.next()){
			actorList.add(actorsResult.getString(1));
		}
		while (directorsResult.next()){
			directorList.add(directorsResult.getString(1));
		}
		actorList.add(0, " select... ");
		directorList.add(0, " select... ");
		String[] actornames = new String[actorList.size()];
		String[] directornames = new String[directorList.size()];
		actornames = actorList.toArray(actornames);
		directornames = directorList.toArray(directornames);
		actorCB1.removeAllItems();
		actorCB2.removeAllItems();
		actorCB3.removeAllItems();
		actorCB4.removeAllItems();
		for (int i=0;i<actornames.length;i++){
			actorCB1.addItem(actornames[i]);
			actorCB2.addItem(actornames[i]);
			actorCB3.addItem(actornames[i]);
			actorCB4.addItem(actornames[i]);
			
		}
		directorCB.removeAllItems();
		for (int i=0;i<directornames.length;i++){
			directorCB.addItem(directornames[i]);
		}
		ItemListener itemListener = new ItemListener() {
		      public void itemStateChanged(ItemEvent itemEvent) {
		    	  try {
					showTagResultSet();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		      }
		    };
		actorCB1.addItemListener(itemListener);
		actorCB2.addItemListener(itemListener);
		actorCB3.addItemListener(itemListener);
		actorCB4.addItemListener(itemListener);
		directorCB.addItemListener(itemListener);
		castSp.validate();
		castSp.repaint();
	}
	
	private void showTagResultSet() throws SQLException{
		DateFormat df = new SimpleDateFormat("yyyy");
		String date = df.format(spinner1.getValue());
		String date1 = df.format(spinner2.getValue()); 
		Integer yearF = Integer.parseInt(date);
		Integer yearT = Integer.parseInt(date1);
		updatechooseGenres();
		updatechooseCountries();
		updatechooseActors();
		updatechooseDirectors();
		String queryTag = "select distinct t.tid,t.tvalue\nfrom tags t,Movie_tags mt,Movies m\n"
				+ "where mt.mid=m.mid and mt.tid=t.tid and m.myear>=" 
				+ yearF + " and m.myear<=" + yearT ;
		String order = "\norder by tid";
		if (andorCb.getSelectedIndex()==0){
			String subqueryg = "\nand m.mid in (select g.mid from Movie_genres g where g.genres = '";
			for (String genre:chooseGenres){
				queryTag += subqueryg + genre + "')";
			}			
			String subqueryc = "\nand m.mid in (select c.mid from Movie_countries c where c.country = '";
			for (String country:chooseCountries){
				queryTag += subqueryc + country + "')";
			}
			String subquerya = "\nand m.mid in (select a.mid from Actors a where a.actor_name = '";
			for (String name:chooseActors){
				queryTag += subquerya + name + "')";
			}			
		}
		else{
			String subqueryg = "\nand m.mid in (select g.mid from Movie_genres g where g.genres in (";
			for (String genre:chooseGenres){
				subqueryg += "'" + genre + "',";
			}
			subqueryg = subqueryg.substring(0,subqueryg.length()-1)+"))";
			queryTag += subqueryg;		
			String subqueryc = "\nand m.mid in (select c.mid from Movie_countries c where c.country in (";
			if (!chooseCountries.isEmpty()){
				for (String country:chooseCountries){
					subqueryc += "'" + country + "',";
				}
				subqueryc = subqueryc.substring(0,subqueryc.length()-1)+"))";
				queryTag += subqueryc;
			}
			String subquerya = "\nand m.mid in (select a.mid from Actors a where a.actor_name in (";
			if (!chooseActors.isEmpty()){
				for (String name:chooseActors){
					subquerya += "'" + name + "',";
				}
				subquerya = subquerya.substring(0,subquerya.length()-1)+"))";
				queryTag += subquerya;
			}
		}
		if (!chooseDirectors.isEmpty()){
			queryTag += "\nand m.mid in (select d.mid from Directors d where d.director_name = '"
					+ chooseDirectors.get(0) + "')" ;
		}
		queryTag += order;
		
		System.out.println(queryTag);
		Statement stmt = con.createStatement();
		ResultSet tagsResult = stmt.executeQuery(queryTag);
		tagP.removeAll();
		while (tagsResult.next()){
			String s = tagsResult.getInt(1) + "    "+ tagsResult.getString(2);
			JCheckBox tags = new JCheckBox(s);
			tagP.add(tags);
			tags.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (tags.isSelected()) {
					}
				}
			});	
		}

		tagP.setLayout(new GridLayout(0, 1));
		tagSp.validate();
		tagSp.repaint();
	}
	
	private void updatechooseGenres(){
		chooseGenres.clear();
		for (Component comp : genreP.getComponents()){
			if (comp instanceof JCheckBox){
				if (((JCheckBox) comp).isSelected()){
					chooseGenres.add(((JCheckBox) comp).getText());
				}
			}
		}
	}
	
	private void updatechooseCountries(){
		chooseCountries.clear();
		for (Component comp : countryP.getComponents()){
			if (comp instanceof JCheckBox){
				if (((JCheckBox) comp).isSelected()){
					chooseCountries.add(((JCheckBox) comp).getText());
				}
			}
		}
	}
	
	private void updatechooseTags(){
		chooseTags.clear();
		for (Component comp : tagP.getComponents()){
			if (comp instanceof JCheckBox){
				if (((JCheckBox) comp).isSelected()){
					chooseTags.add(Integer.parseInt(((JCheckBox) comp).getText().split("\\s+")[0]));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updatechooseActors(){
		chooseActors.clear();
		for (Component comp : castP.getComponents()){
			if (comp instanceof JComboBox){
				if (((JComboBox<String>) comp).getSelectedIndex()!=0){
					chooseActors.add((String) ((JComboBox<String>) comp).getSelectedItem());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updatechooseDirectors(){
		chooseDirectors.clear();
		for (Component comp : directorP.getComponents()){
			if (comp instanceof JComboBox){
				if (((JComboBox<String>) comp).getSelectedIndex()!=0){
					chooseDirectors.add((String) ((JComboBox<String>) comp).getSelectedItem());
				}
			}
		}
	}
	
	private void updatechooseMovies(){
		chooseMovies.clear();
		for (Component comp : movieP.getComponents()){
			if (comp instanceof JCheckBox){
				if (((JCheckBox) comp).isSelected()){
					chooseMovies.add(Integer.parseInt(((JCheckBox) comp).getText().split(",")[0]));
				}
			}
		}
	}
		
	public void updatequery(){
		DateFormat df = new SimpleDateFormat("yyyy");
		String date = df.format(spinner1.getValue());
		String date1 = df.format(spinner2.getValue()); 
		Integer yearF = Integer.parseInt(date);
		Integer yearT = Integer.parseInt(date1);
		String queryMovie = "select m.mid, m.title, g.genres, m.myear, c.country, m.rtRating, m.rtNo\n"
				+ "from Movies m,Movie_genres g,Movie_countries c\n"
				+ "where m.mid=c.mid and m.mid=g.mid and m.myear>="
				+yearF + " and m.myear<=" + yearT;
		String order = "\norder by mid,title";
		updatechooseGenres();
		updatechooseCountries();
		updatechooseActors();
		updatechooseDirectors();
		updatechooseTags();
		if (andorCb.getSelectedIndex()==0){
			String subqueryg = "\nand m.mid in (select g1.mid from Movie_genres g1 where g1.genres = '";
			for (String genre:chooseGenres){
				queryMovie += subqueryg + genre + "')";
			}			
			String subqueryc = "\nand m.mid in (select c1.mid from Movie_countries c1 where c1.country = '";
			for (String country:chooseCountries){
				queryMovie += subqueryc + country + "')";
			}
			String subquerya = "\nand m.mid in (select a.mid from Actors a where a.actor_name = '";
			for (String name:chooseActors){
				queryMovie += subquerya + name + "')";
			}	
			String subqueryt = "\nand m.mid in (select mt.mid from Movie_tags mt,tags t where mt.tid=t.tid and t.tid=";
			for (int i:chooseTags){
				queryMovie += subqueryt + i + ")";
			}
		}
		else{
			String subqueryg = "\nand m.mid in (select g1.mid from Movie_genres g1 where g1.genres in (";
			for (String genre:chooseGenres){
				subqueryg += "'" + genre + "',";
			}
			subqueryg = subqueryg.substring(0,subqueryg.length()-1)+"))";
			queryMovie += subqueryg;		
			String subqueryc = "\nand m.mid in (select c1.mid from Movie_countries c1 where c1.country in (";
			if (!chooseCountries.isEmpty()){
				for (String country:chooseCountries){
					subqueryc += "'" + country + "',";
				}
				subqueryc = subqueryc.substring(0,subqueryc.length()-1)+"))";
				queryMovie += subqueryc;
			}
			String subquerya = "\nand m.mid in (select a.mid from Actors a where a.actor_name in (";
			if (!chooseActors.isEmpty()){
				for (String name:chooseActors){
					subquerya += "'" + name + "',";
				}
				subquerya = subquerya.substring(0,subquerya.length()-1)+"))";
				queryMovie += subquerya;
			}
			String subqueryt = "\nand m.mid in (select mt.mid from Movie_tags mt,tags t where mt.tid=t.tid and t.tid in (";
			if (!chooseTags.isEmpty()){
				for (int i:chooseTags){
					subqueryt += i+ ",";
				}
				subqueryt = subqueryt.substring(0,subqueryt.length()-1)+"))";
				queryMovie += subqueryt;
			}
		}
		if (!chooseDirectors.isEmpty()){
			queryMovie += "\nand m.mid in (select d.mid from Directors d where d.director_name = '"
					+ chooseDirectors.get(0) + "')" ;
		}
		if (!valueTf.getText().equals("")){
			queryMovie += "\nand m.mid in (select mt1.mid from Movie_tags mt1 where mt1.tag_weight"
					+ compareCb.getSelectedItem() + valueTf.getText() + ")";
		}
		showQuery = queryMovie + order;
		queryTa.setText(showQuery);
		queryP.validate();
		queryP.repaint();
		
	}
	
	private void runmoviesQuery() throws SQLException {
		updatequery();
		Statement stmt = con.createStatement();					
		//System.out.println(showQuery);

		ResultSet movieResult = stmt.executeQuery(showQuery);
		movieP.removeAll();
		while (movieResult.next()){
			String result = movieResult.getInt(1)+", "+movieResult.getString(2)+", "+movieResult.getString(3)+", "
			+movieResult.getInt(4)+", "+movieResult.getString(5)+", "+movieResult.getDouble(6) + ", "
			+movieResult.getInt(7);
			JCheckBox movies = new JCheckBox(result);
			movieP.add(movies);
			movies.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent event) {
					if (movies.isSelected()) {
					}
				}
			});	
		}
		
		movieP.setLayout(new GridLayout(0, 1));
		movieresultSp.validate();
		movieresultSp.repaint();
	}
	
	public void runuserQuery() throws SQLException{
		updatechooseMovies();
		updatechooseTags();
		String queryuser = "";
		String queryuser1 = "select distinct u.userid from Usertagmovies u \nwhere ";
		String order = "\norder by userid";
		if (andorCb.getSelectedIndex()==0){
			int count = 0;
			for (int tag:chooseTags){
				if (count == 0){
					queryuser += queryuser1 + "u.tid = " + tag;
				}
				else {
					queryuser += "\nintersect\n" + queryuser1 + "u.tid = " + tag;
				}
				count = 1;
			}
			for (int movie:chooseMovies){
				if (count == 0){
					queryuser += queryuser1 + "u.mid = " + movie;
				}
				else {
					queryuser += "\nintersect\n" + queryuser1 + "u.mid = " + movie;
				}
			}
		}
		else {
			String queryt = "u.tid in (";
			for (int tag:chooseTags){
				queryt += tag + ",";
			}
			queryt = queryt.substring(0,queryt.length()-1)+")";
			if (!chooseTags.isEmpty()){
				queryuser += queryuser1 + queryt;
			}
			String querym = "u.mid in (";
			if (!chooseMovies.isEmpty()){
				for (int movie:chooseMovies){
					querym += movie + ",";
				}
				querym = querym.substring(0,querym.length()-1)+")";
				if (chooseTags.isEmpty()){
					queryuser += queryuser1 + querym;
				}
				else {
					queryuser +=  " and " + querym;
				}
			}
		}
		if (chooseTags.isEmpty()&&chooseMovies.isEmpty()){
			JOptionPane.showMessageDialog(this, "please select at least a movie or a tag");
		}
		else {
			queryuser += order;		
		
			Statement stmt = con.createStatement();					
			//System.out.println(queryuser);
			ResultSet userResult = stmt.executeQuery(queryuser);
			userresultTa.removeAll();
			String result = "";
			while (userResult.next()){	
				result += "user id " + userResult.getInt(1) +"\n";		
			}
			userresultTa.setText(result);
			userresultSp.validate();
			userresultSp.repaint();
		}
	}
	
	public void showGUI() throws SQLException{
		JPanel centerP = new JPanel();
		centerP.setLayout(new FlowLayout());
		genreContainer.setPreferredSize(new Dimension(200,400));
		countrySp.setPreferredSize(new Dimension(180,400));
		castSp.setPreferredSize(new Dimension(260,400));
		tagContainer.setPreferredSize(new Dimension(200,400));
		
		centerP.add(genreContainer);
		centerP.add(countrySp);
		centerP.add(castSp);
		centerP.add(tagContainer);
		//centerP.setBackground(new Color(230, 255, 255));
		JLabel atitle = new JLabel("Movie Attributes");
		atitle.setFont(new Font("Courier New", Font.BOLD, 20));
		atitle.setForeground(titlec);

		JPanel attContainerP = new JPanel();
		attContainerP.setLayout(new BorderLayout());
		attContainerP.add(atitle, BorderLayout.NORTH);
		attContainerP.add(centerP, BorderLayout.CENTER);
		serchP.setBackground(new Color(230, 255, 255));
		attContainerP.add(serchP, BorderLayout.SOUTH);
		
		JPanel eastP = new JPanel();
		JLabel etitle = new JLabel("Movie Result");
		etitle.setFont(new Font("Courier New", Font.BOLD, 20));
		etitle.setForeground(titlec);
		eastP.setLayout(new BorderLayout());
		eastP.add(etitle,BorderLayout.NORTH);
		eastP.add(movieresultSp, BorderLayout.CENTER);
		eastP.setPreferredSize(new Dimension(300,400));
		eastP.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		JPanel southP = new JPanel();
		southP.setLayout(new BorderLayout());
		southP.add(queryP, BorderLayout.CENTER);
		userresultP.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		userresultP.setPreferredSize(new Dimension(300,200));
		southP.add(userresultP, BorderLayout.EAST);
		
		setLayout (new BorderLayout());
		add(attContainerP,BorderLayout.CENTER);
		add(eastP,BorderLayout.EAST);
		add(southP,BorderLayout.SOUTH);
		setBackground(new Color(0, 51, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        endConnection();
		        System.out.println("Connection ended!");
		    }
		});
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
    }
	
	private void endConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Cannot close connection: " + e.getMessage());
		}
	}
	
	

	public static void main(String[] args) throws SQLException {
		hw3 test = new hw3();
		test.run();
	}

}
