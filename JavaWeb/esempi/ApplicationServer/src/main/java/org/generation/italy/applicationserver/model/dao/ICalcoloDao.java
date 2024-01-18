/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.generation.italy.applicationserver.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.generation.italy.applicationserver.model.CalcolatriceModelException;
import org.generation.italy.applicationserver.model.entity.CalcoloEntityBean;

/**
 *
 * @author Jpr2
 */
public interface ICalcoloDao {

    void addCalcolo(CalcoloEntityBean calcolo) throws CalcolatriceModelException;

    List<CalcoloEntityBean> loadElencoCalcoliInDataOdierna() throws CalcolatriceModelException;

    List<CalcoloEntityBean> loadElencoCalcoliPerData(LocalDate dataCalcolo) throws CalcolatriceModelException;

    List<CalcoloEntityBean> loadElencoCalcoliPerUtenteInDataOdierna(int idUtente) throws CalcolatriceModelException;
    
}
