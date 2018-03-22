/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.tests;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.TipoIdentificacion;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSuscripciones;
import edu.eci.pdsw.samples.services.ServiciosPacientesFactory;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author hcadavid
 */
public class ServicesJUnitTest {

    public ServicesJUnitTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void clearDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "anonymous", "anonymous");
        Statement stmt = conn.createStatement();
        stmt.execute("delete from CONSULTAS");
        stmt.execute("delete from PACIENTES");
        conn.commit();
        conn.close();
    }

    /**
     * Obtiene una conexion a la base de datos de prueba
     * @return
     * @throws SQLException 
     */
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "anonymous", "anonymous");
    }
    
    @Test
    public void pruebaCeroTest() throws SQLException, ExcepcionServiciosSuscripciones {
        //Insertar datos en la base de datos de pruebas, de acuerdo con la clase
        //de equivalencia correspondiente
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();

        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (9876,'TI','Carmenzo','1995-07-10')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (1262218,'2001-01-01 00:00:00','Gracias',9876,'TI')");

        
        
        conn.commit();
        conn.close();
	
        //Realizar la operacion de la logica y la prueba
        
        
        List<Paciente> pacientes = ServiciosPacientesFactory.getInstance().getTestingForumServices().consultarPacientes();

        
        for (Paciente paciente : pacientes){
            System.out.println(paciente);
            
        }
        //assert ...
        //Assert.fail("Pruebas no implementadas aun...");
        
    }    
    @Test
    public void pruebaconsultarPacientesPorId() throws SQLException, ExcepcionServiciosSuscripciones {
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();
        
        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (12399,'CC','CesarEduardo','1995-03-03')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (12,'2001-01-01 12:30:00','DOlor en un dedo',12399,'CC')");
        
        Boolean tw=stmt.execute("Select * from CONSULTAS where PACIENTES_id=12399");
        //ResultSet io=stmt.executeQuery("Select * from CONSULTAS where PACIENTES_id=12399");
        //Array y= io.getArray(1);
        //System.out.println("1111"+y);

        conn.commit();
        conn.close();
        

        //System.out.println(y);
            
        Paciente pas=ServiciosPacientesFactory.getInstance().getTestingForumServices().consultarPacientesPorId(12399, TipoIdentificacion.CC);
        System.out.println(""+pas.getNombre());
        Assert.assertEquals(pas.getId(), 12399);
        
        
    }
    @Test
    public void consultarMenoresConEnfermedadContagiosa()throws SQLException, ExcepcionServiciosSuscripciones {
        Connection conn=getConnection();
        Statement stmt=conn.createStatement();


        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (100,'TI','Daniel','1-03-03')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (9,'2001-01-01 12:30:00','hepatitis',100,'TI')");        

        stmt.execute("INSERT INTO `PACIENTES` (`id`, `tipo_id`, `nombre`, `fecha_nacimiento`) VALUES (1200,'TI','Erick','2-03-03')");
        stmt.execute("INSERT INTO `CONSULTAS` (`idCONSULTAS`, `fecha_y_hora`, `resumen`, `PACIENTES_id`, `PACIENTES_tipo_id`) VALUES (5,'2-01-01 12:30:00','varicela',1200,'TI')");                
    
        conn.commit();
        conn.close();
        List<Paciente> pacientesEnfermos = ServiciosPacientesFactory.getInstance().getTestingForumServices().consultarMenoresConEnfermedadContagiosa();
        Assert.assertNotNull(pacientesEnfermos);

    
    }
    

}
