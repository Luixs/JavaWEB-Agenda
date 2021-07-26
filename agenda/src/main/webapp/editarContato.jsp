<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Editar Contato</title>
		<link rel="icon" href="images/calendar.png">
		<link rel="stylesheet" href="styles.css">
	</head>
	<body>
		<h1>Editar contato</h1>
		<form name="frmContato" action="update">
			<table>
				<tr>
					<td><input type="text" name="id" id="campo3" readonly value="${id}"></td>
				</tr>
				<tr>
					<td><input type="text" name="nome" class="campo1" value="<%out.print(request.getAttribute("nome")); %>"></td>
				</tr>
				<tr>
					<td><input type="text" name="telefone" class="campo2" value="${telefone}"></td>
				</tr>
				<tr>
					<td><input type="text" name="email" class="campo1" value="${email}"></td>
				</tr>
			</table>
			<input type="button" value="Salvar" class="btn1" onclick="validar()">
		</form>
		<script type="text/javascript" src="scripts/validador.js"></script>
	</body>
</html>