🚗 Sistema de Gestión para Concesionaria
Sistema backend para la administración integral de una concesionaria de vehículos. Permite gestionar vehículos, clientes, usuarios (empleados), ventas, pagos y estados asociados. Diseñado para optimizar procesos comerciales y financieros mediante una API REST escalable, segura y fácil de integrar con futuras aplicaciones frontend o móviles.

🌟 Características del Sistema
Gestión avanzada de ventas con generación automática de pagos en cuotas o pago único.

Control de estados de ventas y pagos para seguimiento financiero preciso.

Manejo detallado de vehículos y clientes con historial asociado.

Confirmación y anulación de pagos con actualización automática del saldo pendiente.

Relaciones robustas entre entidades: vehículos, clientes, usuarios, ventas y pagos.

DTOs y mapeadores para respuestas claras y segmentadas.

Validaciones personalizadas en el modelo para integridad de datos.

🛠️ Tecnologías Utilizadas
Back-end (API REST)
Java 17

Spring Boot

Spring Web

Spring Data JPA

Bean Validation (javax.validation)

Lombok

MySQL / PostgreSQL (configurable)

Maven

📝 Requerimientos Funcionales
Vehículos:

Alta, edición, baja y listado de vehículos.

Información detallada: marca, modelo, año, estado, etc.

Clientes:

CRUD completo para clientes.

Visualización del historial de compras y pagos.

Usuarios (Empleados):

Gestión de usuarios con roles definidos (ADMIN, VENDEDOR, etc.).

Seguridad inicial para control de acceso (preparado para JWT).

Ventas:

Registro detallado de ventas con productos asociados.

Generación automática de pagos según modalidad: pago único o cuotas.

Actualización automática del saldo y estado de la venta.

Pagos:

Confirmación y anulación de pagos.

Actualización del saldo pendiente de la venta.

Control de estados (PENDIENTE, PAGADO).

⚙️ Requerimientos No Funcionales
Validaciones estrictas en las entidades para garantizar integridad.

Manejo de excepciones centralizado para respuestas API claras.

Arquitectura modular para facilitar futuras integraciones y escalabilidad.

Código limpio, mantenible y documentado, siguiendo principios SOLID y buenas prácticas.

Preparado para integración con sistemas de autenticación JWT y microservicios.
