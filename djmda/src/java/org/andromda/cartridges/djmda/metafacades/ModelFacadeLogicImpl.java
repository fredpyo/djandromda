package org.andromda.cartridges.djmda.metafacades;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.andromda.cartridges.djmda.psm.*;
//import org.andromda.cartridges.djmda.psm.DjDataTypeParameter;
import org.andromda.cartridges.djmda.psm.PyAttrImpl;
import org.andromda.cartridges.djmda.psm.PyFunc;
import org.andromda.metafacades.uml.AssociationClassFacade;
import org.andromda.metafacades.uml.AssociationEndFacade;
import org.andromda.metafacades.uml.AssociationFacade;
import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.ClassifierFacade;
import org.andromda.metafacades.uml.OperationFacade;
import org.andromda.metafacades.uml.TaggedValueFacade;

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
    		AssociationEndFacade assocStart = (AssociationEndFacade) iterator.next();
    		AssociationEndFacade assocEnd = assocStart.getOtherEnd();
    		String assocFieldString = "";
    		String associationTarget = "";
    		String nullable = "";
    		
    		// detectar el target de la asociación, ya sea otro modelo o 'self'
    		if (assocEnd.getType().getName().equals(this.getName()) && assocEnd.getType().getPackageName().equals(this.getPackageName()))
    			associationTarget = "'self'";
    		else
    			associationTarget = assocEnd.getType().getName();
    		//System.out.println(this.getName() +"+"+assocEnd.getType().getName()+"="+ associationTarget);
    		
    		/*
    		System.out.println();
    		System.out.println(assocStart.getType().getName());
    		System.out.println("isMany:" + assocStart.isMany());
    		System.out.println("isMany2One:" + assocStart.isMany2One());
    		System.out.println("isMany2Many:" + assocStart.isMany2Many());
    		System.out.println("isOne2One:" + assocStart.isOne2One());
    		System.out.println("isOne2Many:" + assocStart.isOne2Many());
    		*/
    		
    		// determinar el tipo de asociación dependiendo de si es un one2one, many2one, many2many
    		if (assocStart.isOne2One() && assocEnd.isNavigable()) {
    			assocFieldString = " = models.ForeignKey(";
    		} else if (assocStart.isMany2One()) {
    			assocFieldString = " = models.ForeignKey(";
    		} else if (assocStart.isMany2Many() && assocEnd.isNavigable()){
    			assocFieldString = " = models.ManyToManyField(";
    		}
    		
    		// manejar campos nullable 
    		if (assocEnd.getLower() == 0)
    			nullable = ", null=True, blank=True";
    		
    		// formar el string final
    		if (!assocFieldString.equals("")) {
        		String relString = assocEnd.getName().toLowerCase() + assocFieldString + associationTarget + nullable + ")";
        		relArr.add(relString);
    		}
    	}
        return relArr;
    }

    
    protected java.util.Collection handleFkPGSQL()
    {
    	Collection assocArr = this.getAssociationEnds(); // obtiene la lista de association ends que apuntan a esta clase
    	ArrayList relArr = new ArrayList();
    	//Collection assocArr2 = this.getAssociatedClasses();

    	for (Iterator iterator = assocArr.iterator(); iterator.hasNext();) {
    		AssociationEndFacade assoc = (AssociationEndFacade) iterator.next();
    		assoc = assoc.getOtherEnd(); // queremos tener acceso al otro extremo de la asociación para saber a que clase apunta
    		if (assoc.isNavigable()) {
    			ClassifierFacade classifier = assoc.getType(); // horrorible hack que ni entiendo, pero sirva para obtener el tipo de clase objetivo de la relación :D
        		//String relString = assoc.getName() + " = models.ForeignKey(" + classifier.getName()+ ")";
    			PGSQLFK relString = new PGSQLFK(classifier.getName(),assoc.getName());
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
			try {
				PyAttr pyAttribute = new PyAttrImpl(attribute);
				//String variableName =  attribute.getName() + " = " + pyDataType(attribute.getType(),pyAttribute);
				String variableName = pyAttribute.toDjango();
				varArr.add(variableName);
			} catch (Exception e) {
				System.out.println("[djMDA :: WARNING] El atributo ´" + attribute.getName() + "´ en la clase " + this.getPackageName() + "." + this.getName() + " fue ignorado por un error con su tipo de dato. ");
			}
    	}
        return varArr;
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#uniqueGroups()
     */
    protected java.util.Collection handleUniqueGroups()
    {
    	HashMap grupos = new HashMap(); // diccionario de grupos, cada elemento es un string de nombres de columnas en el grupo
    	Collection attrArr = this.getAttributes(); //se obtienen todos los atributos de la clase
    	ArrayList groupsArr = new ArrayList();

    	/**
    	 * Atributos
    	 */
    	// recorrer los atributos
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
			AttributeFacade attribute = (AttributeFacade) iterator.next();
			// recorrer los tags de cada atributo
			for (Iterator tags = attribute.getTaggedValues().iterator(); tags.hasNext();) {
				TaggedValueFacade tag = (TaggedValueFacade) tags.next();
				// buscar el tag "group"
				if (tag.getName().equals("group")) {
					// leer los valores dentro del tag "group"
					for (Iterator groups = tag.getValues().iterator(); groups.hasNext();) {
						try {
							String group = (String) groups.next();
							if (grupos.get(group) != null) {
								grupos.put(group, grupos.get(group) + ", " + '"' + attribute.getName() + '"');
							} else {
								grupos.put(group, '"' + attribute.getName() + '"');
							}
						} catch (Exception e ) {
							System.out.println("[djMDA :: WARNING] No se pudo leer un valor del tag '" + tag.getName() + "' en el atributo '" + attribute.getName() + "' en el modelo '" + this.getName() + "'");
						}
					}
				}
			}
			//System.out.println(this.getName() + "::" + grupos);
    	}
    	
    	/**
    	 * Asociaciones
    	 */
    	// obtener los extremos de las asociaciones del lado de esta clase
    	attrArr = this.getAssociationEnds();
    	// iterar sobre los association ends
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
    		// obtener el extremo opuesta al a clase
    		AssociationEndFacade assocEnd = ((AssociationEndFacade)iterator.next()).getOtherEnd();
    		// detectar si es referencia a otra tabla o a la misma... otro modelo o 'self'
    		String associationTarget = null;
    		if (assocEnd.getType().getName().equals(this.getName()) && assocEnd.getType().getPackageName().equals(this.getPackageName()))
    			associationTarget = "'self'";
    		else
    			associationTarget = assocEnd.getName().toLowerCase();
    		// recorrer los tags del assocEnd
    		for (Iterator tags = assocEnd.getTaggedValues().iterator(); tags.hasNext();) {
				TaggedValueFacade tag = (TaggedValueFacade) tags.next();
				// buscar el tag "group"
				if (tag.getName().equals("group")) {
					// leer los valores dentro del tag "group"
					for (Iterator groups = tag.getValues().iterator(); groups.hasNext();) {
						try {
							String group = (String) groups.next();
							if (grupos.get(group) != null) {
								grupos.put(group, grupos.get(group) + ", " + '"' + associationTarget + '"');
							} else {
								grupos.put(group, '"' + associationTarget + '"');
							}
						} catch (Exception e ) {
							System.out.println("[djMDA :: WARNING] No se pudo leer un valor del tag '" + tag.getName() + "' en el extremo de asociación '" + associationTarget + "' en el modelo '" + this.getName() + "'.");
						}
					}
				}
    		}
    	}
    	
    	/**
    	 * Limpieza y formateo final
    	 */
    	// quitar el grupo vacio
		grupos.remove("");
		
		// convertir el grupo a una lista (quitar los keys, y dejar como una lista)
		// {"1" : '"a", "b"', "2" : '"b", "c", "d"'} --> ['"a", "b"', '"b", "c", "d"'] 
		Set set = grupos.entrySet();
		for (Iterator i = set.iterator(); i.hasNext();) {
			groupsArr.add(((Map.Entry)i.next()).getValue());
		}

        return groupsArr;
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
    protected java.util.Collection handleToPGSQL()
    {
        // TODO: put your implementation here.
    	Collection attrArr = this.getAttributes();
    	ArrayList varArr = new ArrayList();
    	for (Iterator iterator = attrArr.iterator(); iterator.hasNext();) {
    		AttributeFacade attribute = (AttributeFacade) iterator.next();
    		try {
	    		PyAttr pyAttribute = new PyAttrImpl(attribute);
	    		String variableName = pyAttribute.toPgSql();
	    		varArr.add(variableName);
    		} catch (Exception e ) {
    			
    		}
    	}
        return varArr;
    }
    
    protected String handleGetGeneralizationModel() {
    	String gen;
    	
    	if (this.getGeneralization() != null) {
    		gen = this.getGeneralization().getName();
    	} else {
    		gen = "models.Model";
    	}
    	return gen;
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