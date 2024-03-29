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
     * Retornar las asociaciones en formato de Django.
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
    		String relatedName = ", related_name = '" + assocStart.getName().toLowerCase() + "'";
    		String nullable = "";
    		
    		// detectar el target de la asociación, ya sea otro modelo o 'self'
    		if (assocEnd.getType().getName().equals(this.getName()) && assocEnd.getType().getPackageName().equals(this.getPackageName()))
    			associationTarget = "'self'";
    		else
    			associationTarget = "'"+ assocEnd.getType().getPackage().getName().toLowerCase() + "." + assocEnd.getType().getName()+"'";
    		
    		/**
    		 * Determinar el tipo de asociación dependiendo de si es un one2one, many2one, many2many
    		 */
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
        		String relString = assocEnd.getName().toLowerCase() + assocFieldString + associationTarget + nullable + relatedName + ")";
        		relArr.add(relString);
    		}
    	}
        return relArr;
    }

    /**
     * Retornar las asociaciones de los modelos en formato de PostgreSQL
     */
    protected java.util.Collection handleFkPGSQL()
    {
    	Collection assocArr = this.getAssociationEnds(); // obtiene la lista de association ends que apuntan a esta clase
    	ArrayList relArr = new ArrayList();

    	// recorrer cada asociación e ir bisectando sus componentes para formar el FK
    	for (Iterator iterator = assocArr.iterator(); iterator.hasNext();) {
    		AssociationEndFacade assocStart = (AssociationEndFacade) iterator.next();
    		AssociationEndFacade assocEnd = assocStart.getOtherEnd();
    		String assocFieldString = ""; // nombre que tendrá el FK
    		String associationTarget = ""; // objetivo del FK
    		String relatedName = ", related_name = '" + assocStart.getName().toLowerCase() + "'";
    		boolean nullable = false;
    		
    		// detectar el target de la asociación, ya sea otro modelo o 'self'
    		if (assocEnd.getType().getName().equals(this.getName()) && assocEnd.getType().getPackageName().equals(this.getPackageName()))
    			associationTarget = "'self'";
    		else
    			associationTarget = "'"+ assocEnd.getType().getPackage().getName().toLowerCase() + "." + assocEnd.getType().getName()+"'";
    		
    		/**
    		 * Si es de tipo one2one, many2one, many2many entonces lo aceptamos
    		 */
    		if ((assocStart.isOne2One() && assocEnd.isNavigable()) ||
                (assocStart.isMany2One()) ||
                (assocStart.isMany2Many())){
    			PGSQLFK sqlfk = new PGSQLFK(assocEnd.getType().getPackage().getName().toLowerCase() + "_" + assocEnd.getType().getName().toLowerCase(), assocEnd.getName().toLowerCase(), new Boolean(assocEnd.getLower() == 0));
    			relArr.add(sqlfk);
    		}
    	}
        return relArr;
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ModelFacade#attrToPy()
     * Retorna los atributos en formato de Django
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
     * Retorna una lista de columnas que son unicas (para unique_together e indices de UNIQUE)
     */
    protected java.util.Collection handleUniqueGroups(Boolean vslFormat)
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
    		String associationName = assocEnd.getName().toLowerCase() + (vslFormat.equals(new Boolean(true)) ? "_id" : "");
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
								grupos.put(group, grupos.get(group) + ", " + '"' + associationName + '"');
							} else {
								grupos.put(group, '"' + associationName + '"');
							}
						} catch (Exception e ) {
							System.out.println("[djMDA :: WARNING] No se pudo leer un valor del tag '" + tag.getName() + "' en el extremo de asociación '" + associationName + "' en el modelo '" + this.getName() + "'.");
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
    
    /**
     * Retorna las operaciones en formato de django
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
     * Retorna los atributos en formato de PostgreSQL
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
    
    /**
     * Obtiene el modelo el cual este generaliza en formato django
     */
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
     * Obtiene un FK a la tabla que esta tabla extiende
     */
    protected PGSQLFK handleGetParentTable() {
    	if (this.getGeneralization() == null)
    		return null;
		else {
			PGSQLFK fk = new PGSQLFK();
			ModelFacade gen = new ModelFacadeLogicImpl(this.getGeneralization(), null);
			fk.setName(gen.getName().toLowerCase() + "_ptr_id");
			fk.setTable(gen.getPackage().getName().toLowerCase() + "_" + gen.getName().toLowerCase());
			fk.setNullable(new Boolean(false));
			return fk;
		}
    }
    
    /**
     * Retornar el campo ID o PK de este modelo
     * @return String
     */
    protected String handleGetModelId() {
    	// TODO: implementar tipos de ID cambiables
    	if (this.getGeneralization() == null)
    		return "id";
    	else {
    		return this.getGeneralization().getName().toLowerCase() + "_ptr_id";
    	}
    }
    			
    /**
     * Función utilitaria para armar la cadena del método de una clase
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