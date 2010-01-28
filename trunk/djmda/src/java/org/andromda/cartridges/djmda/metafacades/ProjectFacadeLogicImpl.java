package org.andromda.cartridges.djmda.metafacades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.andromda.metafacades.uml.PackageFacade;


/**
 * MetafacadeLogic implementation for org.andromda.cartridges.djmda.metafacades.ProjectFacade.
 *
 * @see org.andromda.cartridges.djmda.metafacades.ProjectFacade
 */
public class ProjectFacadeLogicImpl
    extends ProjectFacadeLogic
{

    public ProjectFacadeLogicImpl (Object metaObject, String context)
    {
        super (metaObject, context);
    }
    /**
     * @see org.andromda.cartridges.djmda.metafacades.ProjectFacade#prueba()
     */
    protected String handleGetProjectSQL()
    {
        // TODO: put your implementation here.
    	return null;
    }

    /**
     * @see org.andromda.cartridges.djmda.metafacades.ProjectFacade#getApps()
     */
    protected java.util.Collection handleGetApps()
    {
        // TODO: add your implementation here!
    	ArrayList apps = new ArrayList();
    	Collection packages = this.getSubPackages();
    	
    	for (Iterator iterator = packages.iterator(); iterator.hasNext();) {
    		PackageFacade pack = (PackageFacade) iterator.next();
    		if (pack.getStereotypeNames().contains("App"))
    			apps.add(pack);
    	}
    	
        return (Collection) apps;
    }

}