<h1 align="center">
  🚗💨 Sistema de Gestión para Concesionaria
</h1>

<p align="center">
  <b>API REST segura para administración integral de concesionaria vehicular</b>
  <br>
  <em>Spring Boot • Spring Security • MySQL • OpenAPI 3</em>
</p>

<p align="center">
  <a href="http://localhost:8080/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Documentación-SwaggerUI-brightgreen?style=for-the-badge&logo=swagger" alt="Swagger UI">
  </a>
  <a href="http://localhost:8080/api-docs">
    <img src="https://img.shields.io/badge/API-OpenAPI3-orange?style=for-the-badge&logo=openapi-initiative" alt="OpenAPI 3">
  </a>
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Spring_Security-Enabled-success?style=for-the-badge&logo=springsecurity" alt="Spring Security">
</p>

---

## 🌟 Características del Sistema

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
| **Gestión Avanzada de Ventas** | 💰 | Generación automática de pagos (cuotas o pago único) |
| **Control de Estados** | 📊 | Seguimiento financiero detallado de ventas y pagos |
| **Confirmación y Anulación** | 🔄 | Actualización automática del saldo pendiente |
| **Seguridad con Roles** | 🔐 | Autenticación y autorización basada en ADMIN y VENDEDOR |
| **Relaciones Sólidas** | 🔗 | Entidades interconectadas: vehículos, clientes, usuarios |
| **Validaciones Integradas** | ✅ | Modelo con mensajes personalizados y robustos |
| **DTOs Personalizados** | 🎯 | Vistas específicas para diferentes respuestas |
| **Inicialización Automática** | 🤖 | Usuario administrador creado automáticamente |

</div>

---

## 🔐 Seguridad Implementada

- ✔ Integración completa con **Spring Security**
- ✔ Protección de endpoints mediante `SecurityFilterChain`
- ✔ Autenticación con credenciales almacenadas en base de datos
- ✔ Encriptación de contraseñas (BCrypt)
- ✔ Autorización basada en roles (`ADMIN`, `VENDEDOR`)
- ✔ Restricción de acceso según permisos
- ✔ Autenticación stateless con JWT

### Ejemplo de acceso:

- Endpoints administrativos → Solo `ADMIN`
- Gestión de ventas → `ADMIN` y `VENDEDOR`
- Acceso general → requiere autenticación

---

## 📦 Módulos del Sistema

<div align="center">

| Módulo | Icono | Descripción | Endpoints |
|--------|-------|-------------|-----------|
| **Vehículos** | 🚗 | Gestión completa de inventario | `GET/POST/PUT/DELETE /vehiculo` |
| **Clientes** | 👥 | Registro y seguimiento | `GET/POST/PUT /cliente` |
| **Ventas** | 💰 | Procesos de venta y generación automática de cuotas | `GET/POST /ventas` |
| **Pagos** | 💳 | Confirmación, anulación y actualización de saldo | `GET/PUT /pagos` |
| **Usuarios** | 🔐 | Autenticación y control de acceso con roles | `POST /usuario/login` |

</div>

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

### Back-end (API REST)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework base |
| **Spring Security** | <img src="https://img.shields.io/badge/Spring_Security-Enabled-success?style=flat&logo=springsecurity" alt="Spring Security"> | Autenticación y autorización |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia ORM |
| **MySQL** | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql" alt="MySQL"> | Base de datos relacional |
| **Bean Validation** | <img src="https://img.shields.io/badge/Validation-JSR380-orange?style=flat" alt="Bean Validation"> | Validación de datos |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Build y dependencias |

</div>

---

## 📝 Requerimientos Funcionales

<div align="center">

| Módulo | Funcionalidades | Estado |
|--------|-----------------|--------|
| **🚗 Vehículos** | Alta, baja, edición y listado • Gestión de imágenes asociadas | ✅ Implementado |
| **👥 Clientes** | Gestión completa • Historial de compras y pagos | ✅ Implementado |
| **👨‍💼 Usuarios** | Roles (ADMIN, VENDEDOR) • Encriptación de contraseña • Creación automática de admin | ✅ Implementado |
| **💰 Ventas** | Registro • Generación automática de pagos • Cálculo y actualización de saldo | ✅ Implementado |
| **💳 Pagos** | Confirmación y anulación • Impacto financiero directo en la venta | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **📖 Swagger UI** | [Swagger](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva completa |
| **📑 OpenAPI JSON** | http://localhost:8080/api-docs | Especificación técnica de la API |

</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
| **🛡️ Seguridad** | Autenticación y autorización con roles | ✅ Implementado |
| **📐 Arquitectura** | Separación Controller-Service-Repository | ✅ Implementado |
| **💻 Código Limpio** | Principios SOLID y buenas prácticas | ✅ Implementado |
| **📊 Integridad de Datos** | Validaciones robustas y control de consistencia | ✅ Implementado |
| **🚀 Escalabilidad** | Preparado para frontend o microservicios | ✅ Preparado |

</div>

---

<div align="center">

## 🚀 ¿Listo para Probar la API?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html)

---

⭐ Si este proyecto te resultó útil o interesante a nivel técnico, ¡no olvides darle una estrella!

---
*Desarrollado con ❤️ usando Spring Boot, Spring Security y Java 17*

</div>
