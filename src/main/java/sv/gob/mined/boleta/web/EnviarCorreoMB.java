/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.boleta.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import sv.gob.mined.boleta.ejb.LeerBoletasFacade;
import sv.gob.mined.boleta.ejb.SeparacionBoletasFacade;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class EnviarCorreoMB implements Serializable {

    //private String codigoUltimo;
    private String mensajeCorreo;
    private String tituloCorreo;

    private Session mailSession;

    private String usuario = "";
    private String clave = "";
    private String codDepa = "";
    private String nombreArchivo = "";
    private String mes;
    private String anho;
    private String mesAnho = "12_2019";

    @EJB
    private LeerBoletasFacade leerBoletasEJB;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM_yyyy");

    @EJB
    private SeparacionBoletasFacade sbf;

    @PostConstruct
    public void init() {
        //mesAnho = "12_2019";
        mes = sdf.format(new Date()).split("_")[0];
        anho = sdf.format(new Date()).split("_")[1];
    }

    public String getMensajeCorreo() {
        return mensajeCorreo;
    }

    public void setMensajeCorreo(String mensajeCorreo) {
        this.mensajeCorreo = mensajeCorreo;
    }

    public String getTituloCorreo() {
        return tituloCorreo;
    }

    public void setTituloCorreo(String tituloCorreo) {
        this.tituloCorreo = tituloCorreo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    /*public void enviarCorreos() {
        //leerBoletasEJB.leerArchivosPendientes(getMailSession(), codDepa, usuario);
    }*/

    private Session getMailSession() {
        Properties configEmail = new Properties();

        configEmail.put("mail.smtp.auth", "true");
        configEmail.put("mail.smtp.starttls.enable", "true");

        configEmail.put("mail.smtp.host", "smtp.office365.com");
        configEmail.put("mail.smtp.port", "587");

        configEmail.put("mail.user", usuario);
        configEmail.put("mail.user.pass", clave);
        configEmail.put("mail.from", usuario);

        mailSession = Session.getInstance(configEmail, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        return mailSession;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getCodDepa() {
        return codDepa;
    }

    public void setCodDepa(String codDepa) {
        this.codDepa = codDepa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /*public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }*/
    public Properties chargeEmailsProperties(String nombre) {
        Properties info = null;
        try {
            info = new Properties();
            try (InputStream input = EnviarCorreoMB.class.getClassLoader().getResourceAsStream(nombre + ".properties")) {
                info.load(input);
            }

        } catch (IOException ex) {
            Logger.getLogger(EnviarCorreoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;
    }

    public void separacionDeBoletas() {
        codDepa = usuario.substring(7, 9);
        Properties info = chargeEmailsProperties("cuenta_office365");
        clave = info.getProperty("clave_".concat(codDepa));

        System.out.println(mes + " - " + anho + " - " + clave);

        mesAnho = mes.concat("_").concat(anho);
        sbf.separacion(mesAnho, codDepa);
    }

    public void enviarUnSoloCorreo() {
        codDepa = usuario.substring(7, 9);
        Properties info = chargeEmailsProperties("cuenta_office365");
        clave = info.getProperty("clave_".concat(codDepa));

        System.out.println(mes + " - " + anho + " - " + clave);

        mesAnho = mes.concat("_").concat(anho);
        leerBoletasEJB.enviarUnSoloCorreo(codDepa, mesAnho, getMailSession(), usuario, mensajeCorreo, tituloCorreo);
    }

    /*public void enviarUrl() {
        envioDeBoletasFacade.enviarBoletasDePago(codDepa, mesAnho);
    }

    public void enviarBoleta() {
        envioDeBoletasFacade.enviarBoletasDePagoPdf(codDepa, mesAnho);
    }*/
}
