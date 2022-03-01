/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.paradise.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import sv.gob.mined.utils.jsf.JsfUtil;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {

    private String theme = "blue";

    private String layout = "default";

    private boolean overlayMenu = false;

    private boolean slimMenu = false;

    private boolean darkMenu = false;

    public String getTheme() {
        return theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isOverlayMenu() {
        return this.overlayMenu;
    }

    public void setOverlayMenu(boolean value) {
        this.overlayMenu = value;
        this.slimMenu = false;
    }

    public boolean isSlimMenu() {
        return this.slimMenu;
    }

    public void setSlimMenu(boolean value) {
        this.slimMenu = value;
    }

    public boolean isDarkMenu() {
        return this.darkMenu;
    }

    public void setDarkMenu(boolean value) {
        this.darkMenu = value;
    }

    public void logout() {
        try {
            JsfUtil.limpiarVariableSession();
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().clear();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.redirect(((ServletContext) externalContext.getContext()).getContextPath() + "/index.mined");
            System.gc();
        } catch (IOException ex) {
            Logger.getLogger(GuestPreferences.class.getName()).log(Level.SEVERE, "Error haciendo logout", ex);
        }
    }
}
