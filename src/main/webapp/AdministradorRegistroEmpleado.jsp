<%-- 
    Document   : AdministradorRegistroEmpleado
    Created on : 4/11/2018, 08:53:41 PM
    Author     : PC02
--%>

<%@page import="edu.co.sergio.mundo.vo.Empleado"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.co.sergio.mundo.vo.Caja"%>
<%@page import="java.util.List"%>
<%@page import="edu.co.sergio.mundo.vo.Empleado"%>

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




        <!-- TABLES-->
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">

        <link href="css/ccalertify/alertify.css" rel="stylesheet" type="text/css">
        <link href="css/ccalertify/themes/default.css" rel="stylesheet" type="text/css">
        <script src="js/alertify.js"></script>


        <!-- Bootstrap core CSS-->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template-->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <!-- Page level plugin CSS-->
        <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="css/sb-admin.css" rel="stylesheet">

        <!-- Jquery-->
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>

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
                    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
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
                <li class="nav-item">
                    <a class="nav-link" href="AdministradorInicio.jsp">
                        <i class="fa fa-home"></i>
                        <span>Inicio</span>
                    </a>
                </li>
                <li class="nav-item active">
                    <a class=" nav-link" href="ServletAdministradorRegistroEmpleado?idSM=<%=emp.getSupermercado().getIdSM()%>">
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
                <div class="container-fluid">
                    <div class="container">
                        <h1> Registro Empleado: </h1>
                        <form id="form1">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <label> Cédula: </label>
                                    <input class="form-control" name="cedula" id="cedula">
                                    <label> Nombres y Apellidos: </label>
                                    <input class="form-control" name="nomApelli" id="nomApelli">
                                    <label> Contraseña: </label>
                                    <input class="form-control" name="contra" id="contra">
                                    <div class="form-row">
                                        <div class="col-md-8">
                                            <label> Cargo: </label>
                                            <input class="form-control" name="cargo" id="cargo">
                                        </div>
                                        <div class="col-md-4">
                                            <label> Caja Actual: </label>
                                            <input class="form-control" name="cajaActual" id="cajaActual" disable>

                                            <label> Caja: </label>
                                            <select name="caja" id="caja" aria-controls="dataTable" class="custom-select custom-select-sm form-control form-control-sm">
                                                <%
                                                    if (request.getAttribute("cajas") != null) {
                                                        List<String> disponibles = (ArrayList<String>) request.getAttribute("cajas");
                                                        if(disponibles.size()==0){
                                                %>
                                                <option>No hay cajas disponibles</option>
                                                <%
                                                        }else{
                                                        for (int i = 0; i < disponibles.size(); i++) {
                                                            String caja = disponibles.get(i);
                                                %>
                                                <option><%=caja%></option>
                                                <%
                                                    }
                                                  }
                                                } else {
                                                %>
                                                <option>No hay datos.</option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                    </div>

                                </div>  
                                <div class="col-md-1">
                                    <span></span>
                                </div>
                                <div class="col-md-5">
                                    <div class="form-row">
                                        <div class="col-md-6">
                                            <br>
                                            <br>
                                            <input type="button" name="registrar" id="registrar" style="width: 60%" class="btn btn-success waves-effect" value="Registrar">
                                            <br>
                                            <br>
                                            <input type="button" name="eliminar" id="eliminar" style="width: 60%" value="Eliminar" class="btn btn-danger waves-effect" >

                                        </div>
                                        <div class="col-md-6">
                                            <br>
                                            <br>
                                            <input type="button" name="buscar" id="buscar" style="width: 60%" class="btn btn-primary waves-effect" value="Buscar">
                                            <br>
                                            <br>
                                            <input type="button" name="actualizar" id="actualizar" style="width: 60%"  class="btn btn-warning waves-effect" value="Actualizar">
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </form>      
                        <br>
                        <br>
                        <br>
                        <div class="card-body">
                            <table id="Tabla1" class="display" width="100%">   
                            </table>
                        </div>
                    </div>

                    <br>
                    <br>
                    <script>
                        function mostrarTabla(jsonn) {
                            $(document).ready(function () {
                                $('#Tabla1').DataTable({
                                    data: jsonn.emple,
                                    destroy: true,
                                    empty: true,
                                    columns: [
                                        {title: "Cédula"},
                                        {title: "Nombre"},
                                        {title: "Contraseña"},
                                        {title: "Caja"}
                                    ]
                                });
                            });
                        }
                    </script>
                    <!-- Botones Actualizar-REgistrar-Buscar  -->
                    <script>
                        $(document).ready(function () {
                            $('#actualizar').click(function (event) {
                                var fc;
                                var cod = $('#cedula').val();
                                var nom = $('#nomApelli').val();
                                var con = $('#contra').val();
                                var car = $('#cargo').val();
                                var caj = $('#caja').val();
                                var caja = $('#cajaActual').val();
                                var sm = "<%=emp.getSupermercado().getIdSM()%>";
                                var act = "actualizar";
                                $.post('ServletAdministradorRegistroEmpleado', {
                                    codigo: cod,
                                    nombre: nom,
                                    contra: con,
                                    cargo: car,
                                    caja: caj,
                                    cajaActual: caja,
                                    idSM: sm,
                                    actualizar: act
                                }, function (responseText) {
                                    fc = JSON.parse(responseText);
                                    if (typeof fc.error === "undefined") {
                                        mostrarTabla(fc);
                                        document.getElementById("cedula").value = null;
                                        document.getElementById("nomApelli").value = null;
                                        document.getElementById("contra").value = null;
                                        document.getElementById("cargo").value = null;
                                        document.getElementById("cajaActual").value = null;
                                        alertify.success('Actualización correcta');
                                    } else {
                                        alertify.error(fc.error);

                                    }
                                });
                            });
                        });

                        $(document).ready(function () {
                            $('#registrar').click(function (event) {
                                var fc;
                                var cod = $('#cedula').val();
                                var nom = $('#nomApelli').val();
                                var con = $('#contra').val();
                                var car = $('#cargo').val();
                                var caj = $('#caja').val();
                                var caja = $('#cajaActual').val();
                                var sm = "<%=emp.getSupermercado().getIdSM()%>";
                                var Bregistrar = "$('#registrar').val()";
                                $.post('ServletAdministradorRegistroEmpleado', {
                                    codigo: cod,
                                    nombre: nom,
                                    contra: con,
                                    cargo: car,
                                    caja: caj,
                                    cajaActual: caja,
                                    idSM: sm,
                                    registrar: Bregistrar
                                }, function (responseText) {
                                    fc = JSON.parse(responseText);
                                    if (typeof fc.error === "undefined") {
                                        mostrarTabla(fc);
                                        document.getElementById("cedula").value = null;
                                        document.getElementById("nomApelli").value = null;
                                        document.getElementById("contra").value = null;
                                        document.getElementById("cargo").value = null;
                                        document.getElementById("cajaActual").value = null;
                                        alertify.success('Registro correcto');
                                    } else {
                                        alertify.error(fc.error);
                                    }
                                });
                            });
                        });

                        $(document).ready(function () {
                            $('#buscar').click(function (event) {
                                var fc;
                                var cod = $('#cedula').val();
                                var b = "buscar";
                                var sm = "<%=emp.getSupermercado().getIdSM()%>";
                                $.post('ServletAdministradorRegistroEmpleado', {
                                    codigo: cod,
                                    idSM: sm,
                                    buscar: b
                                }, function (responseText) {
                                    fc = JSON.parse(responseText);
                                    if (typeof fc.error === "undefined") {
                                        mostrarTabla(fc);
                                        document.getElementById("cedula").value = fc.cedula2;
                                        document.getElementById("nomApelli").value = fc.nombre2;
                                        document.getElementById("contra").value = fc.contra2;
                                        document.getElementById("cargo").value = fc.cargo2;
                                        document.getElementById("cajaActual").value = fc.caja2;
                                        alertify.success('Correcto');
                                    } else {
                                        if (fc.error === "No se encontró el Cliente") {
                                            alertify.warning(fc.error + " Registre el cl");
                                        } else {
                                            alertify.error(fc.error);
                                        }
                                    }

                                }
                                );
                            });
                        });

                        $(document).ready(function () {
                            $('#eliminar').click(function (event) {
                                var fc;
                                var cod = $('#cedula').val();
                                var e = "eliminar";
                                var sm = "<%=emp.getSupermercado().getIdSM()%>";
                                $.post('ServletAdministradorRegistroEmpleado', {
                                    codigo: cod,
                                    idSM: sm,
                                    eliminar: e
                                }, function (responseText) {
                                    fc = JSON.parse(responseText);
                                    if (typeof fc.error === "undefined") {
                                        mostrarTabla(fc);
                                        document.getElementById("cedula").value = null;
                                        document.getElementById("nomApelli").value = null;
                                        document.getElementById("contra").value = null;
                                        document.getElementById("cargo").value = null;
                                        document.getElementById("cajaActual").value = null;
                                        alertify.success('Correcto');
                                    } else {
                                        if (fc.error === "No se encontró el Cliente") {
                                            alertify.warning(fc.error + " Registre el cl");
                                        } else {
                                            alertify.error(fc.error);
                                        }
                                    }

                                }
                                );
                            });
                        });
                    </script>
                    <!--Inicializar de Empleado -->
                    <%
                        if(request.getAttribute("empleados")!=null){
                            String hola="'"+(String)request.getAttribute("empleados")+"'";
                    %>
                    <script>

                        var jsonn = JSON.parse(<%=hola.toString()%>);
                        mostrarTabla(jsonn);

                    </script>
                    <%
                }
                    %>
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
            <script src="vendor/datatables/jquery.dataTables.js"></script>
            <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

            <!-- Page level plugin JavaScript--> 
            <script src="vendor/datatables/jquery.dataTables.js"></script>
            <script src="vendor/datatables/dataTables.bootstrap4.js"></script>

            <!-- Custom scripts for all pages-->
            <script src="js/sb-admin.min.js"></script>

    </body>

</html>
<%}%>