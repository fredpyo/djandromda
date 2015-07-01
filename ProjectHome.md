Cartucho diseñado para trabajar con los modelos diseñados en AndroMDA y convertirlos en código funcional de Django.

El cartucho se encarga de crear a partir del diagrama los modelos y el código base del admin:
  * models.py: con todas las clases necesarias y sus relaciones correspondientes (one-to-one, many-to-one, many-to-many) y constraints (unique, length, etc).
  * admin.py: creado para registrar los modelos creados y permitir trabajar con ellos directamente con el django admin.

A continuación un diagrama de ejemplo con las capacidades actuales del cartucho, estas abarcan paquetes, clases y relaciones (intra y extra paquete) apoyados por Estereotipos y valores etiquetados:
![http://pynfo.com/misc/djandromda-promo-djclases.jpg](http://pynfo.com/misc/djandromda-promo-djclases.jpg)

  * Cada paquete representa una aplicación Django (como lo indica su estereotipo)
  * El archivo models.py de cada app (paquete) es populado con las clases indicadas en el diagrama, con sus respectivas relaciones y constraints sobre sus atributos.