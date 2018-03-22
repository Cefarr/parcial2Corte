/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.entities.TipoIdentificacion;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import edu.eci.pdsw.samples.persistence.mybatisimpl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSuscripciones;
import edu.eci.pdsw.samples.services.ServiciosPaciente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class ServiciosPacienteImpl implements ServiciosPaciente {

    @Inject
    private DaoPaciente daoPaciente;


    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosSuscripciones {
        try {
            return daoPaciente.loadAll();
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosSuscripciones("Error al realizar la consulta:"+ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public Paciente consultarPacientesPorId(int id, TipoIdentificacion tipoIdentificacion) throws ExcepcionServiciosSuscripciones {
        try {
            return daoPaciente.load(id, tipoIdentificacion);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosSuscripciones("Error al realizar la consulta:"+ex.getLocalizedMessage(), ex);
        }
       
    }

    @Override
    public List<Paciente> consultarMenoresConEnfermedadContagiosa() throws ExcepcionServiciosSuscripciones {
        try {
            List<Paciente> n2=daoPaciente.loadAll();
            List<Paciente> respp= new ArrayList<Paciente>();
            
            for(int i=0; i<n2.size();i++){
                Paciente r1=n2.get(i);
                List<Consulta> co=r1.getConsultas();
                for (int ii=0; ii<co.size();ii++){
                    Consulta cor=co.get(ii);
                    int resp= cor.getResumen().indexOf("hepatitis");
                    int resp1= cor.getResumen().indexOf("varicela");
                    if(resp>0 ){
                        respp.add(r1);
                    }else if(resp1>0){
                        respp.add(r1);
                    }
                }
            }
            return respp;
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosSuscripciones("Error al realizar la consulta:"+ex.getLocalizedMessage(), ex);
        }
       
    }


    
    
}
