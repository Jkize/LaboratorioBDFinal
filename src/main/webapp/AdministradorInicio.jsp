<%-- 
    Document   : AdministradorInicio
    Created on : 4/11/2018, 08:17:02 PM
    Author     : Sebastian-
--%>

<%@page import="edu.co.sergio.mundo.vo.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sesion = request.getSession();
    Empleado emp = (Empleado)sesion.getAttribute("Admin");
    if( emp == null){
      response.sendRedirect("index.jsp");
    }else{ 

%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Administrador</title>

        <!-- Bootstrap core CSS-->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <!-- Page level plugin CSS-->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin.css" rel="stylesheet">

    </head>

    <body id="page-top">

        <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

            <a class="navbar-brand mr-1" href="index.html">Administrador</a>

            <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
                <i class="fas fa-bars"></i>
            </button>

            <!-- NO ELIMINAR -->
            <!-- Navbar Search --> 
            <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
            </form>

            <!-- Navbar -->
            <ul class="navbar-nav ml-auto ml-md-0">
                <li class="nav-item dropdown no-arrow">
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i class="fas fa-user-circle fa-fw"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Cerrar Sesión</a>
                    </div>
                </li>
            </ul>

        </nav>

        <div id="wrapper">

            <!-- Sidebar -->
            <ul class="sidebar navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="AdministradorInicio.jsp">
                        <i class="fa fa-home"></i>
                        <span>Inicio</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ServletAdministradorRegistroEmpleado?idSM=<%=emp.getSupermercado().getIdSM()%>">
                        <i class="fa fa-user"></i>
                        <span>Registro Empleado</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ServletAdministradorRegistroCaja?idSM=<%=emp.getSupermercado().getIdSM()%>">
                        <i class="fa fa-window-restore"></i>
                        <span>Registro Caja</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="ServletAdministradorInventario?idSM=<%=emp.getSupermercado().getIdSM()%>">
                        <i class="fa fa-cubes"></i>
                        <span>Inventario</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="AdministradorInformes.jsp">
                        <i class="fas fa-fw fa-chart-area"></i>
                        <span>Informes</span></a>
                </li>
            </ul>

            <div id="content-wrapper">


                <!-- EMPEZAR A ESCRIBIR Y/O EDITAR DENTRO DE ESTE DIV -->
                <div class="container-fluid" align="center">
                    <div class="container">
                        <!-- Page Content -->
                        <h1>Bienvenido</h1>
                        <hr>
                        <div class="row clearfix" align="center">
                            <div class="col-sm-4">
                                <label>Id Supermercado</label>
                                <input class="form-control"name="IdSup" id="IdSup" value="<%=emp.getSupermercado().getIdSM()%>" disabled>

                            </div>
                            <div class="col-sm-4">
                                <label>Cedula Vendedor</label>
                                <input class="form-control" name="IdVendedor" id="IdVendedor" value="<%=emp.getIdPersona()%>" disabled>
                                <img src="images/img-01.png" alt="IMG">
                            </div>

                            <div class="col-sm-4">
                                <label>Nombre Vendedor</label>
                                <input class="form-control"name="nombreVend" id="nombreVend" value="<%=emp.getNombre()%>" disabled>

                            </div>


                        </div>
                    </div>
                </div>

                <!-- Sticky Footer -->
                <footer class="sticky-footer">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright © Slamena Market-2018</span>
                        </div>
                    </div>
                </footer>

            </div>
            <!-- /.content-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">¿Estas seguro que deseas Salir?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Selecciona "Salir" si estas seguro de cerrar sesión.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancelar</button>
                        <a class="btn btn-primary" href="ServletIndex?Admin=1&idEmpleado=<%=emp.getIdPersona()%>">Salir</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Page level plugin JavaScript-->
        <script src="vendor/chart.js/Chart.min.js"></script>
        <script src="vendor/datatables/jquery.dataTables.js"></script>
        <script src="vendor/datatables/dataTables.bootstrap4.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="js/sb-admin.min.js"></script>

        <!-- Demo scripts for this page-->
        <script src="js/demo/datatables-demo.js"></script>
        <script src="js/demo/chart-area-demo.js"></script>

    </body>


</html>
<%}%>
