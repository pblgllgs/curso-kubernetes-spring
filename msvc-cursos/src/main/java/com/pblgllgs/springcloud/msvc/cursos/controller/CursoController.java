package com.pblgllgs.springcloud.msvc.cursos.controller;

import com.pblgllgs.springcloud.msvc.cursos.entity.Curso;
import com.pblgllgs.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;


    @GetMapping("/")
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> o = cursoService.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        Curso cursoDB = cursoService.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional <Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            Curso cursoDB = o.get();
            cursoDB.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional <Curso> o = cursoService.porId(id);
        if(o.isPresent()){
            cursoService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo "+ err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
