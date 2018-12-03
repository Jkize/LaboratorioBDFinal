<%-- 
    Document   : Empleado
    Created on : 4/11/2018, 08:15:55 PM
    Author     : Jhoan Saavedra-Aya
--%>
<%@page import="edu.co.sergio.mundo.vo.Empleado"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sesion = request.getSession();
    Empleado emp = (Empleado)sesion.getAttribute("Operario");
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

        <title>Empleado</title>




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

            <a class="navbar-brand mr-1" href="index.html">Empleado</a>

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
                    <a class="nav-link" href="Empleado.jsp">
                        <i class="fas fa-search-dollar"></i>
                        <span>Ventas Clientes</span>
                    </a>
            </ul>

            <div id="content-wrapper">
                <!-- EMPEZAR A ESCRIBIR Y/O EDITAR DENTRO DE ESTE DIV -->
                <section class="content">
                    <div class="container-fluid">

                        <!-- Page Content -->
                        <h1>WELCOME</h1>
                        <hr>


                        <div class="block-header">
                            <form id="form1">
                                <div class="card">

                                    <br>
                                    <div class="row clearfix">
                                        <div class="col-sm-12" align="center">
                                            <input class="text" name="nombreVend" id="nombreVend" value="<%=emp.getNombre()%>" disabled>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row clearfix" align="center">
                                        <div class="col-sm-4">
                                            <label>Id Supermercado</label>
                                            <input class="text" name="IdSup" id="IdSup" value="<%=emp.getSupermercado().getIdSM()%>" disabled>

                                        </div>

                                        <div class="col-sm-4">
                                            <label>N° Caja</label>
                                            <input class="text" name="IdCaja" id="IdCaja" value="<%=emp.getCaja().getIdCaja()%>" disabled>

                                        </div>

                                        <div class="col-sm-4">
                                            <label>Cedula Vendedor</label>
                                            <input class="text" name="IdVendedor" id="IdVendedor" value="<%=emp.getIdPersona()%>" disabled>
                                        </div>
                                    </div>


                                    <hr>
                                    <h3>Cliente</h3>

                                    <div class="row clearfix">
                                        <div class="col-sm-6" align="center">
                                            <label>Cedula Cliente</label>
                                            <input class="text" name="IdCliente" id="IdCliente">

                                        </div>

                                        <div class="col-sm-6">

                                            <div class="row clearfix">
                                                <div class="col-sm-4">
                                                    <input type="button" name="buscar" id="buscar" class="btn btn-primary waves-effect" value="Buscar">
                                                </div>
                                                <div class="col-sm-4">

                                                    <input type="button" name="actualizar" id="actualizar" class="btn btn-warning waves-effect" value="Actualizar">
                                                </div>


                                                <div class="col-sm-4">
                                                    <input type="button" name="registrar" id="registrar" class="btn btn-success waves-effect" value="Registrar">
                                                </div>

                                            </div>

                                        </div>


                                    </div>

                                    <br>
                                    <div class="row clearfix">
                                        <div class="col-sm-6" align="center">
                                            <label>Nombre Cliente</label>
                                            <input class="text" name="nameCliente" id="nameCliente">

                                        </div>
                                        <div class="col-sm-6" align="left">
                                            <label>Direccion</label>
                                            <input class="text" name="Cdireccion" id="Cdireccion">

                                        </div>
                                    </div>
                                    <br>

                                    <hr>

                                    <br>

                                    <div class="row clearfix">
                                        <div class="col-sm-4">
                                            <h3>Productos Venta</h3>
                                        </div>

                                        <div class="col-sm-4"> 
                                        </div>

                                        <div class="col-sm-4" >
                                            <label>Monto Actual Caja</label>
                                            <input class="text" name="ActualCaja" id="ActualCaja" value="<%=emp.getCaja().getMontoActual()%>" disabled>
                                        </div>

                                    </div>

                                    <br>
                                    <div class="row clearfix">
                                        <div class="col-sm-6">
                                            <div class="row clearfix">

                                                <div class="col-sm-6" align="center">
                                                    <label>Cod Prod</label>
                                                    <input class="text" name="IdProducto" id="IdProducto">
                                                </div>
                                                <div class="col-sm-6" align="center">
                                                    <label>Cantidad</label>
                                                    <input class="text" name="Cantidad" id="Cantidad">
                                                </div>


                                            </div>
                                        </div>

                                        <div class="col-sm-1">
                                            <input type="button"  name="Agregar" id="AgregarProducto" class="btn btn-success waves-effect" value="AgregarP">
                                        </div>

                                        <div class="col-sm-2" align="center">
                                            <input type="button" name="eliminar" id="eliminar" value="Eliminar" class="btn btn-danger waves-effect" >
                                        </div>

                                    </div>
                                    <br>


                                    <div class="row clearfix">
                                        <div class="col-sm-8">
                                            <div class="card-body">
                                                <table id="Tabla1" class="display" width="100%">                                                  

                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <br>
                                            <div class="row clearfix">
                                                <div class="col-sm-4" align="right">
                                                    <label>Total a pagar</label>

                                                </div>
                                                <div class="col-sm-8">
                                                    <input class="text" name="TotalPagar" id="TotalPagar" disabled>
                                                </div>
                                            </div>

                                            <br>
                                            <div class="row clearfix">
                                                <div class="col-sm-4" align="right">
                                                    <label>Pago</label>

                                                </div>
                                                <div class="col-sm-8">
                                                    <input class="text" name="Pago" id="Pago">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="row clearfix">
                                                <div class="col-sm-4" align="right">
                                                    <label>Cambio</label>

                                                </div>
                                                <div class="col-sm-8">
                                                    <input class="text" name="Cambio" id="Cambio" disabled>
                                                </div>
                                            </div>

                                            <br>
                                            <div class="row clearfix">
                                                <div class="col-sm-6" align="center">
                                                    <input type="button" name="liquidar" id="liquidar" class="btn btn-success waves-effect" value="Liquidar">  
                                                </div>     
                                                <div class="col-sm-6" align="center">
                                                    <input type="button" name="imprimir" id="imprimir" class="btn btn-primary waves-effect" value="Nuevo" >  
                                                </div>  
                                            </div>


                                        </div>


                                    </div>



                                </div>
                            </form>
                        </div>                    
                    </div>
                </section>
            </div>

        </div>

        <script type="text/javascript">
            var array = new Array();
            Array.prototype.indexOf2 = function (key) {
                var pos = -1;
                for (var i = 0; i < this.length; i++) {
                    if (this[i].Id === key) {
                        return i;
                    }
                }
                return pos;

            };
            Array.prototype.toString2 = function () {
                var a = "";
                for (var i = 0; i < this.length; i++) {
                    a += this[i].nombre + "," + this[i].precio + "," + this[i].total + "," + this[i].Id + "," + this[i].cantidad + ",";
                }
                return a;
            };
        </script>


        <!-- Botón Liquidar --->

        <script>
            $(document).ready(function () {
                $('#liquidar').click(function (event) {
                    var fc;
                    var pago = parseFloat($('#Pago').val());


                    var dat = {
                        IdCliente: document.getElementById("IdCliente").value,
                        nameCliente: document.getElementById("nameCliente").value,
                        Cdireccion: document.getElementById("Cdireccion").value,
                        IdSup: document.getElementById("IdSup").value,
                        IdCaja: document.getElementById("IdCaja").value,
                        IdVendedor: document.getElementById("IdVendedor").value,
                        nombreVend: document.getElementById("nombreVend").value,
                        TotalPagar: parseFloat(document.getElementById("TotalPagar").value),
                        date: new Date().getTime()
                    };

                    var Bliquidar = $('#liquidar').val();

                    if (pago >= dat.TotalPagar) {

                        $.post('ServletEmpleado', {
                            Datos: JSON.stringify(dat),
                            Detalle: JSON.stringify(array),
                            liquidar: Bliquidar,
                            Pago: pago
                        }, function (responseText) {
                            fc = JSON.parse(responseText);
                            if (typeof fc.error === "undefined") {
                                alertify.success('Liquidación completa');
                                document.getElementById("Cambio").value = pago - dat.TotalPagar;
                                document.getElementById("imprimir").disabled = false;
                                document.getElementById("liquidar").disabled = true;
                                document.getElementById("ActualCaja").value = fc.CajaActual;

                            } else {
                                alertify.error(fc.error);
                            }
                        });
                    } else {
                        alertify.warning("El pago es menor al Total pagar");
                    }
                });
            }
            );



            $(document).ready(function () {
                $('#imprimir').click(function (event) {
                    document.getElementById("liquidar").disabled = false;
                    document.getElementById("imprimir").disabled = true;
                    document.getElementById("Cambio").value = "";
                    document.getElementById("Pago").value = "";
                    document.getElementById("IdProducto").value = "";
                    document.getElementById("Cantidad").value = "";
                    document.getElementById("IdCliente").value="";
                    document.getElementById("Cdireccion").value="";
                    document.getElementById("nameCliente").value="";


                    array = new Array();
                    mostratTabla();
                });
            }
            );

        </script>

        <!-- Botones Agregar P y Eliminar P -->
        <script type="text/javascript">
            $(document).ready(function () {
                $('#AgregarProducto').click(function (event) {
                    var fc;
                    var datos = {
                        Id: document.getElementById("IdProducto").value,
                        cantidad: parseInt(document.getElementById("Cantidad").value)
                    };

                    if (datos.cantidad > 0) {
                        if (array.length === 0 || array.indexOf2(datos.Id) === -1) {
                            var codBa = $('#IdProducto').val();
                            var agre = $('#AgregarProducto').val();
                            var idsup = $('#IdSup').val();
                            $.post('ServletEmpleado', {
                                IdProducto: codBa,
                                Agregar: agre,
                                IdSup: idsup

                            }, function (responseText) {
                                var fc = JSON.parse(responseText);

                                if (typeof fc.error === "undefined") {
                                    //alert("Entra 1");
                                    datos.nombre = fc.nombre;
                                    datos.precio = fc.precio;
                                    datos.total = datos.precio * datos.cantidad;

                                    //alert(datos.toString());
                                    array.push(datos);
                                    //alert(array.toString2());
                                    mostratTabla();
                                } else {
                                    alertify.error(fc.error);
                                }
                            });
                        } else {
                            var dat2 = array[array.indexOf2(datos.Id)];
                            dat2.cantidad = dat2.cantidad + datos.cantidad;
                            dat2.total += datos.cantidad * dat2.precio;
                            mostratTabla();
                        }
                    } else {
                        alertify.warning("Para un mejor procesamiento Cantidad debe ser entero");
                    }

                });
            });

            $(document).ready(function () {
                $('#eliminar').click(function (event) {
                    var fc;
                    var datos = {
                        Id: document.getElementById("IdProducto").value,
                        cantidad: parseInt(document.getElementById("Cantidad").value)
                    };

                    if (datos.cantidad > 0) {
                        var pos = array.indexOf2(datos.Id);
                        if (pos !== -1) {
                            if (datos.cantidad >= array[pos].cantidad) {
                                array.splice(pos, 1);
                            } else {
                                var dat2 = array[pos];
                                dat2.cantidad = dat2.cantidad - datos.cantidad;
                                dat2.total = dat2.cantidad * dat2.precio;
                            }
                            mostratTabla();
                        }
                    }

                });
            });

        </script>

        <!-- Botones Actualizar-REgistrar-Buscar Cliente -->
        <script>
            $(document).ready(function () {
                $('#actualizar').click(function (event) {
                    var fc;
                    var id = $('#IdCliente').val();
                    var name = $('#nameCliente').val();
                    var Cdire = $('#Cdireccion').val();
                    var Bactuali = $('#actualizar').val();
                    $.post('ServletEmpleado', {
                        IdCliente: id,
                        nameCliente: name,
                        Cdireccion: Cdire,
                        actualizar: Bactuali
                    }, function (responseText) {
                        fc = JSON.parse(responseText);
                        if (typeof fc.error === "undefined") {
                            document.getElementById("nameCliente").value = null;
                            document.getElementById("Cdireccion").value = null;
                            alertify.success('Actualización correcta');
                        } else {
                            if (fc.error === "No se encontró el Cliente") {
                                alertify.warning(fc.error + " Registre el cl");
                            } else {
                                alertify.error(fc.error);
                            }
                        }
                    });
                });
            });

            $(document).ready(function () {
                $('#registrar').click(function (event) {
                    var fc;
                    var id = $('#IdCliente').val();
                    var name = $('#nameCliente').val();
                    var Cdire = $('#Cdireccion').val();
                    var Bregistrar = $('#registrar').val();
                    $.post('ServletEmpleado', {
                        IdCliente: id,
                        nameCliente: name,
                        Cdireccion: Cdire,
                        registrar: Bregistrar
                    }, function (responseText) {
                        fc = JSON.parse(responseText);
                        if (typeof fc.error === "undefined") {
                            document.getElementById("nameCliente").value = null;
                            document.getElementById("Cdireccion").value = null;
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
                    var id = $('#IdCliente').val();
                    var bus = $('#buscar').val();


                    $.post('ServletEmpleado', {
                        IdCliente: id,
                        buscar: bus
                    }, function (responseText) {

                        fc = JSON.parse(responseText);
                        if (typeof fc.error === "undefined") {

                            document.getElementById("IdCliente").value = fc.idCliente;
                            document.getElementById("nameCliente").value = fc.nombre;
                            document.getElementById("Cdireccion").value = fc.direccion;

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


        <!-- Funcion mostrar tabla -->
        <script>
            function mostratTabla() {
                var dataset = new Array();

                var sum = 0;
                for (var s = 0; s < array.length; s++) {
                    var c = new Array();
                    c[0] = array[s].Id;
                    c[1] = array[s].nombre;
                    c[2] = array[s].precio;
                    c[3] = array[s].cantidad;
                    c[4] = array[s].total;
                    dataset[s] = c;
                    sum += array[s].total;
                }
                document.getElementById("TotalPagar").value = sum;

                table = $('#Tabla1').DataTable({
                    data: dataset,
                    destroy: true,
                    empty: true,
                    columns: [
                        {title: "Codigo Barra"},
                        {title: "Nombre"},
                        {title: "Precio"},
                        {title: "Cantidad"},
                        {title: "Total"}
                    ]
                });

            }

        </script>



        <!--Inicializar de Empleado -->
        <%
            if(request.getAttribute("inicio")!=null){
                String hola="'"+ (String)request.getAttribute("inicio")+"'";
                System.out.println("Entro  "+hola);
        %>
        <script>

            var jsonn = JSON.parse(<%=hola.toString()%>);

            document.getElementById("imprimir").disabled = true;

            $(document).ready(function () {
                $('#Tabla1').DataTable({
                    data: [],
                    columns: [
                        {title: "Codigo Barra"},
                        {title: "Nombre"},
                        {title: "Precio"},
                        {title: "Cantidad"},
                        {title: "Total"}
                    ]
                });
            });


        </script>
        <%
    }
        %>



        <!-- Sticky Footer-->
        <footer class="sticky-footer">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright � Slamena Market-2018</span>
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
                    <span aria-hidden="true">�</span>
                </button>
            </div>
            <div class="modal-body">Selecciona "Salir" si estas seguro de cerrar sesión.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancelar</button>
                <a class="btn btn-primary" href="ServletIndex?idEmpleado=<%=emp.getIdPersona()%>">Salir</a>
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