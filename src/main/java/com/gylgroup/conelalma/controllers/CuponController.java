
import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cupon")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping("/")
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("cupones");
        mav.addObject("cupones", cuponService.findAll());
        mav.addObject("cuponesActivos", cuponService.findAllAndEstado(true));
        return mav;
    }

    @GetMapping("/agregar")
    public ModelAndView cuponCreate() {
        ModelAndView mav = new ModelAndView("cupon-formulario");
        mav.addObject("cupon", new Cupon());
        mav.addObject("title", "Crear cupon");
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String cuponSave(@Valid Cupon cupon,
                            BindingResult result,
                            Model model) {
        if(result.hasErrors()){
            model.addAttribute("action", "agregar");
            return "cupon-formulario";
        }
        cuponService.save(cupon);
        return "redirect:/cupon/";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id) throws Exception {
        ModelAndView mav = new ModelAndView("cupon-formulario");
        if(cuponService.existsById(id)){
            mav.addObject("cupon", cuponService.findById(id));
            mav.addObject("action", "editar/"+id);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String cuponUpdate(@PathVariable Integer id,
                              @Valid Cupon cupon,
                              BindingResult result,
                              Model model) {
        try {
            if(result.hasErrors()){
                model.addAttribute("action", "editar/"+id);
                return "cupon-formulario";
            }
            cuponService.update(id, cupon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/cupon/";
    }

    @GetMapping("/activar/{id}")
    public RedirectView activar(@PathVariable("id") int id) {
        cuponService.enable(id);
        return new RedirectView("/cupon/");
    }

    @GetMapping("/desactivar/{id}")
    public RedirectView desactivar(@PathVariable("id") int id) {
        cuponService.disable(id);
        return new RedirectView("/cupon/");
    }
}
