/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import sv.gob.mined.seguridad.api.UsuarioFacade;
import sv.gob.mined.seguridad.model.Usuario;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class LoginController implements Serializable {

    private String username;
    private String password;

    @EJB
    private UsuarioFacade usuarioFacade;
   
    public LoginController() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isUsuarioValido() {
        try {
            Usuario usu = usuarioFacade.findUsuarioByLogin(username, password);
            if (usu != null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("usuario", username);
                context.getExternalContext().getSessionMap().put("clave", password);
                return "/app/menu.mined?faces-redirect=true";
            } else {
                JsfUtil.mensajeAlerta("Usuario/Password equivocados");
            return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
