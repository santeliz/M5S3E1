/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.controladores;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author JAVA
 */

@ManagedBean
@ViewScoped
public class ReportesControl implements Serializable {

    Connection con = null;
    String DATASOURCE_CONTEXT = "java:app/jdbc/Factura";
    JasperPrint jasperPrint;
    HttpServletResponse httpServletResponse = null;
    private Date fechaInicio;
    private Date fechaFin;
    
    @PostConstruct
    public void init() {
        try {
            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            if (datasource != null) {
                con = datasource.getConnection();
            }
        } catch (NamingException | SQLException e) {
            System.out.println("Error al Buscar Datasource");
        }
    }
    public void generarReporte() throws JRException, IOException {
        
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String reportPath = sc.getRealPath("reportes/VentasGlobales/VentasGlobales.jasper");
        Map<String,Object> parametros = new HashMap<String,Object>();
        jasperPrint = JasperFillManager.fillReport(reportPath, parametros, con);
        httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Context-Disposition", "inline; filename=VentasGlobales.pdf");
        ServletOutputStream servletoutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletoutputStream);

        
        FacesContext.getCurrentInstance().responseComplete();
        
    }    
    public void generarReporteFiltrado() throws JRException, IOException {
        
        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String reportPath = sc.getRealPath("reportes/VentasFechas/VentasFechas.jasper");
        String logoPath = sc.getRealPath("resources/images/VentasGlobales.jpg");
        Map<String,Object> parametros = new HashMap<String,Object>();
        parametros.put("fechaInicio", fechaInicio);
        parametros.put("fechaFin", fechaFin);
        parametros.put("logo", logoPath);
        jasperPrint = JasperFillManager.fillReport(reportPath, parametros, con);
        httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Context-Disposition", "inline; filename=VentasFechas.pdf");
        ServletOutputStream servletoutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletoutputStream);

        
        FacesContext.getCurrentInstance().responseComplete();
        
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
}
