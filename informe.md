# Informe de Desarrollo del Proyecto de Criptografía RSA

## Introducción

El objetivo del proyecto fue desarrollar un programa en Java que implemente criptografía RSA para realizar tres funciones principales: generar pares de claves, firmar archivos y verificar firmas digitales. La API Criptográfica de Java fue utilizada para asegurar la integridad y autenticidad de los archivos.

## Desarrollo del Programa

### Generación de# Informe de Desarrollo del Proyecto de Criptografía RSA

## Introducción

El objetivo de este proyecto fue desarrollar un programa en Java que implemente criptografía RSA para generar pares de claves, firmar archivos y verificar firmas digitales. Para ello, utilizamos la API Criptográfica de Java, asegurando la integridad y autenticidad de los archivos procesados.

## Desarrollo del Programa

### Generación de Claves

Para generar el par de claves RSA, se empleó la clase `KeyPairGenerator` de Java. Las claves generadas se almacenan en archivos: la clave privada se cifra utilizando una contraseña proporcionada por el usuario y se guarda en formato `.dat`, mientras que la clave pública se guarda en formato `.der` y `.pem`.

### Firma de Archivos

La firma de archivos se realiza mediante la generación de un hash SHA-256 del contenido del archivo, el cual se firma utilizando la clave privada del usuario. El resultado de la firma se guarda en un archivo binario separado. 

### Verificación de Firmas

Para verificar la firma de un archivo, se genera el hash SHA-256 del archivo original y se compara con el hash firmado usando la clave pública del usuario. Si ambos coinciden, se valida la firma.

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
