package control.gestioneInterazioneLibro;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Recensione;
import bean.Utente;
import model.InterazioneLibroManager;

/**
 * Servlet implementation class AggiungiRecensione
 */
@WebServlet("/AggiungiRecensione")
public class AggiungiRecensione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private InterazioneLibroManager manager = new InterazioneLibroManager();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiRecensione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String testo= request.getParameter("testo");
		String isbn= request.getParameter("isbn");
		Utente utente= (Utente) request.getSession().getAttribute("utente");
		String email= utente.getEmail();
		
		System.out.println(testo +" " + isbn + " " + email);
		
		Recensione recensione= new Recensione(0, testo, isbn, email);
		try {
			manager.aggiungiRecensione(recensione);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher dispatcher= request.getRequestDispatcher("visualizzaLibro?isbn="+isbn);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
