package com.jcohy.framework.configuration.processor.utils;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.SimpleTypeVisitor8;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2022
 * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 2022.0.1 2022/2/11:11:00
 * @since 2022.0.1
 */
public class Types {

	protected Types() {
	}

	public static DeclaredType asDeclared(TypeMirror maybeDeclaredType) {
		return maybeDeclaredType.accept(DeclaredTypeVisitor.INSTANCE, null);
	}

	public static String getBinaryName(TypeMirror mirror) {
		return Elements.getBinaryName(asElement(mirror));
	}

	/**
	 * 检查父接口是否包含注解.
	 * @param interfaces 父接口列表
	 * @param annotationClassName 注解名
	 * @return boolean
	 */
	public static boolean contain(List<? extends TypeMirror> interfaces, String annotationClassName) {
		for (TypeMirror typeMirror : interfaces) {
			return Types.getBinaryName(typeMirror).equals(annotationClassName);
		}
		return false;
	}

	public static TypeElement asTypeElement(TypeMirror mirror) {
		return Elements.asType(asElement(mirror));
	}

	public static Element asElement(TypeMirror typeMirror) {
		return typeMirror.accept(AsElementVisitor.INSTANCE, null);
	}

	private static final class DeclaredTypeVisitor extends CastingTypeVisitor<DeclaredType> {

		private static final DeclaredTypeVisitor INSTANCE = new DeclaredTypeVisitor();

		DeclaredTypeVisitor() {
			super("declared type");
		}

		@Override
		public DeclaredType visitDeclared(DeclaredType type, Void ignore) {
			return type;
		}

	}

	private static final class AsElementVisitor extends SimpleTypeVisitor8<Element, Void> {

		private static final AsElementVisitor INSTANCE = new AsElementVisitor();

		@Override
		protected Element defaultAction(TypeMirror e, Void p) {
			throw new IllegalArgumentException(e + " cannot be converted to an Element");
		}

		@Override
		public Element visitDeclared(DeclaredType t, Void p) {
			return t.asElement();
		}

		@Override
		public Element visitError(ErrorType t, Void p) {
			return t.asElement();
		}

		@Override
		public Element visitTypeVariable(TypeVariable t, Void p) {
			return t.asElement();
		}

	}

	private abstract static class CastingTypeVisitor<T> extends SimpleTypeVisitor8<T, Void> {

		private final String label;

		CastingTypeVisitor(String label) {
			this.label = label;
		}

		@Override
		protected T defaultAction(TypeMirror e, Void v) {
			throw new IllegalArgumentException(e + " does not represent a " + this.label);
		}

	}

}
