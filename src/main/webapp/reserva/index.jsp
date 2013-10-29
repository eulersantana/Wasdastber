<%-- 
    Document   : index.jsp
    Created on : 23/09/2013, 18:49:13
    Author     : massilva
--%>

<%@page import="java.util.Iterator"%>
<%@page import="br.ufba.roomsmanager.model.ReservaSala"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../header.jsp" %>
<body>
    <table class="reservas">
    <%
    ArrayList<ReservaSala> lista = (ArrayList)request.getAttribute("reservas");    
    if(lista != null) {
    %>
        <tr>
            <th colspan="10" class="titulo">
                <h3>Solicitação de reservas de sala</h3>
            </th>
        </tr>
        <tr>
            <th>Sala</th>
            <th>Data de Início</th>
            <th>Data de Término</th>
            <th>Horário de Início</th>
            <th>Horário de Início</th>
            <th>Responsável</th>
        </tr>
    <%
        Iterator it = lista.iterator();
        while(it.hasNext()){
            ReservaSala reserva = (ReservaSala)it.next();
    %>        
        <tr>
            <td><%=reserva.getSala().getNome()%></td>
            <td><%=reserva.getDataInicioPtBr()%></td>
            <td><%=reserva.getDataFimPtBr()%></td>
            <td><%=reserva.getHorarioInicioHM()%></td>
            <td><%=reserva.getHorarioTerminoHM()%></td>
            <td><%=reserva.getResponsavel()%></td>
        </tr>
    <%
        }
    }
    else
    {
    %>
        <tr>
            <td>Não há dados cadastrados.</td>
        </tr>
    <%
        }
    %>
    </table>
</body>
<%@include file="../footer.jsp" %>