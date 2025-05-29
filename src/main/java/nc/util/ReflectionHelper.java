package nc.util;

import org.objectweb.asm.*;

import java.io.*;
import java.lang.reflect.*;

public class ReflectionHelper {
	
	/**
	 * NOTE: The constructor parameter types must match the argument types EXACTLY - they can NOT be superclasses
	 */
	public static <T> T newInstance(Class<T> clazz, Object... args) {
		return newInstance(clazz, StreamHelper.map(args, Object::getClass, Class<?>[]::new), args);
	}
	
	public static <T> T newInstance(Class<T> clazz, Class<?>[] params, Object... args) {
		return LambdaHelper.getThrowing(() -> clazz.getConstructor(params).newInstance(args));
	}
	
	public static class MethodWrapper<T> {
		
		protected final Method method;
		
		/**
		 * Can access parent methods, but not hidden methods.
		 */
		public MethodWrapper(Class<?> source, String name, Class<?>... parameterTypes) {
			method = LambdaHelper.getThrowing(() -> source.getMethod(name, parameterTypes));
		}
		
		@SuppressWarnings("unchecked")
		public T invoke(Object obj, Object... args) {
			return LambdaHelper.getThrowing(() -> (T) method.invoke(obj, args));
		}
	}
	
	public static class DeclaredMethodWrapper<T> {
		
		protected final Method method;
		
		/**
		 * Can access hidden methods, but not parent methods.
		 */
		public DeclaredMethodWrapper(Class<?> source, String name, Class<?>... parameterTypes) {
			method = LambdaHelper.getThrowing(() -> {
				Method method = source.getDeclaredMethod(name, parameterTypes);
				method.setAccessible(true);
				return method;
			});
		}
		
		@SuppressWarnings("unchecked")
		public T invoke(Object obj, Object... args) {
			return LambdaHelper.getThrowing(() -> (T) method.invoke(obj, args));
		}
	}
	
	public static class ConstructorWrapper<T> {
		
		protected final Constructor<T> constructor;
		
		public ConstructorWrapper(Class<T> source, Class<?>... parameterTypes) {
			constructor = LambdaHelper.getThrowing(() -> {
				Constructor<T> constructor = source.getDeclaredConstructor(parameterTypes);
				constructor.setAccessible(true);
				return constructor;
			});
		}
		
		public T newInstance(Object... args) {
			return LambdaHelper.getThrowing(() -> constructor.newInstance(args));
		}
	}
	
	public static <T> Class<T> cloneClass(Class<? extends T> clazz, String cloneName) {
		ClassLoader classLoader = clazz.getClassLoader();
		try (InputStream is = classLoader.getResourceAsStream(clazz.getName().replace('.', '/') + ".class")) {
			ClassReader cr = new ClassReader(is);
			ClassWriter cw = new ClassWriter(cr, 0);
			String fullName = clazz.getPackage().getName() + "." + cloneName;
			cr.accept(new CloneClassVisitor(cw, fullName.replace('.', '/')), 0);
			return defineClass(classLoader, fullName, cw);
		}
		catch (IOException e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> defineClass(ClassLoader loader, String name, ClassWriter cw) {
		byte[] bytes = cw.toByteArray();
		return (Class<T>) DEFINE_CLASS_METHOD.get().invoke(loader, name, bytes, 0, bytes.length);
	}
	
	public static class CloneClassVisitor extends ClassVisitor {
		
		public final String cloneName;
		
		public CloneClassVisitor(ClassVisitor cv, String cloneName) {
			super(Opcodes.ASM5, cv);
			this.cloneName = cloneName;
		}
		
		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			super.visit(version, access, cloneName, signature, superName, interfaces);
		}
	}
	
	public static final Lazy<DeclaredMethodWrapper<?>> DEFINE_CLASS_METHOD = new Lazy<>(() -> new DeclaredMethodWrapper<>(ClassLoader.class, "defineClass", String.class, byte[].class, int.class, int.class));
}
