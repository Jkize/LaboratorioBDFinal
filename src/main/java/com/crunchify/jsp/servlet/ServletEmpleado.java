/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.co.sergio.mundo.vo.Empleado;
import edu.co.sergio.mundo.vo.Caja;
import edu.co.sergio.mundo.vo.Cliente;
import edu.co.sergio.mundo.dao.DAO_Caja;
import edu.co.sergio.mundo.dao.DAO_Cliente;
import edu.co.sergio.mundo.dao.DAO_Detallado;
import edu.co.sergio.mundo.dao.DAO_Empleado;
import edu.co.sergio.mundo.dao.DAO_Inventario;
import edu.co.sergio.mundo.dao.DAO_Venta;
import edu.co.sergio.mundo.vo.Caja;
import edu.co.sergio.mundo.vo.Cliente;
import edu.co.sergio.mundo.vo.Empleado;
import edu.co.sergio.mundo.vo.Inventario;
import edu.co.sergio.mundo.vo.Producto;
import edu.co.sergio.mundo.vo.Supermercado;
import edu.co.sergio.mundo.vo.Venta;
import java.net.URISyntaxException;

/**
 *
 * @author Jhoan Saavedra
 */
public class ServletEmpleado extends HttpServlet {

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

        RequestDispatcher rq = request.getRequestDispatcher("Empleado.jsp");
        if (request.getParameter("idEmpleado") != null && request.getParameter("idSM") != null) {
            try {
                long id = Long.parseLong(request.getParameter("idEmpleado"));
                DAO_Empleado daoEmp = new DAO_Empleado();
                Empleado emp = daoEmp.Buscar(id, request.getParameter("idSM"));

                if (emp != null) {
                    DAO_Caja daoCaja = new DAO_Caja();
                    Caja caja = daoCaja.buscar(emp.getCaja().getIdCaja(), request.getParameter("idSM"));

                    request.setAttribute("inicio", "{" + emp.toString() + "," + caja.toString() + "}");

                    rq.forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
        // System.out.println("ENTROO");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String Bagregar = request.getParameter("Agregar");
        String Bbuscar = request.getParameter("buscar");
        String Bactualizar = request.getParameter("actualizar");
        String Bregistrar = request.getParameter("registrar");

        String idProd = request.getParameter("IdProducto");
        String idSup = request.getParameter("IdSup");

        String idCliente = request.getParameter("IdCliente");
        String nameCliente = request.getParameter("nameCliente");
        String direCliente = request.getParameter("Cdireccion");

        String JsonDatos = request.getParameter("Datos");
        String ArrayDetalles = request.getParameter("Detalle");
        String Bliquidar = request.getParameter("liquidar");
        String Pago = request.getParameter("Pago");

        JSONObject ob = new JSONObject();

        if (Bliquidar != null && JsonDatos != null && ArrayDetalles != null && Pago != null) {

            try {
                JSONObject datos = new JSONObject(JsonDatos);
                JSONArray detalles = new JSONArray(ArrayDetalles);
                DAO_Venta daoVenta = new DAO_Venta();
                DAO_Detallado daoDet = new DAO_Detallado();
                Venta venta = new Venta(new Empleado(datos.getLong("IdVendedor")), new Supermercado(datos.getString("IdSup")), new Cliente(datos.getLong("IdCliente")), new Date(datos.getLong("date")), datos.getDouble("TotalPagar"));

                ArrayList<Inventario> arrayDe = new ArrayList<Inventario>();

                if (daoVenta.crear(venta)) {
                    System.out.println("entra");
                    int lastVenta = daoVenta.obtLastVenta();
                    for (int i = 0; i < detalles.length(); i++) {
                        JSONObject d = detalles.getJSONObject(i);
                        Inventario inv = new Inventario();
                        inv.setProducto(new Producto(d.getString("Id")));
                        inv.setCantidad(d.getInt("cantidad"));
                        inv.setSupermercado(new Supermercado(datos.getString("IdSup")));
                        arrayDe.add(inv);
                    }
                    daoDet.addAll(arrayDe, lastVenta);
                    ob.put("Correcto", "correctamente");
                    out.println(ob);
                }else{
                     System.out.println("no se creo la venta");
                }
                out.println(ob);
            } catch (JSONException ex) {
                 System.out.println(ex.toString());
                ex.printStackTrace();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                 System.out.println(ex.toString());
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                 System.out.println(ex.toString());
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (Bregistrar != null) {

            if (nameCliente.length() > 0 && idCliente.length() > 0 && direCliente.length() > 0) {
                try {
                    long idcliente = Long.parseLong(idCliente);
                    DAO_Cliente daoCliente = new DAO_Cliente();
                    Cliente cliente = new Cliente(idcliente);
                    cliente.setNombre(nameCliente);
                    cliente.setDireccion(direCliente);
                    if (!daoCliente.crear(cliente)) {
                        ob.put("error", "No se pudo registrar, Id replicada");
                    }
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (ClassNotFoundException ex) {
                    out.println("{ \"error\": \"Clase no Encontrada\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"Cedula cliente debe ser un número\"}");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }
        }
        if (Bactualizar != null) {
            if (nameCliente.length() > 0 && idCliente.length() > 0 && direCliente.length() > 0) {
                try {
                    long idcliente = Long.parseLong(idCliente);
                    DAO_Cliente daoCliente = new DAO_Cliente();
                    Cliente cliente = new Cliente(idcliente);
                    cliente.setNombre(nameCliente);
                    cliente.setDireccion(direCliente);
                    if (!daoCliente.actualizar(cliente)) {
                        ob.put("error", "No se encontró el Cliente");
                    }
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (ClassNotFoundException ex) {
                    out.println("{ \"error\": \"Clase no Encontrada\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"Cedula cliente debe ser un número\"}");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }
        }

        if (Bagregar != null && idProd != null && idSup != null) {

            try {
                DAO_Inventario daoInv = new DAO_Inventario();
                Inventario in = daoInv.buscar(idProd.trim() + idSup.trim());

                if (in != null) {
                    ob.put("nombre", in.getNombreProducto());
                    ob.put("precio", in.getPrecio());
                } else {
                    ob.put("error", "Producto no Encontrado");
                }

                out.println(ob);
            } catch (SQLException ex) {
                out.println("{ \"error\": \"Error SQL\"}");
            } catch (ClassNotFoundException ex) {
                out.println("{ \"error\": \"Clase no Encontrada\"}");
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                out.println("{ \"error\": \"Json\"}");
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (Bbuscar != null) {
            if (idCliente.length() > 0) {
                try {
                    long idcliente = Long.parseLong(idCliente);
                    DAO_Cliente daoCliente = new DAO_Cliente();
                    Cliente cliente = daoCliente.Buscar(idcliente);

                    if (cliente != null) {
                        ob.put("idCliente", cliente.getIdPersona());
                        ob.put("nombre", cliente.getNombre());
                        ob.put("direccion", cliente.getDireccion());
                    } else {
                        ob.put("error", "No se encontró el Cliente");
                    }
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                } catch (ClassNotFoundException ex) {
                    out.println("{ \"error\": \"Clase no Encontrada\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NumberFormatException e) {
                    out.println("{ \"error\": \"Cedula cliente debe ser un número\"}");
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ServletEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                out.println("{ \"error\": \"Dato Id Vacio\"}");
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
