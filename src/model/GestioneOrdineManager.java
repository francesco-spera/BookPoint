package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import bean.Libro;
import bean.Ordine;
import connectionPool.DriverMaagerConnectionPool;

public class GestioneOrdineManager {

	/**
	 * 
	 * @param email l'email dell'utente di cui si deve visualizzare lo storico
	 * @return una collection vuota se l'utente non ha effettuato nessun ordine in precedenza, altrimenti una Collection con gli ordini effettuati
	 * @throws SQLException
	 */
	public Collection<Ordine> visualizzaStorico(String email) throws SQLException {
		Connection connection= DriverMaagerConnectionPool.getConnection();
		PreparedStatement preparedStatement= null;
		Collection<Ordine> ordini= new LinkedList<Ordine>();
		
		String selectQ= "SELECT * from ordine WHERE utente = ?";
		try {
			preparedStatement= connection.prepareStatement(selectQ);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				Ordine ordine= new Ordine();
				ordine.setIdOrdine(rs.getInt("numero"));
				ordine.setDataEffettuata(rs.getDate("dataOrdine"));
				ordine.setDataConsegna(rs.getDate("dataConsegna"));
				ordine.setOra(rs.getTime("oraConsegna"));
				ordine.setVia(rs.getString("via"));
				ordine.setNumCivico(rs.getInt("numeroCivico"));
				ordine.setCap(rs.getString("cap"));
				ordine.setCitt�(rs.getString("citt�"));
				ordine.setPrezzoTot(rs.getDouble("totale"));
				ordine.setNumCarta(rs.getString("numeroCarta"));
				ordine.setStato(rs.getString("stato"));
				
				
				ordine.setLibri(getLibri(connection, ordine));
				ordini.add(ordine);
				
			}
		}finally {
			try {
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				DriverMaagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return ordini;
	}

	/**
	 * 
	 * @param connection connessione al db
	 * @param ordine l'ordine di cui si devono prendere i libri acquistati
	 * @return una collezione vuota se non � stato trovato nessun libro acquistato, altrimenti una Collection contenente i libri relativi all'ordine
	 * @throws SQLException
	 */
	private Collection<Libro> getLibri(Connection connection, Ordine ordine) throws SQLException {
		PreparedStatement pStatement= null;
		Collection<Libro> libriAcquistati= new LinkedList<Libro>();
		
		String selectQ = "SELECT * FROM libriacquistati WHERE ordine = ?";
		
		pStatement= connection.prepareStatement(selectQ);
		pStatement.setInt(1, ordine.getIdOrdine());
		ResultSet rs= pStatement.executeQuery();
			
		while(rs.next()) {
			Libro libro = new Libro();
			libro.setTitolo(rs.getString("titolo"));
			libro.setPrezzo(rs.getDouble("prezzoAcquisto"));
			libro.setQuantit�(rs.getInt("quantit�"));
				
			libriAcquistati.add(libro);
		}
		
		
		return libriAcquistati;
		
	}

}