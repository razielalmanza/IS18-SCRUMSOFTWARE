package com.mx.fciencias.scrumsoftware.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  La clase <code>SesionFilter</code> define objetos que aseguran paginas para que no
 * sean usadas si no se ha iniciado sesion dentro de la aplicacion.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class SesionFilter implements Filter {

	// Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public SesionFilter() {
    }

    // Metodos de implementacion.
    /**
     * Ceea el filtro definido por los objetos de esta clase.
     * @param request - La solicitud de servlet que estamos procesando.
     * @param response - La respuesta de servlet que estamos creando.
     * @param chain - La cadena del filtro que estamos procesando.
     */
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
        HttpServletRequest req = ( HttpServletRequest ) request;
        HttpServletResponse res = ( HttpServletResponse ) response;
        HttpSession session = req.getSession( true );
        String pageRequest = req.getRequestURL().toString();
        if ( session.getAttribute( "usuario" ) == null ) {
        	res.sendRedirect( req.getContextPath() + "/PrincipalVisitanteIH.xhtml" );
            return;
        }
        chain.doFilter( request, response );

    }

    @Override
    public void init( FilterConfig fc ) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
