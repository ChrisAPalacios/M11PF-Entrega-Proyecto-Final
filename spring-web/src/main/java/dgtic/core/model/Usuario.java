package dgtic.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @NotBlank(message = "{NotBlank.usuario.nombre}")
    @Size(max = 150, message = "{Novalido.usuario.nombre}")
    @NotBlank(message = "{NotBlank.usuario.nombre}")
    @Column(name = "PILA", nullable = false, length = 150)
    private String nombre;

    @Size(max = 150, message = "{Novalido.usuario.apellidoPaterno}")
    @NotBlank(message = "{NotBlank.usuario.apellidoPaterno}")
    @Column(name = "AP_PATERNO", nullable = false, length = 150)
    private String apellidoPaterno;

    @Size(max = 150, message = "{Novalido.usuario.apellidoMaterno}")
    @NotBlank(message = "{NotBlank.usuario.apellidoMaterno}")
    @Column(name = "AP_MATERNO", length = 150)
    private String apellidoMaterno;

    @NotBlank(message = "{NotBlank.usuario.correo}")
    @Email(message = "{Email.usuario.correo}")
    @Pattern(regexp = ".*@unam\\.mx$", message = "{Pattern.usuario.correo}")
    @Column(name = "CORREO", nullable = false, unique = true, length = 150)
    private String correo;

    @NotBlank(message = "{NotBlank.usuario.telefono}")
    @Pattern(regexp = "\\d{10}", message = "{Novalido.usuario.telefono}")
    @Column(name = "TELEFONO", nullable = false, length = 10)
    private String telefono;

    @Column(name = "TIPOUSUARIO", nullable = false)
    private String tipoUsuario = "CLIENTE";

    @NotBlank(message = "{NotBlank.usuario.password}")
    @Size(min = 6, message = "{Novalido.usuario.password}")
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "usuario")
    private List<Boleto> boletos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Direccion> direcciones;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Cliente cliente;


    public Usuario() {
    }

    public Usuario(String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String telefono, String tipoUsuario, String password) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.tipoUsuario = tipoUsuario;
        this.password = password;
    }


}
