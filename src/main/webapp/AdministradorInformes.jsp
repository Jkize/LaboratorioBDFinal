<%-- 
    Document   : AdministradorInformes
    Created on : 4/11/2018, 08:19:17 PM
    Author     : Sebastian__________--
--%>
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

    <!-- CHARTS-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>

    <!-- TABLES-->
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    
    <!-- Bootstrap core CSS-->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

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
        <li class="nav-item">
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
        <li class="nav-item active">
          <a class="nav-link" href="AdministradorInformes.jsp">
            <i class="fas fa-fw fa-chart-area"></i>
            <span>Informes</span></a>
        </li>
      </ul>

      <div id="content-wrapper">
        <!-- EMPEZAR A ESCRIBIR Y/O EDITAR DENTRO DE ESTE DIV -->
        <div class="container-fluid">

      
            <div style="background-color: #343a40" class="row">
               <div class="col-sm-4">
                <ol style="background-color: #343a40" class="breadcrumb">
            <h6 style="color: white">INGRESAR LA FECHA DEL REPORTE:</h6>
            </li>
             </ol>
                </div>
                <div class="col-sm-4">
                  <ol style="background-color: #343a40" class="breadcrumb">
                  <select style="width:200px" class="form-control" id="sel1">
                  <option>Mes</option>
                  <option>Enero</option>
                  <option>Febrero</option>
                  <option>Marzo</option>
                  <option>Abril</option>
                  <option>Mayo</option>
                  <option>Junio</option>
                  <option>Agosto</option>
                  <option>Septiembre</option>
                  <option>Octubre</option>
                  <option>Noviembre</option>
                  <option>Diciembre</option>
                  </select>
                  </ol>
                </div>

                <div class="col-sm-4">
                  <ol style="background-color: #343a40" class="breadcrumb">
                  <select style="width:200px" class="form-control" id="sel2">
                  <option>Año</option>
                  <option>2018</option>
                  <option>2019</option>
                  <option>2020</option>
                  <option>2021</option>
                  <option>2022</option>
                  <option>2023</option>
                  <option>2024</option>
                  <option>2025</option>
                  </select>
                  </ol>
                </div>
            </div>
        
            <br>

            <!-- Breadcrumbs I -->
          <ol style="background-color:#007bff" class="breadcrumb">
            <li style="color:white" class="breadcrumb-item active">VENTAS POR PRODUCTO</li>
          </ol>

          <!-- FILA I-->

          <div class="row">
            <div class="col-md-7">
              <div class="card mb-6">
                <div class="card-body">
                  <table id="Tabla1" class="display" width="100%"></table>
                </div>
              </div>  
            </div>

            <div class="col-md-5">
              <div class="card mb-6">
                <div class="card-body">
                  <canvas id="pie-chart" width="800" height="450"></canvas>
                </div>
              </div>  
            </div>            
        </div><br>

          <!-- FILA II-->


           <!-- Breadcrumbs II -->
          <ol style="background-color:#ffc107" class="breadcrumb">
            <li style="color:white" class="breadcrumb-item active">VENTAS POR CADA VENDEDOR</li>
          </ol>


           <div class="row">
            <div class="col-md-7">
              <div class="card mb-6">
                <div class="card-body">
                   <table id="Tabla2" class="display" width="100%"></table>
                </div>
              </div>  
            </div>

            <div class="col-md-5">
              <div class="card mb-6">
                <div class="card-body">
                  <canvas id="bar-chart-grouped" width="800" height="450"></canvas>
                </div>
              </div>  
            </div>            
        </div><br>

         <!-- FILA III-->


        <!-- Breadcrumbs III -->
          <ol style="background-color:#28a745" class="breadcrumb">
            <li style="color:white" class="breadcrumb-item active">EVOLUCIÓN DE VENTAS</li>
          </ol>

           <div class="row">
            <div class="col-md-7">
              <div class="card mb-6">  
                <div class="card-body">
                     <table id="Tabla3" class="display" width="100%"></table>
                </div>
              </div>  
            </div>

            <div class="col-md-5">
              <div class="card mb-6">
                <div class="card-body">
                  <canvas id="line-chart" width="800" height="450"></canvas>
                </div>
              </div>  
            </div>            
        </div><br>
        
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


     <script>
        //TABLA I
        new Chart(document.getElementById("pie-chart"), {
          type: 'pie',
          data: {
            labels: ["Africa", "Asia", "Europe", "Latin America", "North America"],
            datasets: [{
              label: "Population (millions)",
              backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850"],
              data: [10,10,10,10,10]
            }]
          },
          options: {
            title: {
              display: true,
              text: 'VENTAS POR PRODUCTO'
            }
          }
      });

      var dataSet1 = [
          [ "Tiger Nixon", "System Architect", "Edinburgh"],
          [ "Unity Butler", "Marketing Designer", "San Francisco"]
      ];
       
      $(document).ready(function() {
          $('#Tabla1').DataTable( {
              data: dataSet1,
              columns: [
                  { title: "Producto" },
                  { title: "Cantidad" },
                  { title: "Porcentaje" }
              ]
          } );
      } );

       //TABLA II
      new Chart(document.getElementById("bar-chart-grouped"), {
          type: 'bar',
          data: {
            labels: ["1900", "1950", "1999", "2050"],
            datasets: [
              {
                label: "Africa",
                backgroundColor: "#3e95cd",
                data: [133,221,783,2478]
              }, {
                label: "Europe",
                backgroundColor: "#8e5ea2",
                data: [408,547,675,734]
              }
            ]
          },
          options: {
            title: {
              display: true,
              text: 'VENTAS POR PERIODO'
            }
          }
      });

       var dataSet2 = [
          [ "Tiger Nixon", "System Architect", "Edinburgh"],
          [ "Unity Butler", "Marketing Designer", "San Francisco"]
      ];
       
      $(document).ready(function() {
          $('#Tabla2').DataTable( {
              data: dataSet2,
              columns: [
                  { title: "Vendedor" },
                  { title: "Total de Ventas" },
                  { title: "Porcentaje" }
              ]
          } );
      } );


       //TABLA III
      new Chart(document.getElementById("line-chart"), {
        type: 'line',
        data: {
          labels: [1500,1600,1700,1750,1800,1850,1900,1950,1999,2050],
          datasets: [{ 
              data: [86,114,106,106,107,111,133,221,783,2478],
              label: "Africa",
              borderColor: "#3e95cd",
              fill: false
            }, { 
              data: [282,350,411,502,635,809,947,1402,3700,5267],
              label: "Asia",
              borderColor: "#8e5ea2",
              fill: false
            }, { 
              data: [168,170,178,190,203,276,408,547,675,734],
              label: "Europe",
              borderColor: "#3cba9f",
              fill: false
            }, { 
              data: [40,20,10,16,24,38,74,167,508,784],
              label: "Latin America",
              borderColor: "#e8c3b9",
              fill: false
            }, { 
              data: [6,3,2,2,7,26,82,172,312,433],
              label: "North America",
              borderColor: "#c45850",
              fill: false
            }
          ]
        },
        options: {
          title: {
            display: true,
            text: 'VENTAS POR MES'
          }
        }
      });


      var dataSet3 = [
          [ "Tiger Nixon", "System Architect"],
          [ "Unity Butler", "Marketing Designer"]
      ];
       
      $(document).ready(function() {
          $('#Tabla3').DataTable( {
              data: dataSet3,
              columns: [
                  { title: "Mes" },
                  { title: "Ventas Mes" }
              ]
          } );
      } );

      </script>




    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <script src="vendor/datatables/jquery.dataTables.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin.min.js"></script>



  </body>

</html>
<%}%>