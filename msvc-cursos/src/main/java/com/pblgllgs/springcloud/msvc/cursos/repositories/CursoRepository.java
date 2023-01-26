package com.pblgllgs.springcloud.msvc.cursos.repositories;

import com.pblgllgs.springcloud.msvc.cursos.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {
}
