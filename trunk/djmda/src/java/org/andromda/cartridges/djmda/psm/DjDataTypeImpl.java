// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.andromda.cartridges.djmda.psm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.TaggedValueFacade;

/**
 * @see org.andromda.cartridges.djmda.psm.DjDataType
 */
public class DjDataTypeImpl
    extends org.andromda.cartridges.djmda.psm.DjDataType
{
    public DjDataTypeImpl()
    {
        super();
    }

    public DjDataTypeImpl(org.andromda.metafacades.uml.ClassifierFacade type)
    {
       super(type);
    }

    public DjDataTypeImpl(org.andromda.metafacades.uml.ClassifierFacade type, java.util.Collection parameters)
    {
        super(type, parameters);
    }

    /**
     * Copy-constructor from other DjDataType
     *
     * @param otherBean, cannot be <code>null</code>
     * @throws java.lang.NullPointerException if the argument is <code>null</code>
     */
    public DjDataTypeImpl(DjDataType otherBean)
    {
        this(otherBean.getType(), otherBean.getParameters());
    }

    /**
     * Build from an existing attribute, consuming type and tagged values
     * @param sourceAttribute
     */
    public DjDataTypeImpl(AttributeFacade sourceAttribute)
    {
    	super();
    	// type
    	this.type = sourceAttribute.getType();
    	this.parameters = new ArrayList();
    	// tagged values
    	for (Iterator i = sourceAttribute.getTaggedValues().iterator(); i.hasNext();) {
    		TaggedValueFacade tag = (TaggedValueFacade) i.next();
    		try {
    			this.parameters.add(new DjDataTypeParameter(tag.getName(), tag.getValue()));
    		} catch (Exception e) {
    			// HACK: por defecto, los valores booleanos al ser true no traen su tipo de dato
    			this.parameters.add(new DjDataTypeParameter(tag.getName(), Boolean.TRUE));
    		}
    	}
    }
    
    /**
     * @see org.andromda.cartridges.djmda.psm.DjDataType#toDjango()
     */
    public java.lang.String toDjango()
    {
        // @todo implement public java.lang.String toDjango()
    	// TODO: Como dije en PyAttrImpl.java...
    	// este debería de retornar un string tipo models.XxxxField(xxx = xx, xx = xx)
    	// en primera instancia el XxxxField va a estar determinado por el atributo this.type
    	// (el cual puede ser String, Integer, o una clase de enumeración...
    	// básicamente el getType() del atributo real es almacenado en this.type, por eso es un ClassifierFacade,
    	// así que podés hacerle un isEnumeration() o getName(), etc, etc, etc...
    	// En otras palabras, la lógia de la función pyDataType en ModelFacadeLogicImpl va a terminar acá
    	// ...
    	// Adicionalmente... si el valor etiquetado djfield está definido con algo como Password o lo que sea, entonces no se
    	// debe retornar un CharField sino un PasswordField... o algo así
    	// y luego se interpretan el resto de los valores etiquetados...
    	// Ah todos estos están almacenados en la colección this.parameters, donde podes iterar entre las instancias de
    	// la clase DjDataTypeParameter, que tiene dos atributos key y value, que coincidentemente se refieren al nombre del tag y su valor.
    	// Ojo que el valor está como un tipo de dato Object, y tenés que castear al tipo adecuado... creo... ya no me acuerdo... jajaja
    	// bueno... eso es todo... chau
        throw new java.lang.UnsupportedOperationException("org.andromda.cartridges.djmda.psm.DjDataType.toDjango() Not implemented!");
    }

    /**
     * @see org.andromda.cartridges.djmda.psm.DjDataType#toPgSql()
     */
    public java.lang.String toPgSql()
    {
        // @todo implement public java.lang.String toPgSql()
        throw new java.lang.UnsupportedOperationException("org.andromda.cartridges.djmda.psm.DjDataType.toPgSql() Not implemented!");
    }

}
