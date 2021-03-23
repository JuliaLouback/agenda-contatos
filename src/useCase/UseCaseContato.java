package useCase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import entity.Contato;

public class UseCaseContato {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public UseCaseContato(HttpServletRequest request, HttpServletResponse response) {
		
		this.request = request;
		this.response = response;
	}
	
	public void execute() throws IOException, ServletException {
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		
		Contato contato;
			
		switch(request.getParameter("acao")) {
			case "Cadastrar":
				contato = this.createContato();
				this.saveSession(contato);
				request.setAttribute("resultado", "HTML");
				
				break;
			case "Editar":
			
				int id_contato = Integer.parseInt(request.getParameter("id_contato"));
				request.setAttribute("contato", new Contato().encontrarContatos(id_contato, listarContato()));
				request.setAttribute("resultado", "HTML");
				
				break;
			case "Alterar":
				
				this.editContato();
				request.setAttribute("resultado", "HTML");
				request.setAttribute("contato", null);

				break;
			case "Excluir":
				this.deleteContato();
				request.setAttribute("resultado", "HTML");
				request.setAttribute("contato", null);
				break;
			case "Limpar":
				request.setAttribute("resultado", "HTML");
				request.setAttribute("contato", null);
				break;
			case "Gerar Tabela HTML":
				request.setAttribute("resultado", "HTML");
				break;
			case "Gerar JSON":
				request.setAttribute("resultado", "JSON");
				break;
			default:
				System.out.println("ee");
		}
	
		rd.forward(request, response);
		
	}
	
	
	private Contato createContato() {

		Contato contato = new Contato();
		
		contato.setNome(request.getParameter("nome"));
		contato.setEmail(request.getParameter("email"));
		contato.setFone(request.getParameter("telefone"));
		
		
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null) {
			List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
			
			if(listaContato == null) {
				contato.setId_contato(1);
			} else {
				Contato contatoId = listaContato.get(listaContato.size() -1);
				
				contato.setId_contato(contatoId.getId_contato() +1);
			}
			
		}
		
	    return contato;
	}
	
	private void editContato() {

		Contato contato = new Contato();
		
		contato.setNome(request.getParameter("nome"));
		contato.setEmail(request.getParameter("email"));
		contato.setFone(request.getParameter("telefone"));
		contato.setId_contato(Integer.parseInt(request.getParameter("id_contato")));
				
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null) {
			List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
					
            listaContato.remove(new Contato().encontrarContatos(contato.getId_contato(), listaContato));
            listaContato.add(contato);
		    
			sessao.setAttribute("listaContato", listaContato);

			try {
				
				JSONArray jsonArray = new JSONArray(listaContato);
				
			    sessao.setAttribute("listaContatoJSON", jsonArray);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	private void deleteContato() {
				
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null) {
			List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
					
			int id = Integer.parseInt(request.getParameter("id_contato"));
            listaContato.remove(new Contato().encontrarContatos(id, listaContato));
		    
			sessao.setAttribute("listaContato", listaContato);

			try {
				
				JSONArray jsonArray = new JSONArray(listaContato);
				
			    sessao.setAttribute("listaContatoJSON", jsonArray);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	 
	private void saveSession(Contato contato) {
			
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null) {
			List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
			
			if(listaContato == null) {
				listaContato = new ArrayList<Contato>();
			}
			
			listaContato.add(contato);
			sessao.setAttribute("listaContato", listaContato);
			
			try {
				
				JSONArray jsonArray = new JSONArray(listaContato);
				
			    sessao.setAttribute("listaContatoJSON", jsonArray);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
		
		
	private List<Contato> listarContato(){
		HttpSession sessao = request.getSession(false);
		
		if(sessao != null) {
			List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
			
			if(listaContato == null) {
				listaContato = new ArrayList<Contato>();
			}
			
			return listaContato;
		}
		
		return null;
	}
}
	
