/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import edu.co.sergio.mundo.dao.DAO_Empleado;
import edu.co.sergio.mundo.vo.Empleado;
import java.net.URISyntaxException;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Sebastian
 */
public class ServletIndex extends HttpServlet {

    private DAO_Empleado daoEmpleado;

    public ServletIndex() throws SQLException, ClassNotFoundException, URISyntaxException {
        daoEmpleado = new DAO_Empleado();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rq = request.getRequestDispatcher("index.jsp");
        HttpSession sesionEmpleado = request.getSession();
        Empleado _sesionEmpleado;
        if (request.getParameter("idEmpleado") != null) {
            if (request.getParameter("Admin") != null) {
                _sesionEmpleado = (Empleado) sesionEmpleado.getAttribute("Admin");
            } else {
                _sesionEmpleado = (Empleado) sesionEmpleado.getAttribute("Operario");
            }
            if (_sesionEmpleado != null) {
                System.out.println(_sesionEmpleado.getIdPersona());
                if (_sesionEmpleado.getIdPersona() == Long.parseLong(request.getParameter("idEmpleado"))) {
                    sesionEmpleado.invalidate();
                }
            }
        }
        if (request.getParameter("error") != null) {
            request.setAttribute("error", "si");
        }
        rq.forward(request, response);
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPersona = request.getParameter("usuario");
        String pass = request.getParameter("pass");
        String error=null;
        try {
            if (idPersona.length() > 0 && pass.length() > 0) {
                Empleado a = new Empleado();
                a.setIdPersona(Long.parseLong(idPersona));
                a.setContrasena(pass);

                a = daoEmpleado.usuarioValido(a);
                HttpSession sesionEmpleado = request.getSession();
                Empleado _sesionEmpleado = (Empleado) sesionEmpleado.getAttribute("Empleado");

                if (_sesionEmpleado == null) {
                    //El usuario no a creado la sesion
                    if (a != null) {
                        if (a.getCargo().equals("AD")) {
                            sesionEmpleado.setAttribute("Admin", a);
                            sesionEmpleado.setMaxInactiveInterval(60 * 2);
                            response.sendRedirect("ServletAdministradorInicio?idEmpleado=" + String.valueOf(a.getIdPersona()) + "&idSM=" + a.getSupermercado().getIdSM());

                            //REDIRIGIMOS AL ADMINISTRADOR
                        } else {
                            sesionEmpleado.setAttribute("Operario", a);
                            sesionEmpleado.setMaxInactiveInterval(60 * 2);
                            response.sendRedirect("ServletEmpleado?idEmpleado=" + String.valueOf(a.getIdPersona()) + "&idSM=" + a.getSupermercado().getIdSM() + "&Monto=" + a.getCaja().getIdCaja());
                            //REDIRIGIMOS AL EMPLEADO 
                        }

                    } else {
                        //VOTAMOS MENSAJE DE ERROR
                         response.sendRedirect("ServletIndex?error=Si");
                           
                    }

                } else {
                    if (a != null) {
                        if (a.getCargo().equals("AD")) {
                            sesionEmpleado.setAttribute("Admin", a);
                            sesionEmpleado.setMaxInactiveInterval(60 * 2);
                            response.sendRedirect("ServletAdministradorInicio?idEmpleado=" + String.valueOf(a.getIdPersona()) + "&idSM=" + a.getSupermercado().getIdSM());

                            //REDIRIGIMOS AL ADMINISTRADOR
                        } else {
                            sesionEmpleado.setAttribute("Operario", a);
                            sesionEmpleado.setMaxInactiveInterval(60 * 2);
                            response.sendRedirect("ServletEmpleado?idEmpleado=" + String.valueOf(a.getIdPersona()) + "&idSM=" + a.getSupermercado().getIdSM() + "&Monto=" + a.getCaja().getIdCaja());
                            //REDIRIGIMOS AL EMPLEADO 
                        }

                    } else {
                        
                         response.sendRedirect("ServletIndex?error=Si");
                    }
                }
            } else {
                
                         response.sendRedirect("ServletIndex?error=Si");
            }
        } catch (ClassNotFoundException ex) {
            response.sendRedirect("ServletIndex?error=Si");
            Logger.getLogger(ServletIndex.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException ex) {
            response.sendRedirect("ServletIndex?error=Si");
            Logger.getLogger(ServletIndex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletIndex.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
