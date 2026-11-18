package pe.saludpublica.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    @DisplayName("Crear un doctor con datos válidos")
    void shouldCreateDoctorWithValidData() {
        Doctor doctor = new Doctor("40123456", "Carlos", "Ramírez",
                "CMP-12345", "esp-001");

        assertEquals("40123456", doctor.getDocumento());
        assertEquals("Carlos", doctor.getNombres());
        assertEquals("Ramírez", doctor.getApellidos());
        assertEquals("CMP-12345", doctor.getColegiatura());
        assertEquals("esp-001", doctor.getEspecialidad());
    }

    @Test
    @DisplayName("Rechazar la creación de un doctor sin documento")
    void shouldRejectDoctorWithoutDocument() {
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor(null, "Carlos", "Ramírez", "CMP-12345", "esp-001"));
    }

    @Test
    @DisplayName("Rechazar la creación de un doctor sin nombres")
    void shouldRejectDoctorWithoutFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("40123456", null, "Ramírez", "CMP-12345", "esp-001"));
    }

    @Test
    @DisplayName("Rechazar la creación de un doctor sin apellidos")
    void shouldRejectDoctorWithoutLastName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Doctor("40123456", "Carlos", null, "CMP-12345", "esp-001"));
    }
}