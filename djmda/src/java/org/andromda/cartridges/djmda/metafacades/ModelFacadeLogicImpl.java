package org.andromda.cartridges.djmda.metafacades;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.cartridges.djmda.psm.DjDataType;
import org.andromda.cartridges.djmda.psm.DjDataTypeImpl;
import org.andromda.cartridges.djmda.psm.*;
//import org.andromda.cartridges.djmda.psm.DjDataTypeParameter;
import org.andromda.cartridges.djmda.psm.PyAttrImpl;
import org.andromda.cartridges.djmda.psm.PyFunc;
import org.andromda.metafacades.uml.AssociationClassFacade;
import org.andromda.metafacades.uml.AssociationEndFacade;
import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.ClassifierFacade;
import org.andromda.metafacades.uml.OperationFacade;

/**
 * MetafacadeLogic implementation for org.andromda.cartridges.djmda.metafacades.ModelFacade.
 *
 * @see org.andromda.cartridges.djmda.metafacades.ModelFacade
 */
public class ModelFacadeLogicImpl
    extends ModelFacadeLogic
{

    public ModelFacadeLogicImpl (Object metaObject, String context)
    {
        super (metaObject, context);
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#assocToPy()
     */
    protected java.util.Collection handleAssocToPy()
    {
    	Collection assocArr = this.getAssociationEnds(); // obtiene la lista de association ends que apuntan a esta clase
    	ArrayList relArr = new ArrayList();
    	//Collection assocArr2 = this.getAssociatedClasses();

    	for (Iterator iterator = assocArr.iterator(); iterator.hasNext();) {
    		AssociationEndFacade assoc = (AssociationEndFacade) iterator.next();
    		assoc = assoc.getOtherEnd(); // queremos tener acceso al otro extremo de la asociaci�n para saber a que clase apunta
    		if (assoc.isNavigable()) {
    			ClassifierFacade classifier = assoc.getType(); // horrorible hack que ni entiendo, pero sirva para obtener el tipo de clase objetivo de la relaci�n :D
        		String relString = assoc.getName() + " = models.ForeignKey(" + classifier.getName()+ ")";
        		relArr.add(relString);
    		}
    	}
        return relArr;
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#attrToPy()
     */
    protected java.util.Collection handleAttrToPy()
       {
    	// TODO: put your implementation here.
    	Collection attrArr = this.getAttributes(); //se obtienen todos los atributos de la clase
    	ArrayList varArr = new ArrayList();
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
			AttributeFacade attribute = (AttributeFacade) iterator.next();
			//PyAttr pyAttribute = new PyAttr(attribute.getName(), new DjDataTypeImpl(attribute));
			PyAttr pyAttribute = new PyAttrImpl(attribute);
			
			String variableName =  attribute.getName() + " = " + pyDataType(attribute.getType(),pyAttribute);
			varArr.add(variableName);
    	}
        return varArr;
    }

   
	
    private String pyDataType(ClassifierFacade type,PyAttr Djfields) {
		// TODO Auto-generated method stub
    	String typeName = type.getName();
    	String fields="";
    	String headfield="";
    	if (type.isEnumeration()) {
    		//typeName = "models.CharField(max_length = 100, choices = " + type.getName().toUpperCase() + "_CHOICES)";
    		headfield = "models.CharField(choices = " + type.getName().toUpperCase();
    		
    	}
		if (typeName.equals("String")){
			//return "models.CharField(max_length = 500)";
			headfield = "models.CharField(";
    		
		}
		if (typeName.equals("Integer")){
			headfield = "models.IntegerField(";
		}
		if (typeName.equals("Boolean")){
			headfield = "models.BooleanField(";
		}
		
		if (typeName.equals("Date")){
			headfield = "models.DateField(";
		}
		if (typeName.equals("DateTime")){
			headfield = "models.DateTimeField(";
		}
    	
		for (Iterator iterator = Djfields.getDataType().getParameters().iterator(); iterator.hasNext();) {
			DjDataTypeParameter pivot = (DjDataTypeParameter) iterator.next();
			if(pivot.getKey().equals("djfield")){
				headfield = pyParser(pivot.getKey(),(String)pivot.getValue());
			}else{
				fields += pyParser(pivot.getKey(),(String)pivot.getValue());
			}
			//fields += ", " + pivot.getKey() + " = " + (String)pivot.getValue() ;
		    
			}
		return headfield + fields + ")";
	}
    
private String pyParser(String key, String value) {
    if (key.equals("djfield")){
    	return "models.Passfield(";
    }
    if (key.equals("unique")){
    	return " unique = " + trueParser(value);
    }
    if (key.equals("blank")){
    	return " blank = " + trueParser(value);
    }
    if (key.equals("str_length")){
    	return " max_length = " + value;
    }
    
    
    return null;
	}
	/**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#operToPy()
     */
private String trueParser(String bool) {
	if(bool.equals("true")){
		return " True ";
	}
	else{
		return " False ";
	}
}
    protected java.util.Collection handleOperToPy()
    {
        // TODO: put your implementation here.
    	Collection operArr = this.getOperations();
    	ArrayList funcArr = new ArrayList();
    	String pyArgNames = "";
    	for (Iterator iterator = operArr.iterator(); iterator.hasNext();) {
    		OperationFacade operation = (OperationFacade) iterator.next();
    		String operationName = operation.getName();
    		String argNames = operation.getArgumentNames();
    		if(!argNames.equals("")) {
    			String[] argsNameArr = argNames.split(",");
    			pyArgNames = ", " + this.toPyFuncVarNames(argsNameArr);
    		} else {
    			pyArgNames = "";
    		}
    		
    		PyFunc pyFunc = new PyFunc(operationName, pyArgNames);
    		funcArr.add(pyFunc);
    		
    	}
        return funcArr;
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#toPGSQL()
     */
    protected org.andromda.cartridges.djmda.psm.PGSQLTable handleToPGSQL()
    {
        // TODO: put your implementation here.
        return null;
    }
    
    /**
     * @param argsNameArr
     * @return
     */
    private String toPyFuncVarNames(String[] argsNameArr) {
    	// TODO: Manejar valores por default :D
    	// este codigo solo convierte de
		// x,y,z
		// a esto:
		// x, y, z
    	String pyArgNames = "";
    	
		for(int i = 0; i < argsNameArr.length - 1; i++) {
    		pyArgNames += argsNameArr[i].trim() + ", ";
    	}
    	pyArgNames += argsNameArr[argsNameArr.length - 1].trim();
    	
    	return pyArgNames;
    }

}