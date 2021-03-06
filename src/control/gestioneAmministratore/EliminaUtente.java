package control.gestioneAmministratore;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AmministratoreManager;

/**
 * Servlet implementation class EliminaUtente
 */
@WebServlet("/EliminaUtente")
public class EliminaUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AmministratoreManager am= new AmministratoreManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EliminaUtente() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email= request.getParameter("email");

		try {
			am.eliminaUtente(email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(request.getSession().getAttribute("utenteCercato") != null) {
			request.getSession().removeAttribute("utenteCercato");
		}
		if(request.getSession().getAttribute("accountNonTrovato") != null) {
			request.getSession().removeAttribute("accountNonTrovato");
		}

		RequestDispatcher dispatcher= request.getRequestDispatcher("AmministratoreAccount.jsp");
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
