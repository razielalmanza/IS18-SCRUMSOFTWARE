package com.mx.fciencias.scrumsoftware;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  La clase <code>Servlet/code> define objetos para utilizar servlets en la apicacion.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
@SuppressWarnings( "serial" )
public class Servlet extends HttpServlet {

    // Metodos de implementacion.
    /**
     * Funcionamiento del servlet.
     * @param request - La solicitud de servlet que estamos procesando.
     * @param response - La respuesta de servlet que estamos creando.
     */
    private void foo( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        response.setContentType( "text/html" );
        PrintWriter pw = response.getWriter();
        pw.println( "<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>" );
        pw.println( "<BODY BGCOLOR=\"#CCBBAA\">" );
        pw.println( "<H2>Leyendo parámetros desde un formulario html</H2><P>" );
        pw.println( "<UL>\n" );
        pw.println( "</BODY></HTML>" );
        pw.close();
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        foo( request, response );
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        foo( request, response );
    }
}
