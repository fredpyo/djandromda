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

    public DjDataTypeImpl(java.lang.String type)
    {
       super(type);
    }

    public DjDataTypeImpl(java.lang.String type, java.util.Collection parameters)
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
    	this.type = sourceAttribute.getType().getName();
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
