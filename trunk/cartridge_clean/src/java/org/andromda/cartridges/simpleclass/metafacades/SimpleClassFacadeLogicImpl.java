package org.andromda.cartridges.simpleclass.metafacades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.cartridges.simpleclass.psm.PHPFunction;
import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.OperationFacade;


/**
 * MetafacadeLogic implementation for org.andromda.cartridges.simpleclass.metafacades.SimpleClassFacade.
 *
 * @see org.andromda.cartridges.simpleclass.metafacades.SimpleClassFacade
 */
public class SimpleClassFacadeLogicImpl
    extends SimpleClassFacadeLogic
{

    public SimpleClassFacadeLogicImpl (Object metaObject, String context)
    {
        super (metaObject, context);
    }
    /**
     * @see org.andromda.cartridges.simpleclass.metafacades.SimpleClassFacade#attrToPHPVars()
     */
    protected java.util.Collection handleAttrToPHPVars()
    {
        // TODO: put your implementation here.
    	Collection attrArr = this.getAttributes();
    	ArrayList varArr = new ArrayList();
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
    		AttributeFacade attribute = (AttributeFacade) iterator.next();
    		String variableName = "$" + attribute.getName();
    		varArr.add(variableName);
    	}
        return varArr;
    }

    /**
     * @see org.andromda.cartridges.simpleclass.metafacades.SimpleClassFacade#optToPHPFuncs()
     */
    protected java.util.Collection handleOpToPHPFuncs()
    {
        // TODO: put your implementation here.
    	
    	// obtiene todas las operaciones de la clase
    	Collection opArr = this.getOperations();
    	
    	ArrayList funcArr = new ArrayList();
    	
    	for (Iterator iterator = opArr.iterator(); iterator.hasNext();) {
    		OperationFacade op = (OperationFacade)iterator.next();
    		String funcName = op.getName(); // nombre de la operacion
    		String phpArgNames = "";
    		String argNames = op.getArgumentNames();
    		if(!argNames.equals("")) {
    			String[] argNamesArr = op.getArgumentNames().split(",");
    			phpArgNames = this.toPHPVarNamesString(argNamesArr);
    		}
    		PHPFunction function = new PHPFunction(funcName, phpArgNames);
    		funcArr.add(function);
    		
    	}
    	return funcArr;
    }
    
    private String toPHPVarNamesString(String[] argsNameArr) {
    	String phpArgNames = "";
    	for(int i = 0; i < argsNameArr.length - 1; i++) {
    		phpArgNames += "$" + argsNameArr[i].trim() + " , ";
    	}
    	return phpArgNames += "$" + argsNameArr[argsNameArr.length - 1].trim();
    }

}