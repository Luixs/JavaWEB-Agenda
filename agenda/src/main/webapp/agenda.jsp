<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="model.JavaBeans" %>
<%@ page import="java.util.ArrayList" %>
<%
	@ SuppressWarnings ("Unchecked")
	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
%>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="utf-8">
		<title>Agenda de Contatos</title>
		<link rel="icon" href="images/calendar.png">
		<link rel="stylesheet" href="styles.css">
	</head>
	<body>
		<h1>Agenda de Contatos</h1>
		<a href="novoCont.html" class="btn1">Novo contato</a>
		<a href="report" class="btn2">Relatório</a>
		<table id="table">
			<thead>
				<tr>
					<th>ID</th>
					<th>NOME</th>
					<th>TELEFONE</th>
					<th>EMAIL</th>
					<th>OPÇÕES</th>
				</tr>
			</thead>
			<tbody>
				<%for (int i =0; i< lista.size();i++){ %>
					<tr>
						<td><%= lista.get(i).getId() %></td>
						<td><%= lista.get(i).getNome() %></td>
						<td><%= lista.get(i).getTelefone() %></td>
						<td><%= lista.get(i).getEmail() %></td>
						<td>
							<a href="select?id=<%=lista.get(i).getId() %>" class="btn1">Editar</a>
							<a href="javascript: confirmar(<%=lista.get(i).getId()%>)" class="btn2">Excluir</a>
							
						</td>
					</tr>
				<%} %>
			</tbody>
		</table>
		<script type="text/javascript" src="scripts/confirmador.js"></script>
	</body>
	
</html>