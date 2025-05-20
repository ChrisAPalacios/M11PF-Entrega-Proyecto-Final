# 📘 Manual de Usuario - Eventia

---

## 🧱 Inicialización de la Base de Datos

Antes de ejecutar la aplicación, es necesario cargar el script de la base de datos. Este script contiene toda la estructura y datos esenciales para el funcionamiento de Eventia.

### Contenido del Script:

- **Tablas necesarias:** Incluye todas las entidades clave como `USUARIO`, `CLIENTE`, `EVENTO`, `CATEGORIA`, `ZONA`, `ASIENTO`, `BOLETO`, entre otras.
- **Usuario Administrador:** Se crea un usuario administrador con toda la información necesaria (incluida la contraseña) para iniciar sesión por primera vez.
- **Datos precargados:**
  - Alcaldías y colonias
  - Eventos de ejemplo
  - Categorías de eventos
  - Zonas para conciertos

Esto permite que la aplicación ya cuente con información básica para visualizar y probar su funcionalidad desde el primer momento.

---

## 🚀 Ejecución del Script y Lanzamiento de Eventia

1. Ejecutar el script SQL en el motor de base de datos **MariaDB**.
2. Ejecutar la aplicación Spring Boot (desde tu IDE o usando):

   ```bash
   ./mvnw spring-boot:run
   ```

3. Acceder a Eventia desde el navegador ingresando:

   ```
   http://localhost:8080
   ```

---

## 🔐 Acceso como Administrador y Configuración Inicial

1. Haz clic en **"Iniciar sesión"**.
2. Ingresa con la cuenta de administrador creada por el script.
3. Dirígete al módulo de administración de eventos.
4. Desde ahí, podrás **asignar pósters** a cada evento.

> Es importante subir una imagen por evento para mejorar la visualización en la vista del cliente.

---

## 🛠️ Panel de Administración

Una vez iniciada la sesión como administrador, se accede al panel desde el cual se pueden gestionar:

- **Eventos:** Crear, editar, eliminar y asignar pósters.
- **Asientos:** Generar y administrar asientos por zona para cada evento.
- **Categorías:** Crear y gestionar categorías de eventos.
- **Usuarios:** Consultar, editar o eliminar usuarios registrados.

---

## 🖼️ Gestión de Eventos y Asignación de Pósters

1. Dirígete a la sección **"Eventos"**.
2. Las imágenes están en:

   ```
   /src/main/resources/static/images/poster/
   ```

3. Para cada evento:
   - Haz clic en **"Añadir Póster"**
   - Selecciona la imagen
   - Guarda los cambios

> Si ya tienen imagen asignada, no es necesario modificarla.

---

## ➕ Creación de Nuevos Eventos

1. Ve al panel de administración.
2. Entra en la sección **"Eventos"**.
3. Haz clic en **"Nuevo Evento"**.
4. Completa:
   - Nombre del evento
   - Fecha
   - Duración
   - Tipo de evento
5. Haz clic en **"Guardar"**.

---

## 🏟️ Gestión de Zonas

1. Desde el menú principal, ve a **"Zonas"**.
2. Se mostrará la lista de zonas existentes.

---

## 🔄 Flujo de Trabajo en la Sección de Zonas

Para una correcta funcionalidad:

### 🔧 Editar Zona
Permite cambiar el **nombre** y **precio** de la zona.

### 🪑 Ver Asientos
1. Inicialmente estará vacío.
2. Haz clic en **"Generar Asientos"**.
3. Se generarán según la cantidad definida y se asignará una letra.

> Haz esto **antes** de usar el botón gris de "Generar Asientos".

### 🧩 Generar Asientos en Eventos

1. Ejecuta esta acción luego de generar los asientos en cada zona.
2. Asignará asientos automáticamente a los eventos.
3. El botón quedará deshabilitado tras su uso.

> También puedes eliminar una zona si es necesario.  
> Si se crea un nuevo evento, los asientos se asignarán automáticamente si ya fueron generados.

---

## 📌 Consideraciones Finales sobre Zonas

Datos requeridos:

- Nombre de la zona
- Costo por boleto
- Capacidad (total de asientos)

> - No uses capacidades demasiado altas (por límite de registros).
> - La capacidad no se puede modificar después de creada la zona.

---

## 👥 Gestión de Usuarios

Desde la sección **"Usuarios"** puedes ver:

- Información personal
- Boletos adquiridos

Permite mantener un control general sobre la actividad del sistema.

---

## 🗂️ Tipos de Eventos

En esta sección puedes:

- Agregar nuevos tipos de evento
- Evitar duplicados (valida que no existan previamente)

Ayuda a organizar y clasificar correctamente los eventos.

---

## 📝 Registro de Nuevos Usuarios y Acceso

1. Haz clic en **"Cerrar Sesión"**.
2. Regresa al **Login**.
3. Elige **"Crear nuevo usuario"**.
4. Llena todos los campos requeridos.

> Si todo es válido, se creará el nuevo usuario y podrá iniciar sesión.

---

## 👤 Interfaz del Cliente y Compra de Boletos

Al iniciar sesión como cliente, puedes:

- Ver eventos disponibles con su información
- Comprar boletos
- Seleccionar zona, cantidad y asientos disponibles
- Finalizar la compra

---

## 🧺 Sección "Mi Cuenta"

Incluye las siguientes opciones:

### 📄 Mi Perfil
Muestra la información del usuario.

### 🛒 Canasta
Boletos seleccionados antes del pago.  
> Se vacía si el usuario cierra sesión.

### 🎟️ Mis Boletos
Historial de compras.  
> Permite devolver boletos si se desea.

---

## 🎫 Compra de Boletos

Disponible para todos los eventos activos desde la interfaz del cliente.

---

## 🧭 Proceso de Selección y Compra

1. Elegir zona
2. Seleccionar cantidad de asientos
3. Elegir los asientos disponibles

> Solo pueden seleccionarse **asientos disponibles**.

---

## 🛒 Canasta de Boletos

Una vez seleccionados, los boletos se añaden a la **canasta**.

Opciones disponibles:

- Seguir comprando
- Proceder al pago

> No se permiten duplicados.  
> Se vacía si el usuario cierra sesión sin pagar.

---

## 💳 Proceso de Pago

Al hacer clic en **"Proceder al pago"**, se solicita:

- Información de pago
- Total a pagar

> Es un proceso **simulado**, pero con formato estructurado para futuras integraciones.

---

## 📄 Confirmación de Compra y PDF

Al confirmar la compra:

1. Se genera un **PDF** con los boletos:
   - Nombre del evento
   - Zona y asiento
   - Precio
   - Código QR (simulado)

2. El PDF se descarga automáticamente.
3. El usuario es redirigido a la **página de inicio**.

---

## ✅ ¡Eventia está lista para usarse!

Ahora puedes:
- Crear eventos
- Configurar zonas
- Registrar usuarios
- Comprar boletos

¡Disfruta de la experiencia con Eventia!

