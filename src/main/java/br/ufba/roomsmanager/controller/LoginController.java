package br.ufba.roomsmanager.controller;

import java.io.*;

import javax.servlet.http.*;
import javax.swing.JOptionPane;

import br.ufba.roomsmanager.model.Login;

public class LoginController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	
        @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
            PrintWriter out = response.getWriter();

            String user = request.getParameter("username");
            String senha = request.getParameter("senha");

            Login login = new Login(user,senha);
            session = request.getSession(true);

            if(login.validate()){
                session.setAttribute("logado", user);
                out.println("<script>location.href='"+request.getRequestURL()+"/../index.do';</script>");
                return;
            }
            else{
                JOptionPane.showMessageDialog(null, "Credenciais incorretas.");
            }

            out.println("<script>location.href='"+request.getRequestURL()+"/../index.jsp';</script>");
	}
	
        @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
            PrintWriter out = response.getWriter();
            session = request.getSession();
            int conf = JOptionPane.showConfirmDialog(null,"Deseja sair do sistema ?","Controle de Sala",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(conf == 0){
                session.invalidate();
                out.println("<script>location.href='"+request.getRequestURL()+"/../index.jsp';</script>");
            }
            else{
                out.println("<script>window.history.back()</script>");
            }
		
	}
	
}