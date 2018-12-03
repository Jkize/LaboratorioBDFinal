/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import edu.co.sergio.mundo.dao.DAO_Caja;
import edu.co.sergio.mundo.dao.DAO_Empleado;
import edu.co.sergio.mundo.dao.DAO_Supermercado;
import edu.co.sergio.mundo.vo.Empleado;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author PC02
 */
public class ServletAdministradorRegistroEmpleado extends HttpServlet {

    private DAO_Empleado daoEmpleado;
    private Empleado empleado;
    private DAO_Caja daoCaja;
    private DAO_Supermercado daoSup;

    @Override
    public void init() throws ServletException {
        try {
            this.daoEmpleado = new DAO_Empleado();
            this.daoSup = new DAO_Supermercado();
            this.daoCaja = new DAO_Caja();
        } catch (SQLException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        RequestDispatcher rq = request.getRequestDispatcher("AdministradorRegistroEmpleado.jsp");
        List<String> cajas = new ArrayList();
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String idSM = request.getParameter("idSM");
        String dat = "";
        try {
            DAO_Caja caja = new DAO_Caja();
            cajas = caja.disponibles(idSM);
        } catch (SQLException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Empleado> inventarioC = null;
        try {
            inventarioC = daoEmpleado.getEmpleados(idSM);
            JSONObject ob = new JSONObject();
            JSONArray matr = new JSONArray();
            for (Empleado inventario1 : inventarioC) {
                JSONArray fila = new JSONArray();
                fila.put(inventario1.getIdPersona());
                fila.put(inventario1.getNombre());
                fila.put(inventario1.getContrasena());
                fila.put(inventario1.getCaja().getIdCaja());
                matr.put(fila);
            }
            ob.put("emple", matr);
            dat = ob.toString();
        } catch (SQLException ex) {
            out.println("{ \"error\": \"Error SQL\"}");
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
             out.println("{ \"error\": \"Json\"}");
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("empleados", dat);

        request.setAttribute("cajas", cajas);
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
        List<Empleado> inventarioC = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        JSONObject ob = new JSONObject();

        String idSM = request.getParameter("idSM");

        if (request.getParameter("buscar") != null) {
            String idInv = request.getParameter("codigo");
            if (idInv.length() > 0) {
                try {
                    System.out.println("ENTRO " + idInv);
                    empleado = daoEmpleado.Buscar(Long.parseLong(idInv), idSM);
                    System.out.println(empleado.getIdPersona());
                    if (empleado != null) {
                        ob.put("cedula2", empleado.getIdPersona());
                        ob.put("nombre2", empleado.getNombre());
                        ob.put("contra2", empleado.getContrasena());
                        ob.put("cargo2", empleado.getCargo());
                        ob.put("caja2", empleado.getCaja().getIdCaja());
                    } else {
                        ob.put("error", "No se encuentra empleado");
                    }
                    inventarioC = daoEmpleado.getEmpleados(idSM);
                    JSONArray matr = new JSONArray();
                    for (Empleado inventario1 : inventarioC) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdPersona());
                        fila.put(inventario1.getNombre());
                        fila.put(inventario1.getContrasena());
                        fila.put(inventario1.getCaja().getIdCaja());
                        matr.put(fila);
                    }
                    ob.put("emple", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"El tipo de dato es incorrecto\"}");
                }
            } else {
                out.println("{ \"error\": \"Dato Id Vacio\"}");
            }

        }

        if (request.getParameter("actualizar") != null) {

            String idInv = request.getParameter("codigo"),
                    nombre = request.getParameter("nombre"),
                    contra = request.getParameter("contra"),
                    cargo = request.getParameter("cargo"),
                    cajaA = request.getParameter("cajaActual"),
                    caja = request.getParameter("caja");
            if (idInv.length() > 0 && nombre.length() > 0 && contra.length() > 0 && cargo.length() > 0 && cajaA.length() > 0) {
                try {
                    empleado = daoEmpleado.Buscar(Long.parseLong(idInv), idSM);
                    empleado.setNombre(nombre);
                    empleado.setContrasena(contra);
                    empleado.setCargo(cargo);

                    if (!daoEmpleado.actualizar(empleado, idSM)) {
                        ob.put("error", "No se encontró el empleado");
                    }

                    inventarioC = daoEmpleado.getEmpleados(idSM);
                    JSONArray matr = new JSONArray();
                    for (Empleado inventario1 : inventarioC) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdPersona());
                        fila.put(inventario1.getNombre());
                        fila.put(inventario1.getContrasena());
                        fila.put(inventario1.getCaja().getIdCaja());
                        matr.put(fila);
                    }
                    ob.put("emple", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"El tipo de dato es incorrecto\"}");
                } catch (NullPointerException e) {
                    out.println("{ \"error\": \"El empleado no existe\"}");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (idInv.length() > 0 && nombre.length() > 0 && contra.length() > 0 && cargo.length() > 0 && caja.length() > 0) {
                try {
                    empleado = daoEmpleado.Buscar(Long.parseLong(idInv), idSM);
                    empleado.setNombre(nombre);
                    empleado.setContrasena(contra);
                    empleado.setCargo(cargo);
                    empleado.setCaja(daoCaja.buscar(caja, idSM));

                    if (!daoEmpleado.actualizar(empleado, idSM)) {
                        ob.put("error", "No se encontró el empleado");
                    }

                    inventarioC = daoEmpleado.getEmpleados(idSM);
                    JSONArray matr = new JSONArray();
                    for (Empleado inventario1 : inventarioC) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdPersona());
                        fila.put(inventario1.getNombre());
                        fila.put(inventario1.getContrasena());
                        fila.put(inventario1.getCaja().getIdCaja());
                        matr.put(fila);
                    }
                    ob.put("emple", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"El tipo de dato es incorrecto\"}");
                } catch (NullPointerException e) {
                    out.println("{ \"error\": \"El empleado no existe\"}");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }

        }

        if (request.getParameter("registrar") != null) {

            String idInv = request.getParameter("codigo"),
                    nombre = request.getParameter("nombre"),
                    contra = request.getParameter("contra"),
                    cargo = request.getParameter("cargo"),
                    caja = request.getParameter("caja");
            
            if (idInv.length() > 0 && nombre.length() > 0 && contra.length() > 0 && cargo.length() > 0 && caja.length() > 0) {

                try {
                    empleado = new Empleado();
                    empleado.setIdPersona(Long.parseLong(idInv));
                    empleado.setNombre(nombre);
                    empleado.setContrasena(contra);
                    empleado.setCaja(daoCaja.buscar(caja, idSM));
                    empleado.setSupermercado(daoSup.buscar(idSM));
                    empleado.setCargo(cargo);
                    
                    if (daoEmpleado.Buscar(Long.parseLong(idInv), idSM) == null) {
                        if (!daoEmpleado.crear(empleado)) {
                            ob.put("error", "No se pudo registrar, Id replicada");
                        }

                    } else {
                        ob.put("error", "No se pudo registrar, Id replicada");
                    }
                    inventarioC = daoEmpleado.getEmpleados(idSM);
                    JSONArray matr = new JSONArray();
                    for (Empleado inventario1 : inventarioC) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdPersona());
                        fila.put(inventario1.getNombre());
                        fila.put(inventario1.getContrasena());
                        fila.put(inventario1.getCaja().getIdCaja());
                        matr.put(fila);
                    }
                    ob.put("emple", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                     out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException ex) {
                    out.println("{ \"error\": \"Tipo de dato incorrecto\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    out.println("{ \"error\": \"No se encuentra la clase\"}");
                    Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }

        }

        if (request.getParameter("eliminar") != null) {

            String idInv = request.getParameter("codigo");

            if (idInv.length() > 0) {
                try {

                    empleado = daoEmpleado.Buscar(Long.parseLong(idInv), idSM);
                    if (empleado != null) {
                        if (!daoEmpleado.eliminar(Long.parseLong(idInv), idSM)) {
                            ob.put("error", "El empleado no se pudo eliminar");
                        }
                    } else {
                        ob.put("error", "No se pudo encontrar el empleado");
                    }
                    inventarioC = daoEmpleado.getEmpleados(idSM);
                    JSONArray matr = new JSONArray();
                    for (Empleado inventario1 : inventarioC) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdPersona());
                        fila.put(inventario1.getNombre());
                        fila.put(inventario1.getContrasena());
                        fila.put(inventario1.getCaja().getIdCaja());
                        matr.put(fila);
                    }
                    ob.put("emple", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                   out.println("{ \"error\": \"Error SQL\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                     out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                     out.println("{ \"error\": \"No se encuentra la clase\"}");
                    Logger.getLogger(ServletAdministradorRegistroEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }
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
