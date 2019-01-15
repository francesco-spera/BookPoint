package control.gestioneAmministratore;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DataManager;

/**
 * Servlet implementation class EliminaRecensione
 */
@WebServlet("/EliminaRecensione")
public class EliminaRecensione extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataManager dm= new DataManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaRecensione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idRecensione= Integer.parseInt(request.getParameter("idRecensione"));
		
		try {
			dm.eliminaRecensione(idRecensione);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher= request.getRequestDispatcher("AmministratoreVisualizzaLibro.jsp");
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
