# Wi-Fi Scanner Android App

Esta es una aplicación simple para escanear redes Wi-Fi cercanas utilizando el `WifiManager` de Android. La aplicación permite al usuario ver una lista de redes Wi-Fi disponibles y sus detalles, como el SSID y las capacidades de seguridad. Además, puedes realizar búsquedas de redes Wi-Fi, recargar los resultados y navegar a través de la lista de redes.

## Características

- Escanea las redes Wi-Fi disponibles usando `WifiManager`.
- Muestra el nombre (SSID) y las capacidades de seguridad de cada red.
- Permite recargar los resultados y escanear redes nuevamente.
- Permite la navegación por la lista de redes Wi-Fi disponibles.
- Asegura que el permiso de ubicación necesario esté solicitado y otorgado.
- Habilita automáticamente el Wi-Fi si está desactivado en el dispositivo.

## Requisitos

Para que esta aplicación funcione correctamente, tu proyecto debe incluir los siguientes permisos en el archivo `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
