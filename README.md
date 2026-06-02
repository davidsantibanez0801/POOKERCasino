# POOKER Casino ♠

Aplicación de escritorio desarrollada en Java Swing para la administración de usuarios de un casino. Implementa el patrón de diseño **Modelo-Vista-Controlador (MVC)** como proyecto final de Programación Orientada a Objetos — FES Aragón, UNAM 2026.

---

## Requisitos

- Java 17 o 21 (LTS recomendado)
- Maven 3.6+
- Dependencia: Lombok 1.18.44 (incluida en `pom.xml`)

---

## Estructura del proyecto

```
POOKERCasino/
├── src/main/java/mx/unam/aragon/ico/pooker/
│   ├── Main.java                          # Punto de entrada
│   ├── model/
│   │   └── UsuarioCasino.java             # Modelo de datos
│   ├── view/
│   │   └── VentanaPrincipal.java          # Interfaz gráfica (Swing)
│   ├── controller/
│   │   └── UsuarioController.java         # Lógica de negocio y eventos
│   └── persistence/
│       └── ArchivoManager.java            # Lectura y escritura CSV
├── usuarios.csv                           # Archivo de datos (5 registros incluidos)
└── pom.xml
```

---

## Arquitectura MVC

| Capa | Clase | Responsabilidad |
|---|---|---|
| **Model** | `UsuarioCasino` | Representa los datos de un usuario del casino |
| **View** | `VentanaPrincipal` | Interfaz gráfica, formulario, tabla y menús |
| **Controller** | `UsuarioController` | Validaciones, CRUD y coordinación con persistencia |
| **Persistence** | `ArchivoManager` | Guardar y cargar usuarios en archivo CSV |

El flujo de datos es siempre: `View → Controller → Model / ArchivoManager`.  
La vista nunca accede directamente al archivo.

---

## Modelo de datos — `UsuarioCasino`

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | int | Identificador único autoincremental |
| `nombre` | String | Nombre del usuario |
| `apellido` | String | Apellido del usuario |
| `edad` | int | Edad (mínimo 18 años) |
| `juegoFavorito` | String | Poker, Blackjack, Ruleta, Slots, Baccarat |
| `experiencia` | String | Principiante / Intermedio / Experto |
| `horario` | String | Mañana / Tarde / Noche |
| `servicios` | String | VIP, Bebidas, Torneos (combinables) |
| `notas` | String | Observaciones adicionales |

---

## Funcionalidades

### CRUD de usuarios
- **Registrar** — captura los datos del formulario y los guarda en el CSV automáticamente.
- **Modificar** — selecciona un usuario de la tabla, edita sus datos y confirma.
- **Eliminar** — selecciona un usuario y confirma la eliminación con un diálogo.
- **Limpiar** — reinicia el formulario sin afectar los datos guardados.

### Validaciones
- Nombre y apellido no pueden estar vacíos.
- La edad debe ser un número entre 18 y 120 años.
- El campo edad debe contener solo dígitos (muestra error si se ingresa texto).

### Reloj en tiempo real
Muestra la hora actual del sistema en la esquina superior derecha, actualizada cada segundo mediante un `Thread` con `setDaemon(true)`.

### Menú Archivo
| Opción | Descripción |
|---|---|
| Guardar como... | `JFileChooser` para exportar los usuarios a cualquier ruta en formato CSV |
| Cargar desde... | `JFileChooser` para importar un CSV externo (filtrado por extensión `.csv`) |
| Salir | Cierra la aplicación |

### Menú Vista
| Opción | Descripción |
|---|---|
| Color de fondo... | `JColorChooser` para personalizar el color del panel principal |
| Color de tabla... | `JColorChooser` para personalizar el color de fondo de la tabla |

---

## Componentes Swing utilizados

`JFrame` `JPanel` `JLabel` `JButton` `JTextField` `JTextArea` `JComboBox`
`JTable` `JMenuBar` `JMenu` `JMenuItem` `JRadioButton` `JCheckBox`
`JFileChooser` `JColorChooser` `JScrollPane` `JOptionPane`

---

## Persistencia

Los datos se almacenan en `usuarios.csv` en el directorio raíz del proyecto.  
El formato de cada línea es:

```
id,nombre,apellido,edad,juegoFavorito,experiencia,horario,servicios,notas
```

El archivo se crea automáticamente al registrar el primer usuario. Al iniciar la aplicación, los datos se cargan automáticamente y el ID retoma desde el último registrado.

---

## Cómo ejecutar

### Con Maven
```bash
mvn compile
mvn exec:java -Dexec.mainClass="mx.unam.aragon.ico.pooker.Main"
```

### Desde IntelliJ IDEA
1. Abrir el proyecto como proyecto Maven.
2. Esperar a que se descarguen las dependencias (Lombok).
3. Ejecutar `Main.java`.

---

## Dependencias

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.44</version>
</dependency>
```

Lombok genera automáticamente los métodos `getters`, `setters`, `constructor` y `toString` del modelo mediante las anotaciones `@Data`, `@AllArgsConstructor` y `@NoArgsConstructor`.

---

## Notas adicionales

- El plugin de Lombok debe estar habilitado en el IDE (`Settings → Plugins → Lombok`).
- Se recomienda usar **Java 21 LTS**. Cambiar en `pom.xml`:
  ```xml
  <maven.compiler.source>21</maven.compiler.source>
  <maven.compiler.target>21</maven.compiler.target>
  ```
- El archivo `usuarios.csv` incluido contiene 5 registros de muestra para la entrega.
