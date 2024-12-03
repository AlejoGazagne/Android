
# Eventos - Aplicación Android con Kotlin y Jetpack Compose

**Eventos** es una aplicación Android desarrollada con Kotlin que utiliza Jetpack Compose para su interfaz de usuario. Diseñada para facilitar la gestión de eventos, permite a los usuarios explorar, marcar favoritos y recibir notificaciones de eventos importantes.

---

## 🎯 **Características principales**

- **Explorar eventos**: Visualiza una lista de eventos disponibles.
- **Favoritos**: Marca y accede rápidamente a tus eventos favoritos.
- **Notificaciones**: Recibe recordatorios y alertas sobre eventos seleccionados.
- **Persistencia local**: Almacena y gestiona datos de manera eficiente con una base de datos local.

---

## 🌐 **Interacción con Endpoints**

La aplicación se conecta a un servidor mock local con los siguientes endpoints:

- **`GET /eventos`**: Obtiene la lista completa de eventos.
- **`GET /users`**: Obtiene la lista de usuarios.
- **`GET /notifications`**: Obtiene la lista de notificaciones.

---

## 🚀 **Cómo ejecutar la aplicación**

### 1️⃣ Clonar el repositorio
```bash
   git clone https://github.com/AlejoGazagne/Android.git
   cd Android
```

### 2️⃣ Abrir el proyecto en Android Studio
- Inicia **Android Studio**.
- Selecciona "Open an existing project".
- Navega al repositorio clonado y selecciónalo.

### 3️⃣ Inicializar el servidor mock
1. Abre una terminal.
2. Ve a la carpeta `mock_server` en el directorio raíz del proyecto.
3. Ejecuta:
   ```bash
   json-server --watch db.json --port 3000
   ```  
   > *Si el puerto 3000 no está disponible, modifica el comando especificando otro puerto y el archivo de retrofit ubicado en /data/remote/RetrofitInstance.*

### 4️⃣ Construir y ejecutar la aplicación
- Conecta un dispositivo Android o inicia un emulador desde Android Studio.
- Haz clic en el botón **Run** para compilar y ejecutar la app.

---

## 📦 **Dependencias clave**

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Interfaz de usuario moderna y declarativa.
- **[Hilt](https://dagger.dev/hilt/)**: Inyección de dependencias simplificada.
- **[Retrofit](https://square.github.io/retrofit/)**: Cliente HTTP para consumir APIs.
- **[Room](https://developer.android.com/jetpack/androidx/releases/room)**: Base de datos local robusta.
- **[Coil](https://coil-kt.github.io/coil/)**: Carga eficiente de imágenes.
- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)**: Manejo asíncrono.
- **[Material3](https://m3.material.io/)**: Componentes para interfaces modernas.
- **[WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)**: Gestión de tareas en segundo plano.

---

## 🛠 **Configuración del proyecto**

Este proyecto está configurado con **Gradle**. Archivos clave de configuración:

- `build.gradle` (nivel de aplicación): Configuración de dependencias y plugins.
- `gradle/libs.versions.toml`: Gestión centralizada de versiones de bibliotecas.

---

¡Contribuciones y mejoras son bienvenidas! 🚀  
Si tienes preguntas, problemas o sugerencias, no dudes en abrir un *issue* en el repositorio.