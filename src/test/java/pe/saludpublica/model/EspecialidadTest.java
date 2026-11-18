package pe.saludpublica.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadTest {

    @Test
    @DisplayName("Crear una especialidad con datos válidos")
    void shouldCreateSpecialtyWithValidData() {
        Especialidad especialidad = new Especialidad("Cardiología",
                "Diagnóstico y tratamiento de enfermedades del corazón");

        assertEquals("Cardiología", especialidad.getNombre());
        assertEquals("Diagnóstico y tratamiento de enfermedades del corazón",
                especialidad.getDescripcion());
    }
}