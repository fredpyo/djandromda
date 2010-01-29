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
    	// HACK: meter el default value como un tagged value
    	this.parameters.add(new DjDataTypeParameter("_default", sourceAttribute.getDefaultValue()));
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
    		// valor por default
    		
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
        if (key.equals("auto_now")) {
        	return " auto_now = " + trueParser(value) + ",";
        }
        if (key.equals("auto_now_add")) {
        	return " auto_now_add = " + trueParser(value) + ",";
        }
        if (key.equals("_default")) {
        	if (value != null) {
        		// HACK: otro terrible terrible hack
        		if (trueParser(value) != null)
        			return " default = " + trueParser(value) + ",";
        		else
        			return " default = " + value + ",";
        	}
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
    	else if(bool.equals("false")){
    		return " False ";
    	}
    	return null;
    }
    /**
     * @see org.andromda.cartridges.djmda.psm.DjDataType#toPgSql()
     * Conversión del datatype a una sentencia de columna (parte de CREATE TABLE...)
     */
    public java.lang.String toPgSql()
    {
    	String headfield = null;
    	String field = "";	
    	String nullable = " NOT NULL";
    	String unique = "";
    	headfield = sqlType("100")+" ";
    	for (Iterator iterator = this.parameters.iterator(); iterator.hasNext();) {
    		DjDataTypeParameter pivot = (DjDataTypeParameter) iterator.next();
    		
    		if (pivot.getKey().equals("str_length")){
    			headfield = sqlType((String)pivot.getValue());
            }
    		if (pivot.getKey().equals("nullable"))
    			if (pivot.getValue().equals("true"))
    				nullable = " NULL";
    		
    		if (pivot.getKey().equals("unique")){
    			if(pivot.getValue().equals("true")){
    				unique = " UNIQUE";
    			}
    		}
    		field += sqlParser(pivot.getKey(),(String)pivot.getValue());	
    	}
    	return headfield+field+nullable+unique+",";
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
			return "integer";
			
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

