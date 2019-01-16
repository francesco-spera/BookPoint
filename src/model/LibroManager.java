package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import bean.Autore;
import bean.Libro;
import bean.Recensione;
import connectionPool.DriverMaagerConnectionPool;

public class LibroManager {
	
	private static final String TABLE_NAME= "libro";

	/**
	 * 
	 * @param testo
	 * @param categoria
	 * @throws SQLException 
	 */
	public Collection<Libro> ricerca(String testo, String categoria) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Libro> libri = new LinkedList<Libro>();

		String selectSQL = "SELECT * FROM libro JOIN scrive WHERE categoria = ? AND "
				+ "((titolo = ? OR autore = ?))";


		try {
			connection = DriverMaagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			preparedStatement.setString(1,categoria);
			preparedStatement.setString(2, testo);
			preparedStatement.setString(3, testo);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Libro bean = new Libro();

				bean.setIsbn(rs.getString("isbn"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setTrama(rs.getString("trama"));
				bean.setFoto(rs.getString("foto"));
				bean.setCasaEditrice(rs.getString("casaEditrice"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setQuantit�(rs.getInt("quantit�"));
				bean.setCategoria(rs.getString("categoria"));
				bean.setCopieVendute(rs.getInt("copieVendute"));
				
				bean.setRecensioni(getRecensioni(connection, bean.getIsbn()));
				bean.setAutori(getAutori(connection, bean.getIsbn()));
				
				libri.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverMaagerConnectionPool.releaseConnection(connection);
			}
		}
		return libri;
	}

	public Collection<Libro> getLibriPi�Venduti() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Libro> libri = new LinkedList<Libro>();

		String selectSQL = "SELECT * FROM " + TABLE_NAME + " JOIN scrive ORDER BY copieVendute desc";

		final int NUMERO_LIBRI=8;//Numero di libri che si vuole mostrare
		try {
			connection = DriverMaagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			int i=0;
			while (rs.next() && i<NUMERO_LIBRI) {
				Libro bean = new Libro();

				bean.setIsbn(rs.getString("isbn"));
				bean.setTitolo(rs.getString("titolo"));
				bean.setTrama(rs.getString("trama"));
				bean.setFoto(rs.getString("foto"));
				bean.setCasaEditrice(rs.getString("casaEditrice"));
				bean.setPrezzo(rs.getDouble("prezzo"));
				bean.setQuantit�(rs.getInt("quantit�"));
				bean.setCategoria(rs.getString("categoria"));
				bean.setCopieVendute(rs.getInt("copieVendute"));
				
				bean.setRecensioni(getRecensioni(connection, bean.getIsbn()));
				bean.setAutori(getAutori(connection, bean.getIsbn()));
				
				libri.add(bean);
				i++;
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverMaagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return libri;
	}
	
	
	public Collection<Libro> getLibriInEvidenza(){
		return null;
	}

	public Libro visualizzaLibro(String isbn) throws SQLException {
		Connection connection= DriverMaagerConnectionPool.getConnection();
		PreparedStatement pStatement= null;
		Libro libro=null;
		
		String selectQ= "SELECT * FROM libro WHERE isbn= ?";
		
		try {
			pStatement= connection.prepareStatement(selectQ);
			pStatement.setString(1, isbn);
			ResultSet rs= pStatement.executeQuery();
			while(rs.next()){
				libro.setIsbn(isbn);
				libro.setPrezzo(rs.getDouble("prezzo"));
				libro.setTitolo(rs.getString("titolo"));
				libro.setTrama(rs.getString("trama"));
				libro.setFoto(rs.getString("foto"));
				libro.setCasaEditrice(rs.getString("casaEditrice"));
				libro.setQuantit�(rs.getInt("quantit�"));
				libro.setCategoria(rs.getString("categoria"));
				libro.setCopieVendute(rs.getInt("copieVendute"));
				
				libro.setRecensioni(getRecensioni(connection, isbn));
				libro.setAutori(getAutori(connection, isbn));
			}
		} finally {
			try {
				if (pStatement != null)
					pStatement.close();
			} finally {
				DriverMaagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return libro;
	}
	
	private ArrayList<Recensione> getRecensioni(Connection connection, String isbn) throws SQLException {
		PreparedStatement pStatement=null;
		ArrayList<Recensione> recensioni= new ArrayList<Recensione>();
		
		String selectQ= "SELECT * FROM (libro "
				+ "INNER JOIN recensione ON (libro.isbn = recensione.libro)) "
				+ "WHERE libro.isbn = ?";
		
		pStatement= connection.prepareStatement(selectQ);
		pStatement.setString(1, isbn);
		ResultSet rs = pStatement.executeQuery();
		while(rs.next()) {
			recensioni.add(new Recensione(rs.getString("testo"), isbn, rs.getString("email")));
		}
		return recensioni;
	}

	//PER GENNARO
	private ArrayList<Autore> getAutori(Connection connection, String isbn) throws SQLException {
		PreparedStatement pStatement=null;
		ArrayList<Autore> autori= new ArrayList<Autore>();
		
		String selectQ= "SELECT autore FROM (libro "
				+ "INNER JOIN scrive ON (libro.isbn = scrive.libro)) "
				+ "WHERE libro.isbn = ?";
		
		pStatement= connection.prepareStatement(selectQ);
		pStatement.setString(1, isbn);
		ResultSet rs = pStatement.executeQuery();
		while(rs.next()) {
			autori.add(new Autore(rs.getString("autore")));
		}
		
		return autori;
	}
}