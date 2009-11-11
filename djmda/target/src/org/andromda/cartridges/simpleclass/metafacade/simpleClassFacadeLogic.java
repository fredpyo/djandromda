//
// Attention: generated code (by MetafacadeLogic.vsl) - do not modify!
//
package org.andromda.cartridges.simpleclass.metafacade;

/**
 * MetafacadeLogic for org.andromda.cartridges.simpleclass.metafacade.simpleClassFacade
 *
 * @see org.andromda.cartridges.simpleclass.metafacade.simpleClassFacade
 */
public abstract class simpleClassFacadeLogic
    extends org.andromda.core.metafacade.MetafacadeBase
    implements org.andromda.cartridges.simpleclass.metafacade.simpleClassFacade
{

    protected Object metaObject;

    public simpleClassFacadeLogic(Object metaObject, String context)
    {
        super(metaObject, getContext(context));
        this.superClassifierFacade =
           (org.andromda.metafacades.uml.ClassifierFacade)
            org.andromda.core.metafacade.MetafacadeFactory.getInstance().createFacadeImpl(
                    "org.andromda.metafacades.uml.ClassifierFacade",
                    metaObject,
                    getContext(context));
        this.metaObject = metaObject;
    }

    /**
     * Gets the context for this metafacade logic instance.
     */
    private static String getContext(String context)
    {
        if (context == null)
        {
            context = "org.andromda.cartridges.simpleclass.metafacade.simpleClassFacade";
        }
        return context;
    }

    private org.andromda.metafacades.uml.ClassifierFacade superClassifierFacade;
    private boolean superClassifierFacadeInitialized = false;

    /**
     * Gets the org.andromda.metafacades.uml.ClassifierFacade parent instance.
     */
    private org.andromda.metafacades.uml.ClassifierFacade getSuperClassifierFacade()
    {
        if (!this.superClassifierFacadeInitialized)
        {
            ((org.andromda.core.metafacade.MetafacadeBase)superClassifierFacade).setMetafacadeContext(this.getMetafacadeContext());
            this.superClassifierFacadeInitialized = true;
        }
        return superClassifierFacade;
    }

    /**
     * @see org.andromda.core.metafacade.MetafacadeBase
     */
    public void resetMetafacadeContext(String context)
    {
        if (!this.contextRoot) // reset context only for non-root metafacades
        {
            context = getContext(context);  // to have same value as in original constructor call
            setMetafacadeContext (context);
            if (this.superClassifierFacadeInitialized)
            {
                ((org.andromda.core.metafacade.MetafacadeBase)superClassifierFacade).resetMetafacadeContext(context);
            }
        }
    }

    /**
     * @see org.andromda.cartridges.simpleclass.metafacade.simpleClassFacade
     */
    public boolean issimpleClassFacadeMetaType()
    {
        return true;
    }
    
    // ---------------- business methods ----------------------

    protected abstract java.util.Collection handleAttrToPy();

    private void handleAttrToPy1oPreCondition()
    {
    }

    private void handleAttrToPy1oPostCondition()
    {
    }

    public java.util.Collection attrToPy()
    {
        handleAttrToPy1oPreCondition();
        java.util.Collection returnValue = handleAttrToPy();
        handleAttrToPy1oPostCondition();
        return returnValue;
    }

    protected abstract java.util.Collection handleOperToPy();

    private void handleOperToPy2oPreCondition()
    {
    }

    private void handleOperToPy2oPostCondition()
    {
    }

    public java.util.Collection operToPy()
    {
        handleOperToPy2oPreCondition();
        java.util.Collection returnValue = handleOperToPy();
        handleOperToPy2oPostCondition();
        return returnValue;
    }

    /**
     * @see org.andromda.metafacades.uml.ClassifierFacade
     */
    public boolean isClassifierFacadeMetaType()
    {
        return true;
    }
    
    /**
     * @see org.andromda.metafacades.uml.GeneralizableElementFacade
     */
    public boolean isGeneralizableElementFacadeMetaType()
    {
        return true;
    }
    
    /**
     * @see org.andromda.metafacades.uml.ModelElementFacade
     */
    public boolean isModelElementFacadeMetaType()
    {
        return true;
    }
    
    // ----------- delegates to org.andromda.metafacades.uml.ClassifierFacade ------------
    // from org.andromda.metafacades.uml.ClassifierFacade
    public org.andromda.metafacades.uml.AttributeFacade findAttribute(java.lang.String name)
    {
        return this.getSuperClassifierFacade().findAttribute(name);
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAbstractions()
    {
        return this.getSuperClassifierFacade().getAbstractions();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAllAssociatedClasses()
    {
        return this.getSuperClassifierFacade().getAllAssociatedClasses();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAllProperties()
    {
        return this.getSuperClassifierFacade().getAllProperties();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAllRequiredConstructorParameters()
    {
        return this.getSuperClassifierFacade().getAllRequiredConstructorParameters();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public org.andromda.metafacades.uml.ClassifierFacade getArray()
    {
        return this.getSuperClassifierFacade().getArray();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getArrayName()
    {
        return this.getSuperClassifierFacade().getArrayName();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAssociatedClasses()
    {
        return this.getSuperClassifierFacade().getAssociatedClasses();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.List getAssociationEnds()
    {
        return this.getSuperClassifierFacade().getAssociationEnds();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAttributes()
    {
        return this.getSuperClassifierFacade().getAttributes();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getAttributes(boolean follow)
    {
        return this.getSuperClassifierFacade().getAttributes(follow);
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getFullyQualifiedArrayName()
    {
        return this.getSuperClassifierFacade().getFullyQualifiedArrayName();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getImplementationOperations()
    {
        return this.getSuperClassifierFacade().getImplementationOperations();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getImplementedInterfaceList()
    {
        return this.getSuperClassifierFacade().getImplementedInterfaceList();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getInstanceAttributes()
    {
        return this.getSuperClassifierFacade().getInstanceAttributes();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getInstanceOperations()
    {
        return this.getSuperClassifierFacade().getInstanceOperations();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getInterfaceAbstractions()
    {
        return this.getSuperClassifierFacade().getInterfaceAbstractions();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getJavaNullString()
    {
        return this.getSuperClassifierFacade().getJavaNullString();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getNavigableConnectingEnds()
    {
        return this.getSuperClassifierFacade().getNavigableConnectingEnds();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getNavigableConnectingEnds(boolean follow)
    {
        return this.getSuperClassifierFacade().getNavigableConnectingEnds(follow);
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public org.andromda.metafacades.uml.ClassifierFacade getNonArray()
    {
        return this.getSuperClassifierFacade().getNonArray();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getOperationCallFromAttributes()
    {
        return this.getSuperClassifierFacade().getOperationCallFromAttributes();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getOperations()
    {
        return this.getSuperClassifierFacade().getOperations();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getProperties(boolean follow)
    {
        return this.getSuperClassifierFacade().getProperties(follow);
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getProperties()
    {
        return this.getSuperClassifierFacade().getProperties();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getRequiredConstructorParameters()
    {
        return this.getSuperClassifierFacade().getRequiredConstructorParameters();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.Long getSerialVersionUID()
    {
        return this.getSuperClassifierFacade().getSerialVersionUID();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getStaticAttributes()
    {
        return this.getSuperClassifierFacade().getStaticAttributes();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.util.Collection getStaticOperations()
    {
        return this.getSuperClassifierFacade().getStaticOperations();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public org.andromda.metafacades.uml.ClassifierFacade getSuperClass()
    {
        return this.getSuperClassifierFacade().getSuperClass();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public java.lang.String getWrapperName()
    {
        return this.getSuperClassifierFacade().getWrapperName();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isAbstract()
    {
        return this.getSuperClassifierFacade().isAbstract();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isArrayType()
    {
        return this.getSuperClassifierFacade().isArrayType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isAssociationClass()
    {
        return this.getSuperClassifierFacade().isAssociationClass();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isBlobType()
    {
        return this.getSuperClassifierFacade().isBlobType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isBooleanType()
    {
        return this.getSuperClassifierFacade().isBooleanType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isClobType()
    {
        return this.getSuperClassifierFacade().isClobType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isCollectionType()
    {
        return this.getSuperClassifierFacade().isCollectionType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isDataType()
    {
        return this.getSuperClassifierFacade().isDataType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isDateType()
    {
        return this.getSuperClassifierFacade().isDateType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isEnumeration()
    {
        return this.getSuperClassifierFacade().isEnumeration();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isFileType()
    {
        return this.getSuperClassifierFacade().isFileType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isInterface()
    {
        return this.getSuperClassifierFacade().isInterface();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isLeaf()
    {
        return this.getSuperClassifierFacade().isLeaf();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isListType()
    {
        return this.getSuperClassifierFacade().isListType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isMapType()
    {
        return this.getSuperClassifierFacade().isMapType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isPrimitive()
    {
        return this.getSuperClassifierFacade().isPrimitive();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isSetType()
    {
        return this.getSuperClassifierFacade().isSetType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isStringType()
    {
        return this.getSuperClassifierFacade().isStringType();
    }

    // from org.andromda.metafacades.uml.ClassifierFacade
    public boolean isTimeType()
    {
        return this.getSuperClassifierFacade().isTimeType();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.lang.Object findTaggedValue(java.lang.String tagName, boolean follow)
    {
        return this.getSuperClassifierFacade().findTaggedValue(tagName, follow);
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.util.Collection getAllGeneralizations()
    {
        return this.getSuperClassifierFacade().getAllGeneralizations();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.util.Collection getAllSpecializations()
    {
        return this.getSuperClassifierFacade().getAllSpecializations();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public org.andromda.metafacades.uml.GeneralizableElementFacade getGeneralization()
    {
        return this.getSuperClassifierFacade().getGeneralization();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.util.Collection getGeneralizationLinks()
    {
        return this.getSuperClassifierFacade().getGeneralizationLinks();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.lang.String getGeneralizationList()
    {
        return this.getSuperClassifierFacade().getGeneralizationList();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public org.andromda.metafacades.uml.GeneralizableElementFacade getGeneralizationRoot()
    {
        return this.getSuperClassifierFacade().getGeneralizationRoot();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.util.Collection getGeneralizations()
    {
        return this.getSuperClassifierFacade().getGeneralizations();
    }

    // from org.andromda.metafacades.uml.GeneralizableElementFacade
    public java.util.Collection getSpecializations()
    {
        return this.getSuperClassifierFacade().getSpecializations();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public void copyTaggedValues(org.andromda.metafacades.uml.ModelElementFacade element)
    {
        this.getSuperClassifierFacade().copyTaggedValues(element);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.Object findTaggedValue(java.lang.String tagName)
    {
        return this.getSuperClassifierFacade().findTaggedValue(tagName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection findTaggedValues(java.lang.String tagName)
    {
        return this.getSuperClassifierFacade().findTaggedValues(tagName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getConstraints()
    {
        return this.getSuperClassifierFacade().getConstraints();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getConstraints(java.lang.String kind)
    {
        return this.getSuperClassifierFacade().getConstraints(kind);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getDocumentation(java.lang.String indent)
    {
        return this.getSuperClassifierFacade().getDocumentation(indent);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getDocumentation(java.lang.String indent, int lineLength)
    {
        return this.getSuperClassifierFacade().getDocumentation(indent, lineLength);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getDocumentation(java.lang.String indent, int lineLength, boolean htmlStyle)
    {
        return this.getSuperClassifierFacade().getDocumentation(indent, lineLength, htmlStyle);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getFullyQualifiedName()
    {
        return this.getSuperClassifierFacade().getFullyQualifiedName();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getFullyQualifiedName(boolean modelName)
    {
        return this.getSuperClassifierFacade().getFullyQualifiedName(modelName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getFullyQualifiedNamePath()
    {
        return this.getSuperClassifierFacade().getFullyQualifiedNamePath();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getId()
    {
        return this.getSuperClassifierFacade().getId();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public org.andromda.metafacades.uml.TypeMappings getLanguageMappings()
    {
        return this.getSuperClassifierFacade().getLanguageMappings();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public org.andromda.metafacades.uml.ModelFacade getModel()
    {
        return this.getSuperClassifierFacade().getModel();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getName()
    {
        return this.getSuperClassifierFacade().getName();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public org.andromda.metafacades.uml.ModelElementFacade getPackage()
    {
        return this.getSuperClassifierFacade().getPackage();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getPackageName(boolean modelName)
    {
        return this.getSuperClassifierFacade().getPackageName(modelName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getPackageName()
    {
        return this.getSuperClassifierFacade().getPackageName();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getPackagePath()
    {
        return this.getSuperClassifierFacade().getPackagePath();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public org.andromda.metafacades.uml.PackageFacade getRootPackage()
    {
        return this.getSuperClassifierFacade().getRootPackage();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getSourceDependencies()
    {
        return this.getSuperClassifierFacade().getSourceDependencies();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public org.andromda.metafacades.uml.StateMachineFacade getStateMachineContext()
    {
        return this.getSuperClassifierFacade().getStateMachineContext();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getStereotypeNames()
    {
        return this.getSuperClassifierFacade().getStereotypeNames();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getStereotypes()
    {
        return this.getSuperClassifierFacade().getStereotypes();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getTaggedValues()
    {
        return this.getSuperClassifierFacade().getTaggedValues();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getTargetDependencies()
    {
        return this.getSuperClassifierFacade().getTargetDependencies();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.Object getTemplateParameter(java.lang.String parameterName)
    {
        return this.getSuperClassifierFacade().getTemplateParameter(parameterName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.util.Collection getTemplateParameters()
    {
        return this.getSuperClassifierFacade().getTemplateParameters();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String getVisibility()
    {
        return this.getSuperClassifierFacade().getVisibility();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public boolean hasExactStereotype(java.lang.String stereotypeName)
    {
        return this.getSuperClassifierFacade().hasExactStereotype(stereotypeName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public boolean hasStereotype(java.lang.String stereotypeName)
    {
        return this.getSuperClassifierFacade().hasStereotype(stereotypeName);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public boolean isBindingDependenciesPresent()
    {
        return this.getSuperClassifierFacade().isBindingDependenciesPresent();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public boolean isConstraintsPresent()
    {
        return this.getSuperClassifierFacade().isConstraintsPresent();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public boolean isTemplateParametersPresent()
    {
        return this.getSuperClassifierFacade().isTemplateParametersPresent();
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String translateConstraint(java.lang.String name, java.lang.String translation)
    {
        return this.getSuperClassifierFacade().translateConstraint(name, translation);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String[] translateConstraints(java.lang.String kind, java.lang.String translation)
    {
        return this.getSuperClassifierFacade().translateConstraints(kind, translation);
    }

    // from org.andromda.metafacades.uml.ModelElementFacade
    public java.lang.String[] translateConstraints(java.lang.String translation)
    {
        return this.getSuperClassifierFacade().translateConstraints(translation);
    }

    /**
     * @see org.andromda.core.metafacade.MetafacadeBase#initialize()
     */
    public void initialize()
    {
        this.getSuperClassifierFacade().initialize();
    }

    /**
     * @see org.andromda.core.metafacade.MetafacadeBase#getValidationOwner()
     */
    public Object getValidationOwner()
    {
        Object owner = this.getSuperClassifierFacade().getValidationOwner();
        return owner;
    }

    /**
     * @see org.andromda.core.metafacade.MetafacadeBase#getValidationName()
     */
    public String getValidationName()
    {
        String name = this.getSuperClassifierFacade().getValidationName();
        return name;
    }

    /**
     * @see org.andromda.core.metafacade.MetafacadeBase#validateInvariants(java.util.Collection)
     */
    public void validateInvariants(java.util.Collection validationMessages)
    {
        this.getSuperClassifierFacade().validateInvariants(validationMessages);
    }
    
    /**
     * The property that stores the name of the metafacade.
     */
    private static final String NAME_PROPERTY = "name";
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        final StringBuffer toString = new StringBuffer(this.getClass().getName());
        toString.append("[");
        try
        {
            toString.append(org.andromda.core.common.Introspector.instance().getProperty(this, NAME_PROPERTY));
        }
        catch (final Throwable throwable)
        {
            // - just ignore when the metafacade doesn't have a name property
        }
        toString.append("]");
        return toString.toString();
    }
}