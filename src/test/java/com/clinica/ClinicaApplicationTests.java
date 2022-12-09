package com.clinica;

import com.clinica.model.dto.OdontologoDto;
import com.clinica.model.dto.PacienteDto;
import com.clinica.model.dto.TurnoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class ClinicaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void obtenerPacientePorIdYElPacienteNoExiste() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/paciente/" + 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("Paciente con id: 5 no existe")));
    }

    @Test
    public void obtenerOdontologoPorIdYElOdontologoNoExiste() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/odontologo/" + 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("Odontologo con id: 5 no existe")));
    }

    @Test
    public void obtenerTurnoPorIdYElTurnoNoExiste() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/turno/" + 5)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("El turno con id: 5 no existe")));
    }

    @Test
    public void crearPacienteYConsultarlo() throws Exception {

        MvcResult resultadoCrearPaciente = mvc.perform(MockMvcRequestBuilders
                        .post("/paciente/nuevo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PacienteDto(1, "1234", "Lore", "Ocampo"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        PacienteDto resultadoPaciente = objectMapper.readValue(resultadoCrearPaciente.getResponse().getContentAsString(), PacienteDto.class);


        mvc.perform(MockMvcRequestBuilders
                        .get("/paciente/" + resultadoPaciente.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.dni", is("1234")))
                .andExpect(jsonPath("$.nombre", is("Lore")))
                .andExpect(jsonPath("$.apellido", is("Ocampo")));


    }
    @Test
    public void crearOdontologoYConsultarlo() throws Exception {

        MvcResult resultadoCrearOdontologo = mvc.perform(MockMvcRequestBuilders
                        .post("/odontologo/nuevo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OdontologoDto(5, "12345", "Lorena"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        PacienteDto resultadoOdontologo = objectMapper.readValue(resultadoCrearOdontologo.getResponse().getContentAsString(), PacienteDto.class);


        mvc.perform(MockMvcRequestBuilders
                        .get("/odontologo/" + resultadoOdontologo.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.matricula", is("12345")))
                .andExpect(jsonPath("$.nombre", is("Lorena")));


    }

    @Test
    public void crearTurnoYConsultarlo() throws Exception {

        OdontologoDto odontologoDto = new OdontologoDto(2, "12345", "Lorena");
        PacienteDto pacienteDto = new PacienteDto(1, "1234", "Lore", "Ocampo");

        MvcResult resultadoCrearPaciente = mvc.perform(MockMvcRequestBuilders
                        .post("/paciente/nuevo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pacienteDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        MvcResult resultadoCrearOdontologo = mvc.perform(MockMvcRequestBuilders
                        .post("/odontologo/nuevo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(odontologoDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        MvcResult resultadoCrearTurno = mvc.perform(MockMvcRequestBuilders
                        .post("/turno/nuevo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TurnoDto(1, pacienteDto, odontologoDto, LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        TurnoDto resultadoTurno = objectMapper.readValue(resultadoCrearTurno.getResponse().getContentAsString(), TurnoDto.class);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        mvc.perform(MockMvcRequestBuilders
                        .get("/turno/" + resultadoTurno.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.odontologo.matricula", is("12345")))
                .andExpect(jsonPath("$.odontologo.nombre", is("Lorena")))
                .andExpect(jsonPath("$.paciente.dni", is("1234")))
                .andExpect(jsonPath("$.paciente.nombre", is("Lore")))
                .andExpect(jsonPath("$.paciente.apellido", is("Ocampo")))
                .andExpect(jsonPath("$.fecha", is(LocalDate.now().format(formato))));
    }
}
