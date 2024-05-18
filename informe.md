# Informe de Desarrollo del Proyecto de Criptografía RSA

## Introducción

El objetivo del proyecto fue desarrollar un programa en Java que implemente criptografía RSA para realizar tres funciones principales: generar pares de claves, firmar archivos y verificar firmas digitales. La API Criptográfica de Java fue utilizada para asegurar la integridad y autenticidad de los archivos.

## Desarrollo del Programa

### Generación de# Informe de Desarrollo del Proyecto de Criptografía RSA

## Introducción

El objetivo de este proyecto fue desarrollar un programa en Java que implemente criptografía RSA para generar pares de claves, firmar archivos y verificar firmas digitales. Para ello, utilizamos la API Criptográfica de Java, asegurando la integridad y autenticidad de los archivos procesados.

## Desarrollo del Programa

Generación de Claves

*KeyPairGenerator de Java: La clase KeyPairGenerator de Java se utiliza para generar un par de claves pública y privada usando el algoritmo RSA. Este proceso implica la creación de una instancia de KeyPairGenerator, la inicialización con un tamaño de clave (en este caso, 2048 bits), y la llamada al método generateKeyPair() para obtener el par de claves.

*Almacenamiento de Claves: Una vez generado el par de claves, se almacenan en archivos para su uso posterior. La clave privada se cifra para protegerla, usando una contraseña proporcionada por el usuario. Luego, se guarda en un archivo en formato .dat. Por otro lado, la clave pública se guarda en formatos .der y .pem para asegurar su compatibilidad con diferentes sistemas y aplicaciones.

Firma de Archivos

*Generación de Hash SHA-256: Antes de firmar un archivo, se calcula un hash del contenido del mismo utilizando el algoritmo SHA-256. Este hash es una representación única y fija del archivo original, que sirve como una especie de "huella digital" que identifica el archivo de manera única.

*Firma con Clave Privada: Una vez obtenido el hash SHA-256, se procede a firmarlo utilizando la clave privada del usuario. La firma se realiza mediante la clase Signature de Java, utilizando el algoritmo "SHA256withRSA". Este proceso garantiza que la firma sea única para ese archivo y esa clave privada, y es imposible de falsificar sin acceso a la clave privada correspondiente.

*Almacenamiento de Firma: El resultado de la firma se guarda en un archivo binario separado. Este archivo contiene la firma digital, que es esencialmente una representación codificada de la firma realizada con la clave privada.

Verificación de Firmas

*Generación de Hash del Archivo Original: Para verificar la firma de un archivo, primero se calcula el hash SHA-256 del archivo original (sin firma). Esto produce un hash que debería coincidir con el hash generado durante la firma, si la firma es válida.

*Verificación de Firma con Clave Pública: La verificación de la firma se realiza utilizando la clave pública del usuario. Se utiliza la misma clase Signature y el mismo algoritmo "SHA256withRSA", pero en modo de verificación. Si el hash generado durante la verificación coincide con el hash firmado originalmente, se valida la firma y se confirma la autenticidad del archivo.

## Dificultades Encontradas

1. **Falta de Práctica con la API Criptográfica de Java:** La complejidad y la falta de experiencia previa con la API Criptográfica de Java dificultaron la comprensión y aplicación efectiva de los conceptos criptográficos.
2. **Complejidad de la Criptografía RSA:** La falta de información detallada y ejemplos claros sobre la implementación de criptografía RSA aumentó la complejidad del proyecto y generó dificultades en la fase de desarrollo.
3. **Gestión de Claves y Contraseñas:** La falta de información clara sobre la gestión segura de claves privadas y el cifrado de claves con AES para almacenamiento seguro generó desafíos en la implementación de la generación de claves.
4. **Entendimiento de Algoritmos Criptográficos:** La comprensión profunda de los algoritmos criptográficos subyacentes, como RSA, AES y SHA-256, fue un desafío debido a su complejidad matemática y conceptual.
5. **Configuración de Parámetros Criptográficos:** La configuración correcta de los parámetros criptográficos, como el tamaño de clave y el número de iteraciones, fue un desafío debido a la falta de claridad en las mejores prácticas y recomendaciones.
6. **Compatibilidad de Formatos de Claves:** Garantizar la compatibilidad y correcta exportación/importación de claves en diferentes formatos (DER, PEM) para su uso en otras aplicaciones fue un desafío técnico que requirió investigación adicional.
7. **Seguridad en el Manejo de Claves:** Implementar medidas de seguridad adecuadas para proteger las claves privadas y las contraseñas durante su generación, almacenamiento y uso presentó dificultades debido a la sensibilidad de estos datos.
8. **Optimización de Rendimiento:** La optimización del rendimiento del programa, especialmente en operaciones criptográficas intensivas como la firma digital, fue un desafío para garantizar tiempos de procesamiento aceptables en grandes volúmenes de datos.
9. **Pruebas y Validación:** La creación de casos de prueba exhaustivos y la validación completa del sistema criptográfico resultaron complejas debido a la variedad de escenarios posibles y la necesidad de verificar la precisión y seguridad en cada etapa.
10. **Gestión de Errores y Excepciones:** La identificación y manejo adecuado de errores y excepciones en operaciones criptográficas, como la verificación de firmas, fue un desafío para garantizar la estabilidad y seguridad del programa en diferentes condiciones.


## Conclusiones

El desarrollo de este programa permitió explorar y aplicar conceptos avanzados de criptografía en Java. A pesar de los desafíos técnicos, se logró implementar un sistema funcional y seguro para la generación y manejo de claves RSA, así como para la firma y verificación de archivos. Este proyecto sirve como una base sólida para futuras mejoras y aplicaciones más avanzadas en el campo de la seguridad informática.

---

Esperamos que este informe proporcione una visión clara del proceso y los logros del proyecto, así como de los obstáculos superados durante su desarrollo.
