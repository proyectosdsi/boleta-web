/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import javax.servlet.http.HttpServlet;
//import sv.gob.mined.boleta.ejb.ObtenerArchivoFacade;

/**
 *
 * @author misanchez
 */
public class DescargarBoleta extends HttpServlet {

    private final int ARBITARY_SIZE = 1048;
    /*@EJB
    private ObtenerArchivoFacade obtenerArchivoFacade;*/

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get image file.
        String codigoGenerado = request.getParameter("codigoGenerado");

        try {
            File boleta = obtenerArchivoFacade.obtenerArchivo(codigoGenerado);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + codigoGenerado + ".pdf");

            try (InputStream in = new BufferedInputStream(new FileInputStream(boleta));
                    OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
        } catch (IOException ex) {
            /*PrintWriter out;
            String title = "Error en la descarga de la boleta";

            // primero selecciona el tipo de contenidos y otros campos de cabecera de la respuesta
            response.setContentType("text/html");
            // Luego escribe los datos de la respuesta
            out = response.getWriter();
            out.println("<HTML><HEAD><TITLE>");
            out.println(title);
            out.println("</TITLE></HEAD><BODY>");
            out.println("<H1>" + title + "</H1>");
            out.println("<P>Por favor escribir un correo para notificar de su situación</P>");
            out.println("<br/>");
            out.println("<a href=\"mailto:boleta@mined.edu.sv\">Admin Boletas</a>");
            out.println("</BODY></HTML>");
            out.close();*/
            //Logger.getLogger(DescargarBoleta.class.getName()).log(Level.SEVERE, "Se ha generado de error en la descarga de la boleta: {0}", codigoGenerado);
            //Logger.getLogger(DescargarBoleta.class.getName()).log(Level.SEVERE, null, ex);
        /*}
    */
}
