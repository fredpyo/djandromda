package org.andromda.cartridges.djmda.metafacades;

import java.util.ArrayList;
import java.util.Iterator;

import org.andromda.metafacades.uml.AttributeFacade;
import org.andromda.metafacades.uml.EnumerationLiteralFacade;


/**
 * MetafacadeLogic implementation for org.andromda.cartridges.djmda.metafacades.ChoiceFacade.
 *
 * @see org.andromda.cartridges.djmda.metafacades.ChoiceFacade
 */
public class ChoiceFacadeLogicImpl
    extends ChoiceFacadeLogic
{

    public ChoiceFacadeLogicImpl (Object metaObject, String context)
    {
        super (metaObject, context);
    }
    /**
     * @see org.andromda.cartridges.djmda.metafacades.ChoiceFacade#getChoices()
     */
    protected java.util.Collection handleGetChoices()
    {
    	ArrayList choices = new ArrayList();
    	for (Iterator iterator = this.getLiterals().iterator(); iterator.hasNext();) {
    		AttributeFacade literal = (AttributeFacade) iterator.next();
    		String a[] = {literal.getName().toLowerCase(), literal.getName()};
    		choices.add(String.format("('%s', '%s')", a));
    	}
        return choices;
    }
}