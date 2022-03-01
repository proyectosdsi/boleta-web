/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import sv.gob.mined.boleta.ejb.PersistenciaFacade;
import sv.gob.mined.boleta.api.model.CorreoDocente;
import sv.gob.mined.boleta.api.model.DominiosCorreo;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class DocenteController implements Serializable {

    private Boolean deshabilitar = true;
    private Boolean mostrar = true;
    private String mensaje;
    private String criterioBusqueda;
    private String correoSinDominio;
    private Integer idDominio;
    private CorreoDocente correoDocente = new CorreoDocente();
    private CorreoDocente docenteSeleccionado = new CorreoDocente();
    private List<CorreoDocente> lstCorreos = new ArrayList();

    @EJB
    private PersistenciaFacade persistenciaFacade;

    public DocenteController() {
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getExternalContext().getSessionMap().containsKey("usuario")) {

        } else {
            try {
                JsfUtil.limpiarVariableSession();
                context.getExternalContext().getSessionMap().clear();
                ExternalContext externalContext = context.getExternalContext();
                externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.mined");
                System.gc();
            } catch (IOException ex) {
                Logger.getLogger(BoletaMB.class.getName()).log(Level.SEVERE, "Error haciendo logout", ex);
            }
        }
    }

    public Boolean getMostrar() {
        return mostrar;
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCorreoSinDominio() {
        return correoSinDominio;
    }

    public void setCorreoSinDominio(String correoSinDominio) {
        this.correoSinDominio = correoSinDominio;
    }

    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }

    public CorreoDocente getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(CorreoDocente correoDocente) {
        this.correoDocente = correoDocente;
    }

    public CorreoDocente getDocenteSeleccionado() {
        return docenteSeleccionado;
    }

    public void setDocenteSeleccionado(CorreoDocente docenteSeleccionado) {
        this.docenteSeleccionado = docenteSeleccionado;
    }

    public Integer getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(Integer idDominio) {
        this.idDominio = idDominio;
    }

    public List<DominiosCorreo> getLstDominiosCorreo() {
        return persistenciaFacade.getLstDominiosCorreo();
    }

    public List<CorreoDocente> getLstCorreoDocente() {
        return lstCorreos;
    }

    public void buscar() {
        lstCorreos = persistenciaFacade.getLstCorreoDocenteByCriterio(criterioBusqueda);
        if (lstCorreos.isEmpty()) {
            mensaje = "No se han encontrado coincidencias";
        } else {
            mensaje = "";
        }

    }

    public void cerrarDlg() {
        correoDocente = docenteSeleccionado;
        correoSinDominio = correoDocente.getCorreoElectronico().split("@")[0];
        switch (correoDocente.getCorreoElectronico().split("@")[1]) {
            case "@docentes.edu.sv":
                idDominio = 1;
                break;
            case "@docentes.mined.edu.sv":
                idDominio = 2;
                break;
            case "@admin.mined.edu.sv":
                idDominio = 3;
                break;
        }
    }

    public void guardarDocente() {
        FacesContext context = FacesContext.getCurrentInstance();
        correoDocente.setPrimerNombre(correoDocente.getPrimerNombre() != null ? correoDocente.getPrimerNombre().toUpperCase() : null);
        correoDocente.setPrimerApellido(correoDocente.getPrimerApellido() != null ? correoDocente.getPrimerApellido().toUpperCase() : null);
        correoDocente.setCorreoElectronico(correoSinDominio.concat(getDominioCorreo(idDominio)).toLowerCase());
        int valor = persistenciaFacade.guardarDocente(correoDocente, context.getExternalContext().getSessionMap().get("usuario").toString());
        if (valor == 1) {
            JsfUtil.mensajeUpdate();
        } else {
            JsfUtil.mensajeAlerta("Ya existe un usuario con el NIP o correo electronico ingresado");
        }
    }

    private String getDominioCorreo(Integer idDominio) {
        switch (idDominio) {
            case 1:
                return "@docentes.edu.sv";
            case 2:
                return "@docentes.mined.edu.sv";
            case 3:
                return "@admin.mined.edu.sv";
            default:
                return "";
        }
    }

    public void nuevoDocente() {
        correoDocente = new CorreoDocente();
        mostrar = true;
        criterioBusqueda = "";
        lstCorreos.clear();
        correoSinDominio = "";
        deshabilitar = false;
    }

    public void modificarDocente() {
        deshabilitar = true;
        mostrar = false;
        criterioBusqueda = "";
        lstCorreos.clear();
    }
}
