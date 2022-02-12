package com.jcohy.framework.configuration.processor.utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor8;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/11:10:59
 * @since 2022.0.1
 */
public final class Elements {

    private Elements() {
    }

    public static String getQualifiedName(Element element) {
        if (element != null) {
            TypeElement enclosingElement = getEnclosingTypeElement(element.asType());
            if (enclosingElement != null) {
                return getQualifiedName(enclosingElement) + "$"
                        + ((DeclaredType) element.asType()).asElement().getSimpleName().toString();
            }
            if (element instanceof TypeElement) {
                return ((TypeElement) element).getQualifiedName().toString();
            }
        }
        return null;
    }

    private static TypeElement getEnclosingTypeElement(TypeMirror type) {
        if (type instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) type;
            Element enclosingElement = declaredType.asElement().getEnclosingElement();
            if (enclosingElement instanceof TypeElement) {
                return (TypeElement) enclosingElement;
            }
        }
        return null;
    }

    public static TypeElement asType(Element element) {
        return element.accept(TypeElementVisitor.INSTANCE, null);
    }

    /**
     * 根据 TypeElement 获取元素的全类名.
     * @param element element
     * @return string 全类名
     */
    public static String getBinaryName(Element element) {
        return getBinaryNameImpl(element, element.getSimpleName().toString());
    }

    /**
     * 根据 TypeElement 获取元素的全类名.
     * @param element element
     * @param className 类名(不含包名)
     * @return string 全类名
     */
    public static String getBinaryNameImpl(Element element, String className) {
        Element enclosingElement = element.getEnclosingElement();

        if (enclosingElement instanceof PackageElement) {
            PackageElement pkg = (PackageElement) enclosingElement;
            if (pkg.isUnnamed()) {
                return className;
            }
            return pkg.getQualifiedName() + "." + className;
        }

        TypeElement typeElement = (TypeElement) enclosingElement;
        return getBinaryNameImpl(typeElement, typeElement.getSimpleName() + "$" + className);
    }

    private static final class TypeElementVisitor extends CastingElementVisitor<TypeElement> {

        private static final TypeElementVisitor INSTANCE = new TypeElementVisitor();

        TypeElementVisitor() {
            super("type element");
        }

        @Override
        public TypeElement visitType(TypeElement e, Void ignore) {
            return e;
        }

    }

    private abstract static class CastingElementVisitor<T> extends SimpleElementVisitor8<T, Void> {

        private final String label;

        CastingElementVisitor(String label) {
            this.label = label;
        }

        @Override
        protected final T defaultAction(Element e, Void ignore) {
            throw new IllegalArgumentException(e + " does not represent a " + this.label);
        }

    }

}
