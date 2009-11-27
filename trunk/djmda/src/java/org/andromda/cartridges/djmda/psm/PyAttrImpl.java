// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.andromda.cartridges.djmda.psm;

import org.andromda.metafacades.uml.AttributeFacade;

/**
 * @see org.andromda.cartridges.djmda.psm.PyAttr
 */
public class PyAttrImpl
    extends org.andromda.cartridges.djmda.psm.PyAttr
{
    public PyAttrImpl()
    {
        super();
    }

    public PyAttrImpl(java.lang.String name, org.andromda.cartridges.djmda.psm.DjDataType dataType)
    {
        super(name, dataType);
    }

    /**
     * Copy-constructor from other PyAttr
     *
     * @param otherBean, cannot be <code>null</code>
     * @throws java.lang.NullPointerException if the argument is <code>null</code>
     */
    public PyAttrImpl(PyAttr otherBean)
    {
        this(otherBean.getName(), otherBean.getDataType());
    }

    /**
     * Build from an existing attribute, consuming it's name, type and tagged values
     * @param sourceAttribute
     */
    public PyAttrImpl(AttributeFacade sourceAttribute)
    {
    	super();
    	// name
    	this.name = sourceAttribute.getName();
    	this.dataType = new DjDataTypeImpl(sourceAttribute);
    }
    
    /**
     * @see org.andromda.cartridges.djmda.psm.PyAttr#toDjango()
     */
    public java.lang.String toDjango()
    {
        // @todo implement public java.lang.String toDjango()
    	// TODO: IDEA DE ESTE METODO
    	// Al ser invocado debe retornar un string tipo nombre = models.CharField(max_length = 220, unique = True)
    	// Lo que se me ocurre es que esta función retorne algo así:
    	// return this.name + " = " + this.dataType.toDjango()
    	// con lo que dataType.toDjango() retornará algo como "models.CharField(max_length = 220, unique = True)
    	// formando el string que nos interesa
    	// el resto de la documentación la pongo en @see:DjDataTypeImpl.java (le podés hacer Ctrl + Click y te va a llevar)
       // throw new java.lang.UnsupportedOperationException("org.andromda.cartridges.djmda.psm.PyAttr.toDjango() Not implemented!");
        return this.name + " = " + this.getDataType().toDjango();
    	//return "blaa";
    	
    }

    /**
     * @see org.andromda.cartridges.djmda.psm.PyAttr#toPgSql()
     */
    public java.lang.String toPgSql()
    {
        // @todo implement public java.lang.String toPgSql()
    	return "\""+ this.name + "\" " + this.getDataType().toPgSql();
        //throw new java.lang.UnsupportedOperationException("org.andromda.cartridges.djmda.psm.PyAttr.toPgSql() Not implemented!");
    }

}
