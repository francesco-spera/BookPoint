<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="bean.Utente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>BookPoint</title>
</head>
<body>
<%@ include file="headerAmministratore.jsp"%>
<% Utente utente=(Utente) request.getSession().getAttribute("utenteCercato") ;%>
<div class="container-cerca">
<div class="cerca">
  <form class=" search-container" action="ricercaAccount" >
    <input  type="text" id="search-bar" placeholder="Cerca account" name="email">
    <input class="btn btn-danger " type="submit" value="Cerca account">
  </form>
</div>
</div>

<%if(utente==null){
	
} else{%>
<div class="container-account">
<h3><%=utente.getNome()%> <%=utente.getCognome() %></h3>
<table class="table-bordered">
  
  
    <tr>
      <td class="td-bold">Email</td>
      <td class="td-serif"><%=utente.getEmail() %></td>
   
    </tr>
      <tr>
      <td class="td-bold">Tipo</td>
      <td class="td-serif"><%=utente.getTipo() %></td>
   
    </tr>
        <tr>
      <td><center><input class="btn btn-danger modificaTipo" type="submit" value="modifica" class=modificaTipo></center></td>
      <td>
      	<form action="eliminaUtente">
      		<input type=hidden value=<%=utente.getEmail()%> name=email>
      		<center><input class="btn btn-danger " type="submit" value="elimina"></center>
      	</form>
      </td>
   
    </tr>
  
</table>
</div>

<div class="formModificaTipo" >
	<form action="cambiaTipo">
		<input type="hidden" value=<%=utente.getEmail() %> name="email">
		<select name=tipo>
		<%if(utente.getTipo().equalsIgnoreCase("cliente")){ %>
		<option value=amministratore>amministratore</option>
		<option value=amministratoreOrdini>amministratoreOrdini</option>
		<%}else if(utente.getTipo().equalsIgnoreCase("amministratore")) {%>
		<option value=cliente>cliente</option>
		<option value=amministratoreOrdini>amministratoreOrdini</option>
		<%}else{ %>
		<option value=cliente>cliente</option>
		<option value=amministratore>amministratore</option>
		<%} %>
		</select>
		<input class="btn btn-danger" type=submit value=modifica>
	</form>
</div>
<%} 

request.getSession().removeAttribute("utenteCercato");
%>
<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="Amministratore.js"></script>


</body>
</html>