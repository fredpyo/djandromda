package org.andromda.cartridges.djmda.metafacades;

import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.andromda.cartridges.djmda.metafacades.ModelFacade;
import org.andromda.cartridges.djmda.psm.M2MTable;
import org.andromda.cartridges.djmda.psm.PGSQLFK;
import org.andromda.metafacades.uml.AssociationEndFacade;
import org.andromda.metafacades.uml.AssociationFacade;
import org.andromda.metafacades.uml.ClassifierFacade;
import org.andromda.metafacades.uml.EnumerationFacade;
import org.andromda.metafacades.uml.ModelElementFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;


/**
 * MetafacadeLogic implementation for org.andromda.cartridges.djmda.metafacades.AppFacade.
 *
 * @see org.andromda.cartridges.djmda.metafacades.AppFacade
 */
public class AppFacadeLogicImpl
    extends AppFacadeLogic
{

    public AppFacadeLogicImpl (Object metaObject, String context)
    {
        super (metaObject, context);
    }
    /**
     * @see org.andromda.cartridges.djmda.metafacades.AppFacade#getModels()
     */
    protected java.util.Collection handleGetModels()
    {
    	
        // agarrar las clases dentro del paquete
    	Collection classes = this.getClasses();
    	ArrayList models = new ArrayList();
    	
    	// iterar
    	for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
    		// agarrar una de las classes
    		ClassifierFacade candidate = (ClassifierFacade) iterator.next();
    		// si es de tipo modelo, agregar a la lista de modelos
    		if (candidate.getStereotypeNames().contains("Model")) {
    			
    			models.add(new ModelFacadeLogicImpl(candidate, null));
    		}
    	}
    	
        return models;
    }
    
    /**
     * @see org.andromda.cartridges.djmda.metafacades.AppFacade#getModels()
     */
    protected java.util.Collection handleGetChoices()
    {
    	Collection elements = this.getClasses();
    	ArrayList choices = new ArrayList();
    	
    	for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
			ClassifierFacade sd = (ClassifierFacade) iterator.next();
			if (sd.isEnumeration()) {
				choices.add(new ChoiceFacadeLogicImpl(sd, null));
			}
    	}
    	
    	/*CollectionUtils.filter(choices, new Predicate() {
    		public boolean evaluate(Object object) {
    			try {
        			final ClassifierFacade sd = (ClassifierFacade) object;
        			return true;    				
    			} catch (Exception ex) {
    				return false;
    			}
    		}
    	});*/
    	
        return choices;
    }
    
    /**
     * @see org.andromda.cartridges.djmda.metafacades.AppFacade#importModelsToPy()
     */
    protected java.util.Collection handleImportModelsToPy()
    {
        // agarrar las clases dentro del paquete
    	Collection classes = this.getClasses();
    	//ArrayList imports = new ArrayList();
    	Set imports = new HashSet(); // para evitar entradas repetidas
    	
    	// iterar
    	for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
    		ModelFacade model = new ModelFacadeLogicImpl(iterator.next(), null);
    		// agarrar asociaciones
    		Collection assocArr = model.getAssociationEnds(); // obtiene la lista de association ends que apuntan a esta clase

    		for (Iterator assocIterator = assocArr.iterator(); assocIterator.hasNext();) {
    			AssociationEndFacade assocStart = (AssociationEndFacade) assocIterator.next();
    			AssociationEndFacade assocEnd = assocStart.getOtherEnd(); // queremos tener acceso al otro extremo de la asociación para saber a que clase apunta
        		if (assocEnd.isNavigable() && (assocStart.isOne2One() || assocStart.isMany2One() || assocStart.isMany2Many())) {
        			if (assocEnd.getType().getStereotypeNames().contains("Model")) {
	        			ModelFacade targetModel = (ModelFacade) assocEnd.getType(); // horrorible hack que ni entiendo, pero sirva para obtener el tipo de clase objetivo de la relación :D
	        			if (!classes.contains(targetModel)) {
	        				imports.add("from " + this.getProjectName() + "." + targetModel.getPackage().getName().toLowerCase() + ".models import " + targetModel.getName());
	        			}
        			}
        		}
    		}
    	}
        return imports;
    }
    
    /**
     * Retornar las tablas Many2Many
     */
    protected java.util.Collection handleGetM2MTables()
    {
    	Collection models = this.getModels();
    	ArrayList m2mTables = new ArrayList();

    	try {
	    	for (Iterator iterator = models.iterator(); iterator.hasNext();) {
	    		ModelFacade model = (ModelFacadeLogicImpl) iterator.next();
	    		for (Iterator assocIterator = model.getAssociationEnds().iterator(); assocIterator.hasNext();) {
	    			AssociationFacade assoc = (AssociationFacade)((AssociationEndFacade)assocIterator.next()).getAssociation();
	    			
	    			// detectar asociaciones M2M
	    			if (assoc.isMany2Many()) {
	    				// generar el nombre de la tabla M2M ordenando el nombre de las tablas involucradas de manera alfabética:
	    				// Perro * <---> * Dueño
	    				// Se traduce a
	    				// dueño_perro
	    				String prefix = null;
	    				ArrayList names = new ArrayList();
	    				names.add(assoc.getAssociationEndA().getType().getName().toLowerCase());
	    				names.add(assoc.getAssociationEndB().getType().getName().toLowerCase());
	    				Collections.sort(names);
	    				// determinar el prefijo del nombre utilizando el package donde se encuentra el modelo desde el cual se puede navegar
	    				if (assoc.getAssociationEndA().isNavigable())
	    					prefix = assoc.getAssociationEndB().getType().getPackage().getName().toLowerCase();
	    				else
	    					prefix = assoc.getAssociationEndA().getType().getPackage().getName().toLowerCase();
	    				// crear tabla M2M
	    				M2MTable table = new M2MTable(prefix + "_" + names.get(0) + "_" + names.get(1), null);
	    				// añadir los FK
	    				PGSQLFK[] fks = new PGSQLFK[2];
	    				AssociationEndFacade assocEnd = assoc.getAssociationEndA();
	    				fks[0] = new PGSQLFK(assocEnd.getType().getPackage().getName().toLowerCase() + "_" + assocEnd.getType().getName().toLowerCase(), assocEnd.getOtherEnd().getName().toLowerCase(), new Boolean(assocEnd.getLower() == 0));
	    				assocEnd = assoc.getAssociationEndB();
	    				fks[1] = new PGSQLFK(assocEnd.getType().getPackage().getName().toLowerCase() + "_" + assocEnd.getType().getName().toLowerCase(), assocEnd.getOtherEnd().getName().toLowerCase(), new Boolean(assocEnd.getLower() == 0));
	    				table.setFks(Arrays.asList(fks));
	    				// guardar
	    				// HACK hashset barato, complejidad O(n)
	    				boolean addThis = true;
	    				for (Iterator i = m2mTables.iterator(); i.hasNext();) {
	    					if (((M2MTable)i.next()).getName().equals(table.getName())) {
	    						addThis = false;
	    						break;
	    					}
	    				}
	    				if (addThis)
	    					m2mTables.add(table);
	    			}
	
	    		}
	    	}
    	} catch (Exception e) {
    		System.out.println("*******" + e.getMessage());
    	}
    	return m2mTables;
    }
 
    protected String handleGetProjectName() {
    	return this.getPackage().getName();
    }
    
    protected java.lang.Object handleGetProject() {
    	return this.getPackage();
    }
}