package org.andromda.cartridges.djmda.metafacades;

import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.cartridges.djmda.metafacades.ModelFacade;
import org.andromda.metafacades.uml.AssociationEndFacade;
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
    	ArrayList imports = new ArrayList();
    	
    	// iterar
    	for (Iterator iterator = classes.iterator(); iterator.hasNext();) {
    		ModelFacade model = new ModelFacadeLogicImpl(iterator.next(), null);
    		// agarrar asociaciones
    		Collection assocArr = model.getAssociationEnds(); // obtiene la lista de association ends que apuntan a esta clase

    		for (Iterator assocIterator = assocArr.iterator(); assocIterator.hasNext();) {
    			AssociationEndFacade assoc = (AssociationEndFacade) assocIterator.next();
    			assoc = assoc.getOtherEnd(); // queremos tener acceso al otro extremo de la asociación para saber a que clase apunta
        		if (assoc.isNavigable()) {
        			if (assoc.getType().getStereotypeNames().contains("Model")) {
	        			ModelFacade targetModel = (ModelFacade) assoc.getType(); // horrorible hack que ni entiendo, pero sirva para obtener el tipo de clase objetivo de la relación :D
	        			if (!classes.contains(targetModel)) {
	        				imports.add("from " + this.getProjectName() + "." + targetModel.getPackage().getName() + ".models import " + targetModel.getName());
	        			}
        			}
        		}
    		}
    	}
        return imports;
    }
    
    protected String handleGetProjectName() {
    	return this.getPackage().getName();
    }
}