/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import edu.co.sergio.mundo.dao.DAO_Inventario;
import edu.co.sergio.mundo.dao.DAO_Producto;
import edu.co.sergio.mundo.dao.DAO_Supermercado;
import edu.co.sergio.mundo.vo.Inventario;
import edu.co.sergio.mundo.vo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.SQLException;
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
 * @author Sebastian
 */
public class ServletAdministradorInventario extends HttpServlet {

    private DAO_Inventario daoIn;
    private DAO_Producto daoPr;
    private DAO_Supermercado daoSup;
    private Inventario Inv;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void init() throws ServletException {
        try {
            this.daoIn = new DAO_Inventario();
            this.daoPr = new DAO_Producto();
            this.daoSup = new DAO_Supermercado();
        } catch (SQLException ex) {
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
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
        RequestDispatcher rq = request.getRequestDispatcher("AdministradorInventario.jsp");
        List<Inventario> inventario = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String idSM = request.getParameter("idSM");
        String dat = "";
        try {
            inventario = daoIn.getProductos(idSM);
            JSONObject ob = new JSONObject();
            JSONArray matr = new JSONArray();
            for (Inventario inventario1 : inventario) {
                JSONArray fila = new JSONArray();
                fila.put(inventario1.getCodigoBarras());
                fila.put(inventario1.getNombreProducto());
                fila.put(inventario1.getPrecio());
                fila.put(inventario1.getCantidad());
                matr.put(fila);
            }
            ob.put("productos", matr);
            dat = ob.toString();
        } catch (SQLException ex) {
             out.println("{ \"error\": \"Error SQL\"}");
            Logger.getLogger(ServletAdministradorRegistroCaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            out.println("{ \"error\": \"Json\"}");
            Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("inventario", dat);
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
        List<Inventario> inventario = null;
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        JSONObject ob = new JSONObject();

        String idSM = request.getParameter("idSM");

        if (request.getParameter("buscar") != null) {
            String idInv = request.getParameter("codigo");
            if (idInv.length() > 0) {
                try {

                    Inv = daoIn.buscar(idInv + idSM);

                    if (Inv != null) {
                        ob.put("codigo2", Inv.getProducto().getCodigoBarras());
                        ob.put("producto2", Inv.getProducto().getNombreProducto());
                        ob.put("precio2", Inv.getPrecio());
                        ob.put("cantidad2", Inv.getCantidad());
                    } else {
                        ob.put("error", "No se encuentra producto");
                    }
                    inventario = daoIn.getProductos(idSM);
                    JSONArray matr = new JSONArray();
                    for (Inventario inventario1 : inventario) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getCodigoBarras());
                        fila.put(inventario1.getNombreProducto());
                        fila.put(inventario1.getPrecio());
                        fila.put(inventario1.getCantidad());
                        matr.put(fila);
                    }
                    ob.put("productos", matr);
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
                    out.println("{ \"error\": \"El tipo de dato es incorrecto\"}");
                }
            } else {
                out.println("{ \"error\": \"Dato Id Vacio\"}");
            }

        }

        if (request.getParameter("actualizar") != null) {

            String idInv = request.getParameter("codigo"),
                    producto = request.getParameter("producto"),
                    cantidad = request.getParameter("cantidad"),
                    precio = request.getParameter("precio");
            if (idInv.length() > 0 && producto.length() > 0 && cantidad.length() > 0 && precio.length() > 0) {
                try {
                    Inv = daoIn.buscar(idInv + idSM);
                    Inv.getProducto().setNombreProducto(producto);
                    Inv.setPrecio(Double.parseDouble(precio));
                    Inv.setCantidad(Integer.parseInt(cantidad));
                    if (!daoIn.actualizar(Inv)) {
                        ob.put("error", "No se encontró el Producto en Inventario");
                    } else {
                    }

                    inventario = daoIn.getProductos(idSM);
                    JSONArray matr = new JSONArray();
                    for (Inventario inventario1 : inventario) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getCodigoBarras());
                        fila.put(inventario1.getNombreProducto());
                        fila.put(inventario1.getPrecio());
                        fila.put(inventario1.getCantidad());
                        matr.put(fila);
                    }
                    ob.put("productos", matr);
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
                    out.println("{ \"error\": \"El tipo de dato es incorrecto\"}");
                } catch (NullPointerException e) {
                    out.println("{ \"error\": \"El producto no existe\"}");
                }
            } else {
                out.println("{ \"error\": \"Datos Vacios\"}");
            }

        }

        if (request.getParameter("registrar") != null) {

            String codigo = request.getParameter("codigo"),
                    producto = request.getParameter("producto"),
                    precio = request.getParameter("precio"),
                    cantidad = request.getParameter("cantidad");
            if (codigo.length() > 0 && producto.length() > 0 && precio.length() > 0 && cantidad.length() > 0) {

                try {
                    Inv = new Inventario();
                    Inv.setCantidad(Integer.parseInt(cantidad));
                    Inv.setIdInventario(codigo + idSM);
                    Inv.setPrecio(Double.parseDouble(precio));
                    //REVISAR EXCEPCION 

                    if (daoPr.Buscar(codigo) == null) {
                        if (!daoPr.crear(new Producto(codigo, producto))) {
                            ob.put("error", "No se pudo registrar, Id replicada");
                        }

                    }
                    Inv.setProducto(daoPr.Buscar(codigo));

                    Inv.setSupermercado(daoSup.buscar(idSM));
                    if (!daoIn.crear(Inv)) {
                        ob.put("error", "El producto ya se encuentra en inventario");
                    }
                    inventario = daoIn.getProductos(idSM);
                    JSONArray matr = new JSONArray();
                    for (Inventario inventario1 : inventario) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getCodigoBarras());
                        fila.put(inventario1.getNombreProducto());
                        fila.put(inventario1.getPrecio());
                        fila.put(inventario1.getCantidad());
                        matr.put(fila);
                    }
                    ob.put("productos", matr);
                    out.println(ob);
                } catch (SQLException ex) {
                    out.println("{ \"error\": \"Error SQL\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                     out.println("{ \"error\": \"Clase no Encontrada\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
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
           
            if(idInv.length()>0){
                try {
                
                Inv = daoIn.buscar(idInv + idSM);
                if(Inv!=null){
                    if(!daoIn.eliminar(Inv.getIdInventario())){
                        ob.put("error", "El producto no se pudo eliminar");
                    }
                }else{
                     ob.put("error", "No se pudo encontrar el producto");
                }
                
                inventario = daoIn.getProductos(idSM);
                    JSONArray matr = new JSONArray();
                    for (Inventario inventario1 : inventario) {
                        JSONArray fila = new JSONArray();
                        fila.put(inventario1.getCodigoBarras());
                        fila.put(inventario1.getNombreProducto());
                        fila.put(inventario1.getPrecio());
                        fila.put(inventario1.getCantidad());
                        matr.put(fila);
                    }
                    ob.put("productos", matr);
                    System.out.println("HOLA " +ob.toString());
                    out.println(ob);
            } catch (SQLException ex) {
                out.println("{ \"error\": \"Error SQL\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                     out.println("{ \"error\": \"Clase no Encontrada\"}");
                    Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    out.println("{ \"error\": \"Json\"}");
                Logger.getLogger(ServletAdministradorInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
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
