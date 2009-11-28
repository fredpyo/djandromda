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
        String field="";
        String headfield="";
    
	    	//AQUI SE RECOORRRE LOS PARAMETROS Y SE LOS PARSEA CON PYPARSER sI ES djfield SE PARSEA LA CABECERA 
	    	for (Iterator iterator = this.parameters.iterator(); iterator.hasNext();) {
	        		DjDataTypeParameter pivot = (DjDataTypeParameter) iterator.next();
	        		//if(pivot.getKey().equals("djfield")){
	    			//	headfield = pyParser(pivot.getKey(),(String)pivot.getValue());
	    			//}else{
	    				field += pyParser(pivot.getKey(),(String)pivot.getValue());
	    			//}
	    			//fields += ", " + pivot.getKey() + " = " + (String)pivot.getValue() ;
	    		    
	    				
	    	}
        
    		//ESTO MANEJA LA CABECERA DEL TIPO DE DATO
        	if (this.getType().isEnumeration()) {
        		//typeName = "models.CharField(max_length = 100, choices = " + type.getName().toUpperCase() + "_CHOICES)";
        		headfield = "models.CharField(";
        		field += "choices = " + this.getType().getName().toUpperCase() + "_CHOICES,";
        		// validaciones finales :D
    			if (!field.contains("max_length")) {
        			field += "max_length = 50,"; // TODO: sacar del enumeration facade (o choice facade) la longitud
        		}
        	}
    		if (this.getType().getName().equals("String")){
    			//return "models.CharField(max_length = 500)";
    			headfield = "models.CharField(";
        		// validaciones finales :D
    			if (!field.contains("max_length")) {
        			field += "max_length = 100,";
        		}
    		}
    		if (this.getType().getName().equals("Integer")){
    			headfield = "models.IntegerField(";
    		}
    		if (this.getType().getName().equals("Boolean")){
    			headfield = "models.BooleanField(";
    		}
    		
    		if (this.getType().getName().equals("Date")){
    			headfield = "models.DateField(";
    		}
    		if (this.getType().getName().equals("DateTime")){
    			headfield = "models.DateTimeField(";
    		}

	    	if(field.length()>2){
	    		field=field.substring(0,field.length()-1);
	    	}
    	
    	return headfield + field + ")";
    	//System.out.println(this.parameters);
        //System.out.println("##############################");
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
        //throw new java.lang.UnsupportedOperationException("org.andromda.cartridges.djmda.psm.DjDataType.toDjango() Not implemented!");
    	//return "blaa";
    }
    //FUNCION PARA PARSEAR PARAMETROS
    private String pyParser(String key, String value) {
        if (key.equals("djfield")){
        	return "";
        	//return "widget=forms.PasswordInput(render_value=False),";
        	//return "models.PasswordField(";
        }
        if (key.equals("unique")){
        	return " unique = " + trueParser(value)+ ",";
        }
        if (key.equals("blank")){
        	return " blank = " + trueParser(value)+",";
        }
        if (key.equals("str_length")){
        	return " max_length = " + value+",";
        }
        
        
        return "";
   	}
    	/**
         * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#operToPy()
         */
    
    //NO TENIA GANAS DE ESCRIBR TANTAS VECES TRUE FALSE
    private String trueParser(String bool) {
    	if(bool.equals("true")){
    		return " True ";
    	}
    	else{
    		return " False ";
    	}
    }
    /**
     * @see org.andromda.cartridges.djmda.psm.DjDataType#toPgSql()
     */
    public java.lang.String toPgSql()
    {
    	String headfield ="";
    	String field ="";	
    	String notNull ="NOT NULL";	
    	headfield = sqlType("100")+" ";
    	for (Iterator iterator = this.parameters.iterator(); iterator.hasNext();) {
    		DjDataTypeParameter pivot = (DjDataTypeParameter) iterator.next();
    		
    		if (pivot.getKey().equals("str_length")){
    			headfield = sqlType((String)pivot.getValue());
            }
    		if (pivot.getKey().equals("unique")){
    			if(pivot.getValue().equals("false")){
    				notNull = "";
            
    			}
    		}
    		field += sqlParser(pivot.getKey(),(String)pivot.getValue());	
    	}
    	return headfield+field+notNull+",";
    }
    private String sqlParser(String key, String value) {
    	
    	if (key.equals("djfield")){
        	return "";
        }
        
        if (key.equals("blank")){
        	return "";
        }
        if (key.equals("null")){
        
        	
        	if(value.equals("true")){
        		return " NOT NULL ";
        		}
        	}
    	return "";
    }
    
    
    private String sqlType(String value) {
    	if (this.getType().isEnumeration()) {
    		return "varchar(50)";
    		
    	}
    	if (this.getType().getName().equals("String")){
			return "varchar"+"("+value+") ";
		
		}
		if (this.getType().getName().equals("Integer")){
			return "intenger";
			
		}
		if (this.getType().getName().equals("Boolean")){
			return "bool";
			
		}
		
		if (this.getType().getName().equals("Date")){
			return "date";
			
		}
		if (this.getType().getName().equals("DateTime")){
			return "timestamp with time zone";
			
		}
		return "";
 
    }
}

