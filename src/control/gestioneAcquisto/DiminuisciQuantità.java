package control.gestioneAcquisto;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Libro;
import carrello.Carrello;
import model.OrdineManager;

/**
 * Servlet implementation class DimuisciQuantità
 */
@WebServlet("/DimuisciQuantità")
public class DiminuisciQuantità extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static OrdineManager manager= new OrdineManager();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiminuisciQuantità() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Carrello carrello= (Carrello) request.getSession().getAttribute("carrello");
		if(carrello==null) {
			carrello= new Carrello();
			request.getSession().setAttribute("carrello", carrello);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String isbn= request.getParameter("isbn");
		carrello.diminuisciQuantità(isbn);
		Libro lib= carrello.getLibro(isbn);
		
		request.getSession().setAttribute("carrello", carrello);
		String risposta= "{\"totaleCarrello\": \""+carrello.getTotale()+"\","
				+ "\"totaleProdotto\": \""+lib.getQuantitàSelezionata()*lib.getPrezzo()+"\","
						+ "\"quantità\": \""+lib.getQuantitàSelezionata()+"\"}";
		System.out.println(risposta);
		response.getWriter().write(risposta);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
