package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	
	/**  Módulo de Conexão *. */
	// Parâmetros
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/agenda?useTimezone=true&serverTimezone=UTC";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "*****";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
	// MÉTODO PARA ABRIR A CONEXÃO COM O BANCO
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Teste conexao.
	 */
	//------------------------ TESTANDO A CONEXÃO
	public void TesteConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	//------------------------  CREATE CONTATO
	public void inserirContato(JavaBeans contato) {
		String create = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";
		try {
			//ABRINDO CONEXÃO
			Connection con = conectar();
			//PREPARANDO A QUERY
			PreparedStatement pst = con.prepareStatement(create);
			
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getTelefone());
			pst.setString(3, contato.getEmail());
			//INSERINDO NO BANDO DE DAODS
			pst.executeUpdate();
			//FECHANDO A CONEXÃO
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	//------------------------ READ CONTATO
	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "SELECT * FROM contatos ORDER BY id";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				String id = rs.getString(1);
				String nome = rs.getString(2);
				String telefone = rs.getString(3);
				String email = rs.getString(4);
				contatos.add(new JavaBeans(id, nome, telefone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	//------------------------ UPDATE CONTATO
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// PEGANDO O USUÁRIO
	public void selecionarContato(JavaBeans contato) {
		String read2 = "SELECT * FROM contatos WHERE id=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getId());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				contato.setId(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setTelefone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Update usuario.
	 *
	 * @param contato the contato
	 */
	// FAZENDO O UPDATE
	public void updateUsuario(JavaBeans contato) {
		String read3 = "UPDATE contatos SET nome=?, telefone=?, email=? WHERE id=?";
		try {
			//ABRINDO CONEXÃO COM BANCO
			Connection con = conectar();
			//PREPARANDO A QUERY
			PreparedStatement pst = con.prepareStatement(read3);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getTelefone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getId());
			//EXECUTANDO A QUERY
			pst.executeUpdate();
			//FECHANDO A CONEXÃO
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Delete contato.
	 *
	 * @param contato the contato
	 */
	//------------------------ DELETE CONTATO
	public void deleteContato(JavaBeans contato) {
		String read4 = "DELETE FROM contatos WHERE id=?";
		try {
			//ABRINDO A CONEXÃO
			Connection con  = conectar();
			//PREPARANDO A QUERY
			PreparedStatement pst = con.prepareStatement(read4);
			pst.setString(1, contato.getId());
			//EXECUTAR A QUERY
			pst.execute();
			//FECHANDO A CONEXÃO
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}



