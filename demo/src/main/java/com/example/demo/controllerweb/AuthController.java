package com.example.demo.controllerweb;


import com.example.demo.service.ClubService;
import com.example.demo.service.JudokaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final JudokaService judokaService;
    private final ClubService clubService;

    public AuthController(JudokaService judokaService, ClubService clubService) {
        this.judokaService = judokaService;
        this.clubService = clubService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(HttpSession session) {
        if (session.getAttribute("username") != null) {
            String tipo = (String) session.getAttribute("tipo");
            if ("judoka".equals(tipo)) return "redirect:/judoka/home";
            if ("club".equals(tipo)) return "redirect:/club/home";
        }
        return "Model/login";
    }

    @PostMapping("/login")
    public String doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("tipo") String tipo,
            Model model,
            HttpSession session
    ) {
        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Usuario vacío");
            return "Model/login";
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "Contraseña vacía");
            return "Model/login";
        }

        boolean valido = false;

        if ("judoka".equals(tipo)) {
            valido = judokaService.validarContrasena(username, password);
        } else if ("club".equals(tipo)) {
            valido = clubService.validarContrasena(username, password);
        } else {
            model.addAttribute("error", "Tipo inválido");
            return "Model/login";
        }

        if (!valido) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "Model/login";
        }

        session.setAttribute("username", username);
        session.setAttribute("tipo", tipo);

        if ("judoka".equals(tipo)) {
            return "redirect:/judoka/home";
        } else {
            return "redirect:/club/home";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registro")
    public String showRegistro() {
        return "Model/registro";
    }
}