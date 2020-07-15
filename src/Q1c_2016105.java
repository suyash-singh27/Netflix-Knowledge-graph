import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.rdfconnection.RDFConnectionRemote;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class Q1c_2016105 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//Loading properties for configuration file
		Properties pro = new Properties();
		FileInputStream pro_inp =  new FileInputStream("configuration.properties");
		pro.load(pro_inp);
		RDFConnection conn = RDFConnectionRemote.create().destination(pro.getProperty("local_host")).queryEndpoint("sparql").build();
		
		//Loading Knowledge graph on fuseki
		conn.load(pro.getProperty("kg_file"));
		
		
		//Creating New movies Graph
		
		System.out.println("Graph Loaded!");

		
		System.out.println("Step 1: creating all the named graphs:");
		
		//creating new movies graph 
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/newmoviesgraph> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				" ?subject ?predicate ?object.\n" + 
				"  ?subject base:hasReleaseYear ?obj2.\n" + 
				"  ?obj2 base:hasYear ?val.\n" + 
				"  FILTER (?val>=2016)"+
				"}");
		System.out.println("New Graph Made");

		
		// for creating OLD movies GRAPH
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/oldmoviesgraph> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				" ?subject ?predicate ?object.\n" + 
				"  ?subject base:hasReleaseYear ?obj2.\n" + 
				"  ?obj2 base:hasYear ?val.\n" + 
				"  FILTER (?val<2016)"+
				"}");
		System.out.println("Old Graph Made");

		
		//For Genre graph
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Genre> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"  ?sub1 base:hasGenreSet ?obj1 .\n" + 
				"  ?obj1 base:hasGenres ?obj2.\n" + 
				"  ?obj2 base:hasValue ?obj3.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1 || ?subject =?obj2) && (?predicate=base:hasGenres||?predicate=base:hasValue))"+
				"}");
		System.out.println("Genre Graph Made");
		
		
		//For creating Countries graph
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Country> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"    ?sub1 base:isReleasedIn ?obj1 .\n" + 
				"  ?obj1 base:hasCountry ?obj2.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1) && (?predicate=base:hasCountry))"+
				"}");
		System.out.println("Country Graph Made");
		
		// For creating Description graph
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
				"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Description> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"     ?sub1 des:isDefinedIn ?obj1 .\n" + 
				"  ?obj1 base:hasDescription ?obj2.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1) && (?predicate=base:hasDescription))"+
				"}");
		System.out.println("description Graph Made");
		
		
		// For creating Duration graph
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
				"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Duration> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"    ?sub1 base:hasDuration ?obj1 .\n" + 
				"  ?obj1 base:hasValue ?obj2.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1) && (?predicate=base:hasValue))"+
				"}");
		System.out.println("duration Graph Made");

		
		//for release YEAR graph 
		
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
				"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Year> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"    ?sub1 base:hasReleaseYear ?obj1 .\n" + 
				"  ?obj1 base:hasYear ?obj2.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1) && (?predicate=base:hasYear))"+
				"}");
		System.out.println("year Graph Made");

		//for Director graph

		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
				"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
				"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
				"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
				+ "INSERT\n" + 
				"{\n" + 
				"    GRAPH <http://iiitd.ac.in/sweb/2016105/Director> {\n" + 
				"        ?subject ?predicate ?object\n" + 
				"    }\n" + 
				"}\n" + 
				"\n" +
				"WHERE\n" + 
				"{\n " + 
				"   ?sub1 base:hasDirectorSet ?obj1 .\n" + 
				"  ?obj1 base:hasDirector ?obj2.\n" + 
				"  ?obj2 rol:hasRole ?obj21.\n" + 
				"  ?obj2 base:hasName ?obj3.\n" + 
				"  ?subject ?predicate ?object.\n" + 
				"  Filter((?subject =?obj1 || ?subject =?obj2) && (?predicate= base:hasDirector ||?predicate=base:hasName||?predicate=rol:hasRole))"+
				"}");
		System.out.println("director Graph Made");

		
		//for creating Actor graph
		
		
		conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
					"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
					"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
					"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
					+ "INSERT\n" + 
					"{\n" + 
					"    GRAPH <http://iiitd.ac.in/sweb/2016105/Actor> {\n" + 
					"        ?subject ?predicate ?object\n" + 
					"    }\n" + 
					"}\n" + 
					"\n" +
					"WHERE\n" + 
					"{\n " + 
					"    ?sub1 base:hasCast ?obj1 .\n" + 
					"    ?obj1 base:hasActors ?obj2.\n" + 
					"    ?obj2 rol:hasRole ?obj21.\n" + 
					"    ?obj2 base:hasName ?obj3.\n" + 
					"    ?subject ?predicate ?object.\n" + 
					"    Filter((?subject =?obj1 || ?subject =?obj2) && (?predicate= base:hasActors ||?predicate=base:hasName||?predicate=rol:hasRole))"+
					"}");
			System.out.println("Actor Graph Made");
			
			// for creating Base Graph
			
			
			conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
					"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
					"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
					"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
					+ "INSERT\n" + 
					"{\n" + 
					"    GRAPH <http://iiitd.ac.in/sweb/2016105/Base> {\n" + 
					"        ?subject ?predicate ?object\n" + 
					"    }\n" + 
					"}\n" + 
					"\n" +
					"WHERE\n" + 
					"{\n " + 
					"      ?subject ?predicate ?object.\n" + 
					"  Filter (!contains(str(?predicate),\"suyash\")&&!contains(str(?predicate),\"objectrole\")&&!contains(str(?predicate),\"description\")&&!contains(str(?predicate),\"collection\"))"+
					"}");
			System.out.println("Base Graph Made");
			
			
			// Deleting from original graph
			
			conn.update("PREFIX base: <http://www.semanticweb.org/suyash-singh/ontologies/2020/3/2016105_Q3#>\n" + 
					"PREFIX des: <http://www.ontologydesignpatterns.org/cp/owl/description.owl#>\n" + 
					"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n"+
					"PREFIX rol: <http://www.ontologydesignpatterns.org/cp/owl/objectrole.owl#>"
					+ "DELETE\n" + 
					"    {\n" + 
					"        ?subject ?predicate ?object\n" + 
					"    }\n" + 
					"\n" +
					"WHERE\n" + 
					"{\n " + 
					"    ?subject ?predicate ?object\n" + 					 
					"}");
			System.out.println("triples deleted from default");

		
	}

}
