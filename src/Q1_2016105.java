import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.jena.graph.Triple;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.shacl.engine.Targets;
import org.apache.jena.util.FileManager;

public class Q1_2016105 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Creating properties for configuration file
		Properties pro = new Properties();
		FileInputStream pro_inp =  new FileInputStream("configuration.properties");
		pro.load(pro_inp);
		
		//Loading the ontology for creating triples
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		InputStream in = new FileInputStream(pro.getProperty("input_owl"));
		m.read(in,"RDF/XML");
		
		//Setting up the URI
		String base="http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#";
		String agentURI="http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#";
		String DescriptionURI = "http://www.ontologydesignpatterns.org/cp/owl/description.owl#";
		
		
		//Instantiating all the classes
		
		OntClass Actor = m.getOntClass( base + "Actor" );
		OntClass genreSet = m.getOntClass(base + "GenreSet");
		OntClass role = m.getOntClass(agentURI+ "Role"); 
		OntClass Movie = m.getOntClass(base+"Movie");
		OntClass TvShow = m.getOntClass(base + "TvShow");
		OntClass Country = m.getOntClass(base + "Country");
		OntClass Duration = m.getOntClass(base + "Duration"); 
		OntClass Description = m.getOntClass(DescriptionURI + "Description");
		OntClass Genre = m.getOntClass(base + "Genre" ); 
		OntClass Director = m.getOntClass(base+"Director");
		OntClass Rating = m.getOntClass(base+"Rating");
		OntClass ReleaseYear = m.getOntClass(base + "ReleaseYear");
		OntClass Cast = m.getOntClass(base+"Cast");
		OntClass CountryReleaseSet = m.getOntClass(base + "CountryReleaseSet"); 
		OntClass DirectorSet = m.getOntClass(base + "DirectorSet");
		
////////////////////////////////////////////////////////////////
		
		// Instantiating properties
		
		//Object Properties
		OntProperty hasCast = m.getObjectProperty(base+"hasCast");
		OntProperty hasActors = m.getObjectProperty(base+"hasActors");
		OntProperty hasCountry = m.getObjectProperty(base+"hasCountry");
		OntProperty hasDirector = m.getObjectProperty(base+"hasDirector");
		OntProperty hasGenres = m.getObjectProperty(base+"hasGenres");
		OntProperty hasDirectorSet = m.getObjectProperty(base+"hasDirectorSet");
		OntProperty hasDuration = m.getObjectProperty(base+"hasDuration");
		OntProperty hasGenreSet = m.getObjectProperty(base+"hasGenreSet");
		OntProperty hasRating = m.getObjectProperty(base+"hasRating");
		OntProperty hasReleaseYear = m.getObjectProperty(base+"hasReleaseYear");
		OntProperty hasRole = m.getObjectProperty(agentURI+"hasRole"); 	
		OntProperty isDefinedIn = m.getObjectProperty(DescriptionURI+"isDefinedIn");
		OntProperty isReleasedIn = m.getOntProperty(base + "isReleasedIn");
		
		//dataProperties
		OntProperty hasDescription = m.getDatatypeProperty(base + "hasDescription"); 
		OntProperty hasYear = m.getDatatypeProperty(base + "hasYear"); 		
		OntProperty hasValue = m.getDatatypeProperty(base + "hasValue"); 
		OntProperty hasName = m.getDatatypeProperty(base + "hasName"); 
		
		
		//Instantiating Individuals
		Individual role_acting = m.createIndividual( base + "Acting", role );
		Individual role_directing = m.createIndividual(base + "Directing", role);
		
		
		
		//Reading the CSV
		FileReader filereader = new FileReader(pro.getProperty("csv_file"));
		CSVReader reader = new CSVReader(filereader);
		String[] line;
		 try {
			int cnt= 0;
			while ((line = reader.readNext()) != null) {
				if(cnt ==0) {
					cnt = cnt +1;
					continue;
				}
				else {
				cnt = cnt+1; 
				//checking type
				if(line[1].equals("Movie")) {
					Individual movie_ind;
					//creating individuals
					if (line[2].charAt(0)=='#') {
						movie_ind = m.createIndividual( base + "realityhigh", Movie );
					}
					else {
						movie_ind = m.createIndividual( base + line[2].replace(' ', '_'), Movie );
					}
					
					Individual release_year_ind = m.createIndividual( base + line[7].replace(' ', '_'), ReleaseYear );
					Individual rating_ind = m.createIndividual( base + line[8].replace(' ', '_'), Rating);
					Individual duration_ind = m.createIndividual( base + line[9].replace(' ', '_'), Duration );
					Individual Desc_ind = m.createIndividual(DescriptionURI +"desc_" +line[0].replace(' ', '_'), Description);
					Individual directorset_ind = m.createIndividual( base +"director_"+ line[0].replace(' ', '_'), DirectorSet );
					Individual cast_ind = m.createIndividual( base +"cast_" +line[0].replace(' ', '_'), Cast);
					Individual genreset_ind = m.createIndividual( base +"genre_" +line[0].replace(' ', '_'), genreSet );
					Individual countryset_ind = m.createIndividual(base +"country_" +line[0].replace(' ', '_'), CountryReleaseSet);
					
					
					//creating statements
					Statement s1 = ResourceFactory.createStatement(movie_ind, hasReleaseYear, release_year_ind);
					Statement s2 = ResourceFactory.createStatement(movie_ind, hasCast, cast_ind);
					Statement s3 = ResourceFactory.createStatement(movie_ind, hasDirectorSet,directorset_ind );
					Statement s4 = ResourceFactory.createStatement(movie_ind, hasDuration,duration_ind );
					Statement s5 = ResourceFactory.createStatement(movie_ind, hasGenreSet,genreset_ind );
					Statement s6 = ResourceFactory.createStatement(movie_ind, hasRating,rating_ind );
					Statement s7 = ResourceFactory.createStatement(movie_ind, isDefinedIn,Desc_ind );
					Statement s8 = ResourceFactory.createStatement(movie_ind, isReleasedIn,countryset_ind );
					Literal desc_lit =  ResourceFactory.createStringLiteral(line[11]);
					Statement s_prime = ResourceFactory.createStatement(Desc_ind, hasDescription, desc_lit);
					Statement s_dur = ResourceFactory.createStatement(duration_ind, hasValue, ResourceFactory.createStringLiteral(line[9]));
					Statement s_year = ResourceFactory.createStatement(release_year_ind, hasYear, ResourceFactory.createTypedLiteral(Integer.parseInt(line[7])));
					Statement s_tit = ResourceFactory.createStatement(movie_ind, hasValue, ResourceFactory.createStringLiteral(line[2]));
					
					//adding in the model
					m.add(s_tit);
					m.add(s1);
					m.add(s2);
					m.add(s3);
					m.add(s4);
					m.add(s5);
					m.add(s6);
					m.add(s7);
					m.add(s8);
					m.add(s_prime);
					m.add(s_dur);
					m.add(s_year);
					//for genre 
					if (line[10].length()==0) {continue;}
					else {
					String [] genrelist = line[10].split(",");
					//loop for genre
					for (int i1 = 0; i1<genrelist.length;i1++) 
					{
						Individual genre_ind = m.createIndividual(base+ genrelist[i1].trim().replace(' ', '_'),Genre);
						Statement s9 = ResourceFactory.createStatement(genreset_ind, hasGenres,genre_ind );
						Statement s10 = ResourceFactory.createStatement(genre_ind, hasValue, ResourceFactory.createStringLiteral(genrelist[i1].trim()));
						m.add(s9);
						m.add(s10);
					}
					}
					if (line[4].length()==0) {continue;}
					else {
					String [] actorlist = line[4].split(",");
					//loop for actor
					for (int i1 = 0; i1<actorlist.length;i1++) 
					{
						
						Individual actor_ind = m.createIndividual(base+ actorlist[i1].trim().replace(' ', '_'),Actor);
						Statement s9 = ResourceFactory.createStatement(cast_ind, hasActors,actor_ind );
						Statement s10 = ResourceFactory.createStatement(actor_ind, hasRole,role_acting );
						Statement s_11 = ResourceFactory.createStatement(actor_ind, hasName, ResourceFactory.createStringLiteral(actorlist[i1].trim()));
						m.add(s9);			
						m.add(s10);
						m.add(s_11);
					}
					}
					//for directors
					if (line[3].length()==0) {continue;}
					else {
					String [] directorlist = line[3].split(",");
					//loop for director
					for (int i1 = 0; i1<directorlist.length;i1++) 
					{
						Individual director_ind = m.createIndividual(base+ directorlist[i1].trim().replace(' ', '_'),Director);
						Statement s9 = ResourceFactory.createStatement(directorset_ind, hasDirector,director_ind );
						Statement s10 = ResourceFactory.createStatement(director_ind, hasRole,role_directing );
						Statement s_11 = ResourceFactory.createStatement(director_ind, hasName, ResourceFactory.createStringLiteral(directorlist[i1].trim()));
						m.add(s9);
						m.add(s10);
						m.add(s_11);
					}
					}
					
					//for directors
					if (line[5].length()==0) {continue;}
					else {
					String [] countrylist = line[5].split(",");
					//loop for director
					for (int i1 = 0; i1<countrylist.length;i1++) 
					{
						Individual country_ind = m.createIndividual(base+ countrylist[i1].trim().replace(' ', '_'),Country);
						Statement s9 = ResourceFactory.createStatement(countryset_ind, hasCountry,country_ind );
						m.add(s9);
					}
					}
					
				}
				else {
					//creating individuals
					Individual movie_ind;
					//creating individuals
					if (line[2].charAt(0)=='#') {
						movie_ind = m.createIndividual( base + "realityhigh", TvShow );
					}
					else {
						movie_ind = m.createIndividual( base + line[2].replace(' ', '_'), TvShow );
					}
					Individual release_year_ind = m.createIndividual( base + line[7].replace(' ', '_'), ReleaseYear );
					Individual rating_ind = m.createIndividual( base + line[8].replace(' ', '_'), Rating);
					Individual duration_ind = m.createIndividual( base + line[9].replace(' ', '_'), Duration );
					Individual Desc_ind = m.createIndividual(DescriptionURI +"desc_" +line[0].replace(' ', '_'), Description);
					Individual directorset_ind = m.createIndividual( base +"director_"+ line[0].replace(' ', '_'), DirectorSet );
					Individual cast_ind = m.createIndividual( base +"cast_" +line[0].replace(' ', '_'), Cast);
					Individual genreset_ind = m.createIndividual( base +"genre_" +line[0].replace(' ', '_'), genreSet );
					Individual countryset_ind = m.createIndividual(base +"country_" +line[0].replace(' ', '_'), CountryReleaseSet);
					
					
					//creating statements
					Statement s1 = ResourceFactory.createStatement(movie_ind, hasReleaseYear, release_year_ind);
					Statement s2 = ResourceFactory.createStatement(movie_ind, hasCast, cast_ind);
					Statement s3 = ResourceFactory.createStatement(movie_ind, hasDirectorSet,directorset_ind );
					Statement s4 = ResourceFactory.createStatement(movie_ind, hasDuration,duration_ind );
					Statement s5 = ResourceFactory.createStatement(movie_ind, hasGenreSet,genreset_ind );
					Statement s6 = ResourceFactory.createStatement(movie_ind, hasRating,rating_ind );
					Statement s7 = ResourceFactory.createStatement(movie_ind, isDefinedIn,Desc_ind );
					Statement s8 = ResourceFactory.createStatement(movie_ind, isReleasedIn,countryset_ind );
					Literal desc_lit =  ResourceFactory.createStringLiteral(line[11]);
					Statement s_prime = ResourceFactory.createStatement(Desc_ind, hasDescription, desc_lit);
					Statement s_dur = ResourceFactory.createStatement(duration_ind, hasValue, ResourceFactory.createStringLiteral(line[9]));
					Statement s_year = ResourceFactory.createStatement(release_year_ind, hasYear, ResourceFactory.createTypedLiteral(Integer.parseInt(line[7])));
					Statement s_tit = ResourceFactory.createStatement(movie_ind, hasValue, ResourceFactory.createStringLiteral(line[2]));
					
					//adding in the model
					m.add(s_tit);
					m.add(s1);
					m.add(s2);
					m.add(s3);
					m.add(s4);
					m.add(s5);
					m.add(s6);
					m.add(s7);
					m.add(s8);
					m.add(s_prime);
					m.add(s_dur);
					m.add(s_year);
				
					
					//for genre 
					if (line[10].length()==0) {continue;}
					else {
					String [] genrelist = line[10].split(",");
					//loop for genre
					for (int i1 = 0; i1<genrelist.length;i1++) 
					{
						Individual genre_ind = m.createIndividual(base+ genrelist[i1].trim().replace(' ', '_'),Genre);
						Statement s10 = ResourceFactory.createStatement(genre_ind, hasValue, ResourceFactory.createStringLiteral(genrelist[i1].trim()));
						Statement s9 = ResourceFactory.createStatement(genreset_ind, hasGenres,genre_ind );
						m.add(s9);
						m.add(s10);
					}
					}
					if (line[4].length()==0) {continue;}
					else {
					String [] actorlist = line[4].split(",");
					//loop for actor
					for (int i1 = 0; i1<actorlist.length;i1++) 
					{
						
						Individual actor_ind = m.createIndividual(base+ actorlist[i1].trim().replace(' ', '_'),Actor);
						Statement s9 = ResourceFactory.createStatement(cast_ind, hasActors,actor_ind );
						Statement s10 = ResourceFactory.createStatement(actor_ind, hasRole,role_acting );
						Statement s_11 = ResourceFactory.createStatement(actor_ind, hasName, ResourceFactory.createStringLiteral(actorlist[i1].trim()));
						m.add(s9);
						m.add(s10);
						m.add(s_11);
					}
					}
					//for directors
					if (line[3].length()==0) {continue;}
					else {
					String [] directorlist = line[3].split(",");
					//loop for director
					for (int i1 = 0; i1<directorlist.length;i1++) 
					{
						Individual director_ind = m.createIndividual(base+ directorlist[i1].trim().replace(' ', '_'),Director);
						Statement s9 = ResourceFactory.createStatement(directorset_ind, hasDirector,director_ind );
						Statement s10 = ResourceFactory.createStatement(director_ind, hasRole,role_directing );
						Statement s_11 = ResourceFactory.createStatement(director_ind, hasName, ResourceFactory.createStringLiteral(directorlist[i1].trim()));
						m.add(s9);
						m.add(s10);
						m.add(s_11);
					}
					}
					
					//for countries
					if (line[5].length()==0) {continue;}
					else {
					String [] countrylist = line[5].split(",");
					//loop for countries
					for (int i1 = 0; i1<countrylist.length;i1++) 
					{
						Individual country_ind = m.createIndividual(base+ countrylist[i1].trim().replace(' ', '_'),Country);
						Statement s9 = ResourceFactory.createStatement(countryset_ind, hasCountry,country_ind );
						m.add(s9);
					}
					}
					
				}
				
				
				}
			 }
			
			//// Saving the knowledge Graph
			System.out.println("Knowledge graph created! ");
			FileManager.get().addLocatorClassLoader(Q1_2016105.class.getClassLoader());
			String fileName = pro.getProperty("output_owl");
			FileWriter out = new FileWriter(fileName);
			m.write(out,"TURTLE");
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
