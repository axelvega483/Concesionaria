<h1 align="center">
  🚗💨 Sistema de Gestión para Concesionaria
</h1>

<p align="center">
  <b>Sistema backend completo para administración integral de concesionaria vehicular</b>
  <br>
  <em>Desarrollado con Spring Boot • MySQL • OpenAPI 3</em>
</p>

<p align="center">
  <a href="http://localhost:8080/swagger-ui/index.html">
    <img src="https://img.shields.io/badge/Documentación-SwaggerUI-brightgreen?style=for-the-badge&logo=swagger" alt="Swagger UI">
  </a>
  <a href="http://localhost:8080/v3/api-docs">
    <img src="https://img.shields.io/badge/API-OpenAPI3-orange?style=for-the-badge&logo=openapi-initiative" alt="OpenAPI 3">
  </a>
  <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot">
</p>

---

## 🌟 Características del Sistema

<div align="center">

| Característica | Icono | Descripción |
|----------------|-------|-------------|
| **Gestión Avanzada de Ventas** | 💰 | Generación automática de pagos (cuotas o pago único) |
| **Control de Estados** | 📊 | Seguimiento financiero detallado de ventas y pagos |
| **Confirmación y Anulación** | 🔄 | Actualización automática del saldo pendiente |
| **Relaciones Sólidas** | 🔗 | Entidades interconectadas: vehículos, clientes, usuarios |
| **Validaciones Integradas** | ✅ | Modelo con mensajes personalizados y robustos |
| **DTOs Personalizados** | 🎯 | Vistas específicas para diferentes respuestas |
| **Inicialización Automática** | 🤖 | Usuario administrador creado automáticamente |

</div>

---

## 📦 Módulos del Sistema

<div align="center">

| Módulo | Icono | Descripción | Endpoints |
|--------|-------|-------------|-----------|
| **Vehículos** | 🚗 | Gestión completa de inventario | `GET/POST/PUT/DELETE /vehiculo` |
| **Clientes** | 👥 | Registro y seguimiento | `GET/POST/PUT /cliente` |
| **Ventas** | 💰 | Procesos de venta y cuotas | `GET/POST /ventas` |
| **Pagos** | 💳 | Control de pagos y estados | `GET/PUT /pagos` |
| **Usuarios** | 🔐 | Sistema de autenticación | `POST /usuario/login` |

</div>

---

## 🛠️ Tecnologías Utilizadas

<div align="center">

### Back-end (API REST)

| Tecnología | Icono | Uso |
|------------|-------|-----|
| **Java 17** | <img src="https://img.shields.io/badge/Java-17-blue?style=flat&logo=openjdk" alt="Java 17"> | Lenguaje de programación principal |
| **Spring Boot** | <img src="https://img.shields.io/badge/Spring_Boot-3.4.5-brightgreen?style=flat&logo=springboot" alt="Spring Boot"> | Framework principal de desarrollo |
| **Spring Data JPA** | <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat&logo=hibernate" alt="Spring Data JPA"> | Persistencia y mapeo ORM |
| **MySQL** | <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql" alt="MySQL"> | Base de datos relacional |
| **Bean Validation** | <img src="https://img.shields.io/badge/Validation-JSR380-orange?style=flat" alt="Bean Validation"> | Validación de datos y modelos |
| **Maven** | <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven" alt="Maven"> | Gestión de dependencias y build |

</div>

---

## 📝 Requerimientos Funcionales

<div align="center">

| Módulo | Funcionalidades | Estado |
|--------|-----------------|--------|
| **🚗 Vehículos** | Alta, baja, edición y listado • Información detallada (marca, modelo, año, estado, precio) | ✅ Implementado |
| **👥 Clientes** | Gestión completa • Visualización de historial de compras y pagos | ✅ Implementado |
| **👨‍💼 Usuarios** | Administración con roles (ADMIN, VENDEDOR) • Creación automática de administrador | ✅ Implementado |
| **💰 Ventas** | Registro con detalles • Generación automática de pagos • Actualización de saldo y estado | ✅ Implementado |
| **💳 Pagos** | Confirmación y anulación • Actualización automática del saldo pendiente | ✅ Implementado |

</div>

---

## 📄 Documentación Técnica

<div align="center">

| Recurso | Enlace | Descripción |
|---------|--------|-------------|
| **📖 Swagger UI** | [Swagger](http://localhost:8080/swagger-ui/index.html) | Documentación interactiva completa de la API |
| **🔧 Endpoints** | Ver tabla de módulos | Lista completa de endpoints disponibles |

</div>

---

## ⚙️ Requerimientos No Funcionales

<div align="center">

| Categoría | Especificación | Estado |
|-----------|----------------|--------|
| **🛡️ Validaciones** | Entidades con mensajes claros y personalizados | ✅ Implementado |
| **📐 Modularidad** | Arquitectura escalable para futuras integraciones (web, mobile) | ✅ Implementado |
| **💻 Código Limpio** | Principios SOLID y buenas prácticas de desarrollo | ✅ Implementado |
| **🔒 Seguridad** | Validación de datos y relaciones consistentes | ✅ Implementado |
| **📊 Performance** | Consultas optimizadas y gestión eficiente de recursos | ✅ Implementado |

</div>

---

<div align="center">

## 🚀 ¿Listo para Comenzar?

[**📖 Ir a la Documentación Interactiva**](http://localhost:8080/swagger-ui/index.html) • 

**⭐ ¡No olvides darle una estrella al repo si te fue útil!**

---
*Desarrollado con ❤️ usando Spring Boot y Java 17*

</div>
