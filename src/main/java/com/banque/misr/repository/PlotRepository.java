package com.banque.misr.repository;

import com.banque.misr.models.Plot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends CrudRepository<Plot, Long> {
}
