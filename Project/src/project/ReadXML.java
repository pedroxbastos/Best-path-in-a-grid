package project;

import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import coordinate.Coordinate;
import coordinate.Edge;
import pec.Eventslist;
import pec.PEC;
import java.util.List;
import java.util.ArrayList;

public class ReadXML {
	
	public static void readXML(String file) {
		Grid g;
		Population p;
		 try {
			 	
			 DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			 domFactory.setValidating(true);
			 DocumentBuilder builder = domFactory.newDocumentBuilder();
			 Document doc = builder.parse(file);

			 doc.getDocumentElement().normalize();

				//Serach for the node list, the node that documents the beggining of the simulation data
				NodeList nList = doc.getElementsByTagName("simulation");
				
				//Inside the node element look for the attributes like finalints, initpop...
				int finalinst = Integer.parseInt(nList.item(0).getAttributes().getNamedItem("finalinst").getNodeValue());
				if(finalinst<0) {
					System.out.println("Invalid finalinst");
					System.exit(-1);
				}
				int initpop = Integer.parseInt(nList.item(0).getAttributes().getNamedItem("initpop").getNodeValue());
				if(initpop<0) {
					System.out.println("Invalid initpop");
					System.exit(-1);
				}
				int maxpop = Integer.parseInt(nList.item(0).getAttributes().getNamedItem("maxpop").getNodeValue());
				if (maxpop<0) {
					System.out.println("Invalid maxpop");
					System.exit(-1);
				}
				int comfortsens = Integer.parseInt(nList.item(0).getAttributes().getNamedItem("comfortsens").getNodeValue());
				if(comfortsens<0) {
					System.out.println("Invalid comfortsens");
					System.exit(-1);
				}
				
				//Lool for another element, and inside this element "Grid" search for his attributes
				NodeList grid = doc.getElementsByTagName("grid");
				int colsnb = Integer.parseInt(grid.item(0).getAttributes().getNamedItem("colsnb").getNodeValue());
				if (colsnb<0) {
					System.out.println("Invalid colsnb");
					System.exit(-1);
				}
				int rowsnb = Integer.parseInt(grid.item(0).getAttributes().getNamedItem("rowsnb").getNodeValue());
				if (rowsnb<0) {
					System.out.println("Invalid rowsnb");
					System.exit(-1);
				}
				
				//Look for the initial point element, and search for his attributes
				NodeList initialpoint = doc.getElementsByTagName("initialpoint");
				int xposinicial = Integer.parseInt(initialpoint.item(0).getAttributes().getNamedItem("xinitial").getNodeValue());
				if ((xposinicial<0)||(xposinicial>colsnb)) {
					System.out.println("Invalid x_initialpoint");
					System.exit(-1);
				}
				int yposinicial = Integer.parseInt(initialpoint.item(0).getAttributes().getNamedItem("yinitial").getNodeValue());
				if ((yposinicial<0)||(yposinicial>rowsnb)) {
					System.out.println("Invalid y_initialpoint");
					System.exit(-1);
				}
				Coordinate pi = new Coordinate(xposinicial, yposinicial);
				
				//Repeat the attributes seacrh for the eleemnt finalpoint
				NodeList finalpoint = doc.getElementsByTagName("finalpoint");
				int xposfinal =Integer.parseInt(finalpoint.item(0).getAttributes().getNamedItem("xfinal").getNodeValue());
				if((xposfinal<0)||(xposfinal>colsnb)) {
					System.out.println("Invalid x_finalpoint");
					System.exit(-1);
				}
				int yposfinal =Integer.parseInt(finalpoint.item(0).getAttributes().getNamedItem("yfinal").getNodeValue());
				if((yposfinal<0)||(yposfinal>rowsnb)) {
					System.out.println("Invalid y_finalpoint");
					System.exit(-1);
				}
				Coordinate pf = new Coordinate(xposfinal, yposfinal);
				
				//Search for the element zone is found in the file
				NodeList specialcostzones = doc.getElementsByTagName("zone");
				int length_zones= specialcostzones.getLength(); //Get the number of times it appears
				if(length_zones<0) {
					System.out.println("Invalid number of specialcostzones");
					System.exit(-1);
				}
				NodeList obstacles = doc.getElementsByTagName("obstacles");
				int num = Integer.parseInt(obstacles.item(0).getAttributes().getNamedItem("num").getNodeValue());
				if (num<0) {
					System.out.println("Invalid number of obstacles");
					System.exit(-1);
				}
				
				//Crate a Grid g, from class Grid
				g = new Grid(colsnb,rowsnb,pi,pf,finalinst, num, comfortsens);
				int cmax=0;
				//Cycle to go through the number of times, that zone was found in the file, and get his attributes, and add them to the grid
				for (int i=0; i<length_zones;i++) {
					int xposicao = Integer.parseInt(specialcostzones.item(i).getAttributes().getNamedItem("xinitial").getNodeValue());
					int yposicao = Integer.parseInt(specialcostzones.item(i).getAttributes().getNamedItem("yinitial").getNodeValue());
					Coordinate zpi = new Coordinate(xposicao,yposicao);
					xposicao = Integer.parseInt(specialcostzones.item(i).getAttributes().getNamedItem("xfinal").getNodeValue());
					yposicao = Integer.parseInt(specialcostzones.item(i).getAttributes().getNamedItem("yfinal").getNodeValue());
					Coordinate zpf = new Coordinate(xposicao,yposicao);
					int czona = Integer.parseInt(specialcostzones.item(i).getTextContent());
					if(czona<0)
						System.exit(-1);
					if(czona > cmax)
						cmax = czona;
					Edge e = new Edge(zpi, zpf, czona);
					if ( (zpi.getX()>zpf.getX()) || (zpi.getY()>zpi.getY()) ) {
						System.out.println("Invalid special costzone");
						System.exit(-1);
					}
					g.ExtractEdge(e);
				}
				//Set the maximum cost found for the special zones
				g.setCmax(cmax);
				//Repeat the process made for the special zones, but for the obstacles informations
				NodeList obstacle = doc.getElementsByTagName("obstacle");
				
				for (int i=0;i<num;i++) {
					int xposicao = Integer.parseInt(obstacle.item(i).getAttributes().getNamedItem("xpos").getNodeValue());
					int yposicao = Integer.parseInt(obstacle.item(i).getAttributes().getNamedItem("ypos").getNodeValue());
					if((xposicao<0)||(yposicao<0)) {
						System.out.println("Invalid obstacle");
						System.exit(-1);
					}
					Coordinate po = new Coordinate(xposicao,yposicao);
					g.addObs(po, i);
				}
				
				//Get the parameters related to the individuals life
				NodeList deaths = doc.getElementsByTagName("death");
				int death = Integer.parseInt(deaths.item(0).getAttributes().getNamedItem("param").getNodeValue());
				if(death<0) {
					System.out.println("Invalid dead param");
					System.exit(-1);
				}

				NodeList reproductions = doc.getElementsByTagName("reproduction");
				int reproduction = Integer.parseInt(reproductions.item(0).getAttributes().getNamedItem("param").getNodeValue());
				if(reproduction<0) {
					System.out.println("Invalid reproduction param");
					System.exit(-1);
				}

				NodeList moves = doc.getElementsByTagName("move");
				int move = Integer.parseInt(moves.item(0).getAttributes().getNamedItem("param").getNodeValue());
				if(move<0) {
					System.out.println("Invalid move param");
					System.exit(-1);
				}
				//Initialize a population
				p = new Population(initpop, maxpop, death, reproduction, move);
				List<Event> list = new ArrayList<>();
				PEC pec = new Eventslist(list, 0);

				//Add as much individuals as the initial population
				for(int i=0; i<p.getNi(); i++) {
					List<Coordinate> l = new ArrayList<>();
					l.add(g.getPi());
					Individual indiv = new Individual(g, p.getNid(), g.getPi(), l, 0, 0);
					p.AddIndividual(indiv,0,pec,g);	
				}
				
				//Generate the observations events, since this event time is fixed, and we already know where to add them
				for(int i = 0; i <= 20; i++) {
					Observation o = new Observation(g, i);
					pec.addEvent(o);
				}
				
				//Pass all the information,list and structures to class Total, so that we can return the to the simulation
				new Data(g,p,pec);

				//return sim;
				// If the File reading fails, then an Exception is thrown 
			    } catch (Exception e) {
				e.printStackTrace();
			    }
		// return null;
	}

}
