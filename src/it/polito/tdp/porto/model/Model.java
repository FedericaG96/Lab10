package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	PortoDAO dao;
	Map<Integer, Author> idMap;
	Graph<Author, MyEdge> grafo;
	Map<Integer, Paper> idMapPaper;
	
	public  Model() {
		 dao = new PortoDAO();
		idMap = new HashMap<Integer, Author>();
		dao.getAuthors(idMap);
		idMapPaper = new HashMap<Integer, Paper>();
		dao.getPapers(idMapPaper);
	}

	public List<Author> getAuthor() {
		List<Author> autori = new ArrayList<Author>(idMap.values());
		Collections.sort(autori);
		return autori;
	}

	public void doGrafo() {
		
		this.grafo = new SimpleGraph<Author, MyEdge>(MyEdge.class);
		Graphs.addAllVertices(grafo, dao.getAuthors(idMap));
		
		for(CoAutore ap: dao.getCoAuthors(idMap, idMapPaper)) {
			if(grafo.getEdge(ap.getAutore1(),ap.getAutore2())==null) {
				MyEdge myEdge = new MyEdge(ap.getAutore1(), ap.getAutore2(), ap.getPubbl());
				grafo.addEdge(ap.getAutore1(), ap.getAutore2(), myEdge);
			}
		}
		
	}
	
	public List<Author> getCoAutori(Author autore1){
		return Graphs.neighborListOf(grafo, autore1);
	}
	
	public List< Paper> trovaCamminoMinimo(Author a1, Author a2){
		
		DijkstraShortestPath<Author, MyEdge> dij= new DijkstraShortestPath<>(this.grafo);
		GraphPath<Author, MyEdge> path = dij.getPath(a1, a2);
		List<Paper> lista = new ArrayList<Paper>();
		
		for(MyEdge m : path.getEdgeList()) {
			lista.add(m.getPubbl());
		}
		return lista;
	}
}
