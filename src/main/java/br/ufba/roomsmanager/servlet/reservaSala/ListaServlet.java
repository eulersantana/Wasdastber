/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.roomsmanager.servlet.reservaSala;

import br.ufba.roomsmanager.dao.Hibernate;
import br.ufba.roomsmanager.model.ReservaSala;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author massilva
 */
public class ListaServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        SessionFactory sf = Hibernate.getSessionFactory();
        Session session = sf.openSession();

        List<ReservaSala> reservas = (List<ReservaSala>) session.createQuery("FROM ReservaSala ORDER BY data_inicio DESC").list();
        
        request.setAttribute("reservas", reservas);

        RequestDispatcher view = getServletContext().getRequestDispatcher("/reserva/index.jsp");
        view.forward(request,response);	
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        RequestDispatcher view = getServletContext().getRequestDispatcher("/reserva/index.jsp");
        view.forward(request,response);	
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet para listar as reservas de salas.";
    }// </editor-fold>
}
