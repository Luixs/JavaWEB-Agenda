package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select","/update","/delete","/report"})
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();

	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// --FAZENDO O CONTROLLER
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		}else if (action.equals("/update")) {
			editarContato(request, response);
		}else if(action.equals("/delete")){
			deletarContato(request,response);
		}else if(action.equals("/report")){
			gerarRelatorio(request,response);
		}
		else {
			response.sendRedirect("Index.html");
		}
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// -------------------------- LISTAR TODOS OS CONTATOS
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// PEGANDO A LISTA DE CONTATOS DO DATABASE
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// ENCAMINHANDO A LISTA PARA A PAGINA DINÂMICA
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// ----------------------------ADICIONAR CONTATOS
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("nome"));
		// SETANDO AS VARIÁVEIS DO JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("telefone"));
		contato.setEmail(request.getParameter("email"));
		// INVOCANDO MÉTODO E PASSANDO O CONTATO
		dao.inserirContato(contato);
		// REDIRECIONANDO USUÁRIO PARA A AGENDA
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// ----------------------------LISTAR UM CONTATO PARA EDIÇÃO
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//PEGANDO O ID DO CONTATO
		String id = request.getParameter("id");
		// SETANDO A VARIÁVEL NO JavaBeans
		contato.setId(id);
		// Executando o método de seleção
		dao.selecionarContato(contato);
		//MANDANDO OS DADOS PARA OUTRA PAG
		request.setAttribute("id", contato.getId());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("telefone", contato.getTelefone());
		request.setAttribute("email", contato.getEmail());
		RequestDispatcher rd = request.getRequestDispatcher("editarContato.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// ----------------------------EDITAR UM CONTATO PARA EDIÇÃO
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//PEGANDO OS DADOS RECEBIDOS
		contato.setId(request.getParameter("id"));
		contato.setNome(request.getParameter("nome"));
		contato.setTelefone(request.getParameter("telefone"));
		contato.setEmail(request.getParameter("email"));
		//MANDANDO PARA A PAGINA RESPONSÁVEL PELO CONTATO COM O BANCO DE DADOS
		dao.updateUsuario(contato);
		//JOGANDO NA PAGINA PRINCIPAL
		response.sendRedirect("main");
	}
	
	/**
	 * Deletar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// ----------------------------EXCLUIR UM CONTATO
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//PEGANDO O ID QUE VEIO NO PATH
		String id = request.getParameter("id");
		System.out.println("Chegou o Id --> " + id);
		//MANDANDO O CONTATO PARA O DAO, PARA SE COMUNICAR COM O BANCO DE DADOS
		contato.setId(id);
		dao.deleteContato(contato);
		//DANDO UMA RESPOSTA PARA A PAGINA
		response.sendRedirect("main");
	}
	
	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// ----------------------------GERAR RELATÓRIO
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document doc = new Document();
		try {
			//TIPO DE CONTEUDO
			response.setContentType("application/pdf");
			//NOME DO DOC
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			//CRIANDO O DOC
			PdfWriter.getInstance(doc, response.getOutputStream());
			//ABRINDO O DOC PARA GERAR O CONTEUDO
			doc.open();
			doc.add(new Paragraph("Lista de contatos:"));
			doc.add(new Paragraph(" "));
			//CRIANDO A TABELA
			PdfPTable tabela = new PdfPTable(3);
			//PDF CABEÇALHO ESTÁTICO
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//PDF GERANDO OS CONTATOS DINÂMICOS
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i= 0; i< lista.size();i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getTelefone());
				tabela.addCell(lista.get(i).getEmail());
			}
			doc.add(tabela);
			doc.close();
		} catch (Exception e) {
			System.out.println(e);
			doc.close();
		}
	}
}


