# 🚗 Sistema de Gestión para una Concesionaria

Sistema de backend para la administración integral de una concesionaria de vehículos. Permite gestionar vehículos, clientes, usuarios (empleados), ventas, pagos y estados asociados. Pensado para optimizar y automatizar los procesos comerciales y financieros mediante una API REST robusta, extensible y segura.

---

## 🌟 Características del Sistema

- **Gestión avanzada de ventas** con generación automática de pagos (cuotas o pago único).
- **Control de estados** de ventas y pagos para seguimiento financiero detallado.
- **Confirmación y anulación de pagos** con actualización automática del saldo pendiente.
- **Relaciones sólidas entre entidades**: vehículos, clientes, usuarios, ventas y pagos.
- **Validaciones integradas** en el modelo con mensajes personalizados.
- **DTOs personalizados** para diferentes vistas y respuestas.
- **Inicialización automática del usuario administrador** si no existen registros.

---

## 🛠️ Tecnologías Utilizadas

### Back-end (API REST)
- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Bean Validation (javax.validation)**
- **Lombok**
- **MySQL** / PostgreSQL (configurable)
- **Maven**

---

## 📝 Requerimientos Funcionales

1. **Vehículos**:
   - Alta, baja, edición y listado de vehículos.
   - Información detallada: marca, modelo, año, estado, precio.

2. **Clientes**:
   - Gestión completa de clientes.
   - Visualización del historial de compras y pagos realizados.

3. **Usuarios (Empleados)**:
   - Administración de usuarios con roles (`ADMIN`, `VENDEDOR`).
   - Creación automática del administrador si no hay registros.

4. **Ventas**:
   - Registro de ventas con detalle de vehículo y cliente.
   - Generación automática de pagos asociados.
   - Actualización de saldo y estado de la venta.

5. **Pagos**:
   - Confirmación y anulación de pagos.
   - Actualización automática del saldo pendiente y estado del pago.

---

## ⚙️ Requerimientos No Funcionales

- **Validaciones en entidades** con mensajes claros y personalizados.
- **Modularidad y escalabilidad** para futuras integraciones (web, mobile).
- **Código limpio y documentado** con principios SOLID y buenas prácticas.

