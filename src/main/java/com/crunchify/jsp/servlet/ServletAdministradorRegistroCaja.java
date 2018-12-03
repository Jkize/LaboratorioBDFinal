/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import edu.co.sergio.mundo.vo.Caja;
import edu.co.sergio.mundo.dao.DAO_Caja;
import edu.co.sergio.mundo.dao.DAO_Supermercado;
import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
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
public class ServletAdministradorRegistroCaja extends HttpServlet {

    private DAO_Caja daoCaja;
    private DAO_Supermercado daoSuper;
    private Caja caja;

    @Override
    public void init() throws ServletException {
        try {
            this.daoCaja = new DAO_Caja();
            this.daoSuper = new DAO_Supermercado();
        } catch (SQLException ex) {
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
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
        RequestDispatcher rq = request.getRequestDispatcher("AdministradorRegistroCaja.jsp");
        List<Caja> inventarioC = null;
        String idSM = request.getParameter("idSM");
        String dat = "";
        try {
            inventarioC = daoCaja.getCajas(idSM);
            JSONObject ob = new JSONObject();
            JSONArray matr = new JSONArray();
            for (Caja inventario1 : inventarioC) {
                JSONArray fila = new JSONArray();
                fila.put(inventario1.getIdCaja());
                fila.put(inventario1.getMontoActual());
                matr.put(fila);
            }
            ob.put("cajas1", matr);
            dat = ob.toString();
        } catch (SQLException ex) {
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("cajas", dat);
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
        List<Caja> cajas = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        JSONObject ob = new JSONObject();

        String idSM = request.getParameter("idSM");

        if (request.getParameter("buscar") != null) {
            String idInv = request.getParameter("codigo");
            if (idInv.length() > 0) {
                try {

                    caja = daoCaja.buscar(idInv, idSM);

                    if (caja != null) {
                        ob.put("idCaja2", caja.getIdCaja());
                        ob.put("monto2", caja.getMontoActual());
                    } else {
                        ob.put("error", "No se encuentra caja");
                    }
                    cajas = daoCaja.getCajas(idSM);
                    JSONArray matr = new JSONArray();
                    for (Caja inventario1 : cajas) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdCaja());
                        fila.put(inventario1.getMontoActual());
                        matr.put(fila);
                    }
                    ob.put("cajas1", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"Tipo de dato incorrecto\"}");
                }
            } else {
                out.println("{ \"error\": \"Dato Id Vacio\"}");
            }

        }

        if (request.getParameter("actualizar") != null) {

            String idInv = request.getParameter("codigo"),
                    monto = request.getParameter("monto");
            if (idInv.length() > 0 && monto.length() > 0) {
                try {
                    caja = daoCaja.buscar(idInv, idSM);
                    caja.setMontoActual(Double.parseDouble(monto));
                    if (!daoCaja.actualizarMonto(caja, idSM)) {
                        ob.put("error", "No se encontró la caja");
                    } else {
                    }

                    cajas = daoCaja.getCajas(idSM);
                    JSONArray matr = new JSONArray();
                    for (Caja inventario1 : cajas) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdCaja());
                        fila.put(inventario1.getMontoActual());
                        matr.put(fila);
                    }
                    ob.put("cajas1", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"Tipo de dato incorrecto\"}");
                } catch (NullPointerException e) {
                    out.println("{ \"error\": \"La caja no existe\"}");
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }

        }

        if (request.getParameter("registrar") != null) {

            String codigo = request.getParameter("codigo"),
                    monto = request.getParameter("monto");
            if (codigo.length() > 0 && monto.length() > 0) {

                try {
                    caja = new Caja();
                    caja.setIdCaja((codigo));
                    caja.setMontoActual(Double.parseDouble(monto));
                    caja.setSuperMercado(daoSuper.buscar(idSM));
                    //REVISAR EXCEPCION 
                    System.out.println(caja.getIdCaja() + " " + caja.getMontoActual());
                    if (daoCaja.buscar(codigo, idSM) == null) {
                        if (!daoCaja.crear(caja)) {
                            ob.put("error", "No se pudo registrar, Id replicada");
                        }

                    } else {
                        ob.put("error", "No se pudo registrar, Id replicada");
                    }
                    cajas = daoCaja.getCajas(idSM);
                    JSONArray matr = new JSONArray();
                    for (Caja inventario1 : cajas) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdCaja());
                        fila.put(inventario1.getMontoActual());
                        matr.put(fila);
                    }
                    ob.put("cajas1", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException ex) {
                    out.println("{ \"error\": \"Tipo de dato incorrecto\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }

        }

        if (request.getParameter("eliminar") != null) {

            String idInv = request.getParameter("codigo");

            if (idInv.length() > 0) {
                try {

                    caja = daoCaja.buscar(idInv, idSM);
                    if (caja != null) {
                        if (!daoCaja.eliminar(caja.getIdCaja(), idSM)) {
                            ob.put("error", "La caja no se pudo eliminar");
                        }
                    } else {
                        ob.put("error", "No se pudo encontrar la caja");
                    }

                    cajas = daoCaja.getCajas(idSM);
                    JSONArray matr = new JSONArray();
                    for (Caja inventario1 : cajas) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getIdCaja());
                        fila.put(inventario1.getMontoActual());
                        matr.put(fila);
                    }
                    ob.put("cajas1", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
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
