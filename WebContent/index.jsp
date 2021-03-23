<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List,java.util.ArrayList,org.json.JSONArray, entity.Contato" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="UTF-8">
	<title>Agenda de Contatos</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
	<link rel="icon" href="https://www.flaticon.com/svg/vstatic/svg/724/724664.svg?token=exp=1616465578~hmac=dde0389a6dfa132a78eba6fa7960ab1c">
</head>
<body>
	<%
		HttpSession sessao = request.getSession(false);
		String resultado = (String) request.getAttribute("resultado");
		Contato contatos;
		
		if((Contato) request.getAttribute("contato") == null){
			contatos = new Contato();
			
			contatos.setNome("");
			contatos.setFone("");
			contatos.setEmail("");
			contatos.setId_contato(0);
		} else {
			contatos = (Contato) request.getAttribute("contato");
		}
		
	%>
	<nav class="navbar navbar-light bg-primary bg-gradient">
	  <div class="container-fluid">
	    <a class="navbar-brand text-light" href="#">Agenda de Contatos</a>
	  </div>
	</nav>
	<div class="container-fluid">
		<div class="row p-4">
						
			<div class="col-4">
				<h3 class="text-primary">Cadastro de Contatos</h3>
			
				<form name="formContato" action="Controller" method="POST" class="mt-4">
					<input type="hidden" name="id_contato" value="<%= contatos.getId_contato() %>">
					<div class="mb-3">
					  <label for="nome" class="form-label">Nome</label>				
					  <input type="text" class="form-control" id="nome" name="nome" value="<%= contatos.getNome() %>">
					</div>
					<div class="mb-3">
					  <label for="telefone" class="form-label">Telefone</label>
					  <input type="tel" class="form-control" id="telefone" name="telefone" value="<%= contatos.getFone() %>">
					</div>
					<div class="mb-3">
					  <label for="email" class="form-label">E-mail</label>
					  <input type="email" class="form-control" id="email" name="email" value="<%= contatos.getEmail() %>">
					</div>
					<div class="d-grid gap-2 d-flex">
						<% if(contatos.getId_contato() == 0){ %>
							<input type="submit" class="btn btn-primary" name="acao" value="Cadastrar">
						<%} else { %>
							<input type="submit" class="btn btn-primary" name="acao" value="Alterar">
						<%} %>
						<input type="submit" class="btn btn-success" name="acao" value="Limpar">
					</div>
				</form>
				
				<div class="d-grid gap-2 d-flex justify-content-center mt-3 mb-4">
					<form action="Controller" method="POST" class="mt-4">
						<input type="submit" class="btn btn-outline-success" name="acao" value="Gerar Tabela HTML">
						<input type="submit" class="btn btn-outline-success" name="acao" value="Gerar JSON">
					</form>
				</div>
			</div>
				
			<div class="col-8">
				<%
					if (resultado == "HTML"){		
				%>
				
						<table class="table table-bordered table-hover ms-3">
							<thead class="text-primary">
								<th>Nome</th>
								<th>Telefone</th>
								<th>E-mail</th>
								<th>Editar</th>
								<th>Excluir</th>
							</thead>
							<tbody>
				
				<%		
						String lista = "";						
						//sessao.removeAttribute("listaContato");
	
						if(sessao != null) {
							List<Contato> listaContato = (List<Contato>) sessao.getAttribute("listaContato");
							
							if(listaContato != null) {
								for(Contato contato:listaContato){
				%>	
								<tr>
									<td><%= contato.getNome() %></td>
									<td><%= contato.getFone() %></td>
									<td><%= contato.getEmail() %></td>
									<td>
										<form name="formEdit" action="Controller" method="POST">
											<input type="hidden" value="<%= contato.getId_contato() %>" name="id_contato" class="d-none">
											<input type="submit" class="btn btn-primary" name="acao" value="Editar">
										</form>
									<td>
										<form name="formDelete" action="Controller" method="POST">
											<input type="hidden" value="<%= contato.getId_contato() %>" name="id_contato" class="d-none">
											<input type="submit" class="btn btn-danger" name="acao" value="Excluir">
										</form>
									</td>
								</tr>
				
				<%	
									}
								}	
							}
						}
				%>
				
				
				<%
					if (resultado == "JSON"){		
						if(sessao != null) {
							JSONArray jsonArray  = (JSONArray) sessao.getAttribute("listaContatoJSON");
							
							if(jsonArray != null) {
				%>
								<div class="card">
								  <div class="card-header">
								    JSON
								  </div>
								  <div class="card-body">
								    <blockquote class="blockquote mb-0">
								      <p><%= jsonArray %></p>
								    </blockquote>
								  </div>
								</div>
				<%			
							}
						}
					}
				%>
			</div>
		</div>
	</div>
</body>
</html>