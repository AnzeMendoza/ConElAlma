package com.gylgroup.conelalma.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroresController implements ErrorController {

    @RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView renderErrorPage(HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("error");
        String mensaje = "";
        int codigo = response.getStatus();

        switch (codigo) {
            case 400:
                mensaje = "EL RECURSO SOLICITADO NO EXISTE";
                break;
            case 401:
                mensaje = "NO SE ENCUENTRA AUTORIZADO";
                break;
            case 403:
                mensaje = "NO TIENE PERMISOS PARA ACCEDER AL RECURSO";
                break;
            case 404:
                mensaje = "EL RECURSO SOLICITADO NO FUE ENCONTRADO";
                break;
            case 500:
                mensaje = "OCURRIÓ UN ERROR INTERNO";
                break;
            default:
                mensaje = "ERROR INESPERADO";
                break;
        }

        mav.addObject("mensaje", mensaje);
        mav.addObject("codigo", codigo);

        return mav;
    }
}
