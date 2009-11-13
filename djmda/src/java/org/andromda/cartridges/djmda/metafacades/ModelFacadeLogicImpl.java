package org.andromda.cartridges.djmda.metafacades;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.cartridges.djmda.psm.PyFunc;
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
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#attrToPy()
     */
    protected java.util.Collection handleAttrToPy()
       {
    	// TODO: put your implementation here.
    	Collection attrArr = this.getAttributes(); //se obtienen todos los atributos de la clase
    	ArrayList varArr = new ArrayList();
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
			AttributeFacade attribute = (AttributeFacade) iterator.next();
			String variableName =  attribute.getName() + " = " + pyDataType(attribute.getType().getName());
			varArr.add(variableName);
    	}
        return varArr;
    }

   
	
    private String pyDataType(String type) {
		// TODO Auto-generated method stub
		if (type.equals("String")){
			return "models.CharField(max_length = 500)";
		}
		if (type.equals("Integer")){
			return "models.IntegerField()";
		}
		if (type.equals("Boolean")){
			return "models.BooleanField()";
		}
		
		if (type.equals("Date")){
			return "models.DateField()";
		}
		if (type.equals("DateTime")){
			return "models.DateField()";
		}
    	
    	return null;
	}
	/**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#operToPy()
     */
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