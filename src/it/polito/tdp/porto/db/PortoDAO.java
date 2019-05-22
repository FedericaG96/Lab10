package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAutore;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAuthors(Map<Integer, Author> idMap) {
		final String sql = "SELECT * FROM author ";
		List<Author> autori = new LinkedList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(idMap.get(rs.getInt("id"))==null) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				idMap.put(autore.getId(), autore);
				autori.add(autore);
				}
				else
					autori.add(idMap.get(rs.getInt("id")));
				
			}
			conn.close();
			return autori;
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Paper> getPapers(Map<Integer, Paper> idMapPaper) {
		final String sql = "SELECT * FROM paper ";
		List<Paper> papers = new LinkedList<Paper>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(idMapPaper.get(rs.getInt("eprintid"))==null) {
					Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
							rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				idMapPaper.put(paper.getEprintid(), paper);
				papers.add(paper);
				}
				else
					papers.add(idMapPaper.get(rs.getInt("eprintid")));
				
			}
			conn.close();
			return papers;
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<CoAutore> getCoAuthors(Map<Integer, Author> idMap, Map<Integer, Paper> idMapPaper) {
		final String sql = "SELECT c1.authorid AS id1, c2.authorid AS id2, c1.eprintid AS idPrint " + 
				"FROM creator AS c1, creator AS c2 " + 
				"WHERE c1.authorid!= c2.authorId " + 
				"AND c1.eprintid = c2.eprintid ";
		List<CoAutore> coAutori = new LinkedList<CoAutore>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
		
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author autore1 = idMap.get(rs.getInt("id1"));
				Author autore2 = idMap.get(rs.getInt("id2"));
				Paper pubbl = idMapPaper.get(rs.getInt("idPrint"));
				
				CoAutore co = new CoAutore(autore1, autore2, pubbl);
				
			
				coAutori.add(co);
				}
				
			conn.close();
			return coAutori;
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}