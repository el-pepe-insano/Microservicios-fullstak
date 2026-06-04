package com.example.formulario.formulario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.formulario.formulario.model.Formulario;
import com.example.formulario.formulario.repository.FormularioRepository;
import java.util.List;
@Service
public class FormularioService {

    
    @Autowired
    private FormularioRepository formularioRepository;

    // Obtener todos los formularios registrados
    public List<Formulario> getFormularios() {
        return formularioRepository.findAll();
    }

    // Obtener un formulario por ID (lanzará excepción si no existe)
    public Formulario getFormulario(Long id) {
        return formularioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));
    }

    // Guardar un nuevo formulario (valores vienen del controlador o frontend)
    public Formulario saveFormulario(String consultarProducto, String problemasProducto) {
        Formulario formulario = new Formulario();
        formulario.setConsultarProducto(consultarProducto);
        formulario.setProblemasProducto(problemasProducto);
        return formularioRepository.save(formulario);
    }

    // Actualizar un formulario existente
    public Formulario updateFormulario(Long id, Formulario datosActualizados) {
        Formulario formulario = getFormulario(id); // Verifica existencia

        formulario.setConsultarProducto(datosActualizados.getConsultarProducto());
        formulario.setProblemasProducto(datosActualizados.getProblemasProducto());

        return formularioRepository.save(formulario);
    }

    // Eliminar un formulario por ID (lanza error si no existe)
    public String deleteFormulario(Long id) {
        if (!formularioRepository.existsById(id)) {
            throw new RuntimeException("El formulario no existe");
        }
        formularioRepository.deleteById(id);
        return "Formulario eliminado correctamente";
    }
   
    

}
