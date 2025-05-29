package nc.dssl.interpret.element;

import nc.dssl.DSSLHelpers;
import nc.dssl.interpret.*;
import nc.dssl.interpret.element.iter.IterElement;
import nc.dssl.interpret.element.primitive.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Element {
	
	public final Interpreter interpreter;
	
	public final @Nonnull Clazz clazz;
	
	protected Element(Interpreter interpreter, @Nonnull Clazz clazz) {
		this.interpreter = interpreter;
		
		this.clazz = clazz;
	}
	
	public final @Nonnull String typeName() {
		return clazz.fullIdentifier;
	}
	
	public @Nullable IntElement asInt(TokenExecutor exec) {
		return null;
	}
	
	public @Nullable BoolElement asBool(TokenExecutor exec) {
		return null;
	}
	
	public @Nullable FloatElement asFloat(TokenExecutor exec) {
		return null;
	}
	
	public @Nullable CharElement asChar(TokenExecutor exec) {
		return null;
	}
	
	public @Nullable StringElement asString(TokenExecutor exec) {
		return null;
	}
	
	protected RuntimeException castError(String type) {
		return new IllegalArgumentException(String.format("Failed to cast %s \"%s\" to %s!", typeName(), this, type));
	}
	
	public @Nonnull IntElement intCast(TokenExecutor exec) {
		throw castError(BuiltIn.INT);
	}
	
	public @Nonnull BoolElement boolCast(TokenExecutor exec) {
		throw castError(BuiltIn.BOOL);
	}
	
	public @Nonnull FloatElement floatCast(TokenExecutor exec) {
		throw castError(BuiltIn.FLOAT);
	}
	
	public @Nonnull CharElement charCast(TokenExecutor exec) {
		throw castError(BuiltIn.CHAR);
	}
	
	public @Nonnull StringElement stringCastInternal(TokenExecutor exec) {
		return new StringElement(interpreter, toString(exec));
	}
	
	public @Nonnull StringElement stringCast(TokenExecutor exec) {
		TokenResult magic = magicAction(exec, "__str__");
		return (magic == null ? this : exec.pop()).stringCastInternal(exec);
	}
	
	public @Nonnull RangeElement rangeCast(TokenExecutor exec) {
		throw castError(BuiltIn.RANGE);
	}
	
	public @Nonnull ListElement listCast(TokenExecutor exec) {
		throw castError(BuiltIn.LIST);
	}
	
	public @Nonnull SetElement setCast(TokenExecutor exec) {
		throw castError(BuiltIn.SET);
	}
	
	public @Nonnull DictElement dictCast(TokenExecutor exec) {
		throw castError(BuiltIn.DICT);
	}
	
	protected RuntimeException binaryOpError(String operator, @Nonnull Element other) {
		return new IllegalArgumentException(String.format("Binary operator \"%s\" is undefined for argument types \"%s\" and \"%s\"!", operator, typeName(), other.typeName()));
	}
	
	@SuppressWarnings("all")
	protected @Nonnull TokenResult onEqualToInternal(TokenExecutor exec, @Nonnull Element other) {
		exec.push(new BoolElement(interpreter, interpreter.builtIn.nullElement.equals(other) ? false : equals(other)));
		return TokenResult.PASS;
	}
	
	@SuppressWarnings("all")
	protected @Nonnull TokenResult onNotEqualToInternal(TokenExecutor exec, @Nonnull Element other) {
		exec.push(new BoolElement(interpreter, interpreter.builtIn.nullElement.equals(other) ? true : !equals(other)));
		return TokenResult.PASS;
	}
	
	protected @Nonnull TokenResult onLessThanInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("<", other);
	}
	
	protected @Nonnull TokenResult onLessOrEqualInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("<=", other);
	}
	
	protected @Nonnull TokenResult onMoreThanInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError(">", other);
	}
	
	protected @Nonnull TokenResult onMoreOrEqualInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError(">=", other);
	}
	
	protected @Nonnull TokenResult onPlusInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("+", other);
	}
	
	protected @Nonnull TokenResult onAndInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("&", other);
	}
	
	protected @Nonnull TokenResult onOrInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("|", other);
	}
	
	protected @Nonnull TokenResult onXorInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("^", other);
	}
	
	protected @Nonnull TokenResult onMinusInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("-", other);
	}
	
	protected @Nonnull TokenResult onConcatInternal(TokenExecutor exec, @Nonnull Element other) {
		if (other instanceof StringElement) {
			exec.push(new StringElement(interpreter, stringCast(exec).toString(exec) + other.toString(exec)));
			return TokenResult.PASS;
		}
		throw binaryOpError("~", other);
	}
	
	protected @Nonnull TokenResult onLeftShiftInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("<<", other);
	}
	
	protected @Nonnull TokenResult onRightShiftInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError(">>", other);
	}
	
	protected @Nonnull TokenResult onMultiplyInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("*", other);
	}
	
	protected @Nonnull TokenResult onDivideInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("/", other);
	}
	
	protected @Nonnull TokenResult onRemainderInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("%", other);
	}
	
	protected @Nonnull TokenResult onPowerInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("**", other);
	}
	
	protected @Nonnull TokenResult onIdivideInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("//", other);
	}
	
	protected @Nonnull TokenResult onModuloInternal(TokenExecutor exec, @Nonnull Element other) {
		throw binaryOpError("%%", other);
	}
	
	public @Nonnull TokenResult onEqualTo(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__eq__", other);
		if (magic != null) {
			return magic;
		}
		return onEqualToInternal(exec, other);
	}
	
	public @Nonnull TokenResult onNotEqualTo(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__ne__", other);
		if (magic != null) {
			return magic;
		}
		return onNotEqualToInternal(exec, other);
	}
	
	public @Nonnull TokenResult onLessThan(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__lt__", other);
		if (magic != null) {
			return magic;
		}
		return onLessThanInternal(exec, other);
	}
	
	public @Nonnull TokenResult onLessOrEqual(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__le__", other);
		if (magic != null) {
			return magic;
		}
		return onLessOrEqualInternal(exec, other);
	}
	
	public @Nonnull TokenResult onMoreThan(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__gt__", other);
		if (magic != null) {
			return magic;
		}
		return onMoreThanInternal(exec, other);
	}
	
	public @Nonnull TokenResult onMoreOrEqual(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__ge__", other);
		if (magic != null) {
			return magic;
		}
		return onMoreOrEqualInternal(exec, other);
	}
	
	public @Nonnull TokenResult onPlus(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__add__", other);
		if (magic != null) {
			return magic;
		}
		return onPlusInternal(exec, other);
	}
	
	public @Nonnull TokenResult onAnd(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__and__", other);
		if (magic != null) {
			return magic;
		}
		return onAndInternal(exec, other);
	}
	
	public @Nonnull TokenResult onOr(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__or__", other);
		if (magic != null) {
			return magic;
		}
		return onOrInternal(exec, other);
	}
	
	public @Nonnull TokenResult onXor(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__xor__", other);
		if (magic != null) {
			return magic;
		}
		return onXorInternal(exec, other);
	}
	
	public @Nonnull TokenResult onMinus(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__sub__", other);
		if (magic != null) {
			return magic;
		}
		return onMinusInternal(exec, other);
	}
	
	public @Nonnull TokenResult onConcat(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__concat__", other);
		if (magic != null) {
			return magic;
		}
		return onConcatInternal(exec, other);
	}
	
	public @Nonnull TokenResult onLeftShift(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__lshift__", other);
		if (magic != null) {
			return magic;
		}
		return onLeftShiftInternal(exec, other);
	}
	
	public @Nonnull TokenResult onRightShift(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__rshift__", other);
		if (magic != null) {
			return magic;
		}
		return onRightShiftInternal(exec, other);
	}
	
	public @Nonnull TokenResult onMultiply(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__mul__", other);
		if (magic != null) {
			return magic;
		}
		return onMultiplyInternal(exec, other);
	}
	
	public @Nonnull TokenResult onDivide(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__div__", other);
		if (magic != null) {
			return magic;
		}
		return onDivideInternal(exec, other);
	}
	
	public @Nonnull TokenResult onRemainder(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__rem__", other);
		if (magic != null) {
			return magic;
		}
		return onRemainderInternal(exec, other);
	}
	
	public @Nonnull TokenResult onPower(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__pow__", other);
		if (magic != null) {
			return magic;
		}
		return onPowerInternal(exec, other);
	}
	
	public @Nonnull TokenResult onIdivide(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__floordiv__", other);
		if (magic != null) {
			return magic;
		}
		return onIdivideInternal(exec, other);
	}
	
	public @Nonnull TokenResult onModulo(TokenExecutor exec, @Nonnull Element other) {
		TokenResult magic = magicAction(exec, "__mod__", other);
		if (magic != null) {
			return magic;
		}
		return onModuloInternal(exec, other);
	}
	
	protected RuntimeException unaryOpError(String operator) {
		return new IllegalArgumentException(String.format("Unary operator \"%s\" is undefined for argument type \"%s\"!", operator, typeName()));
	}
	
	public @Nonnull TokenResult onNotInternal(TokenExecutor exec) {
		throw unaryOpError("!");
	}
	
	public @Nonnull TokenResult onNot(TokenExecutor exec) {
		TokenResult magic = magicAction(exec, "__not__");
		if (magic != null) {
			return magic;
		}
		return onNotInternal(exec);
	}
	
	public @Nullable IterElement iterator(TokenExecutor exec) {
		return null;
	}
	
	public @Nullable Iterable<Element> internalIterable(TokenExecutor exec) {
		@Nullable IterElement iter = iterator(exec);
		return iter == null ? null : () -> iter.internalIterator(exec);
	}
	
	public @Nullable Stream<Element> internalStream(TokenExecutor exec) {
		@Nullable Iterable<Element> iterable = internalIterable(exec);
		return iterable == null ? null : DSSLHelpers.stream(iterable);
	}
	
	protected RuntimeException builtInMethodError(String name) {
		return new IllegalArgumentException(String.format("Built-in method \"%s\" is undefined for argument type \"%s\"!", name, typeName()));
	}
	
	public @Nonnull Element iter(TokenExecutor exec) {
		throw builtInMethodError("iter");
	}
	
	public void unpack(TokenExecutor exec) {
		throw builtInMethodError("unpack");
	}
	
	public int size(TokenExecutor exec) {
		throw builtInMethodError("size");
	}
	
	public boolean isEmpty(TokenExecutor exec) {
		throw builtInMethodError("isEmpty");
	}
	
	public boolean contains(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("contains");
	}
	
	public void push(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("push");
	}
	
	public void insert(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("insert");
	}
	
	public void add(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("add");
	}
	
	public void remove(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("remove");
	}
	
	public boolean containsAll(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("containsAll");
	}
	
	public void pushAll(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("pushAll");
	}
	
	public void insertAll(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("insertAll");
	}
	
	public void addAll(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("addAll");
	}
	
	public void removeAll(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("removeAll");
	}
	
	public @Nonnull Element pop(TokenExecutor exec) {
		throw builtInMethodError("pop");
	}
	
	public void clear(TokenExecutor exec) {
		throw builtInMethodError("clear");
	}
	
	public @Nonnull Element get(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("get");
	}
	
	public void set(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("set");
	}
	
	public @Nonnull Element slice(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("slice");
	}
	
	public @Nonnull Element startsWith(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("startsWith");
	}
	
	public @Nonnull Element endsWith(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("endsWith");
	}
	
	public @Nonnull Element matches(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("matches");
	}
	
	public @Nonnull Element replace(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("replace");
	}
	
	public @Nonnull Element split(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("split");
	}
	
	public @Nonnull Element lower(TokenExecutor exec) {
		throw builtInMethodError("lower");
	}
	
	public @Nonnull Element upper(TokenExecutor exec) {
		throw builtInMethodError("upper");
	}
	
	public @Nonnull Element trim(TokenExecutor exec) {
		throw builtInMethodError("trim");
	}
	
	public @Nonnull Element format(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("format");
	}
	
	public void removeValue(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("removeValue");
	}
	
	public @Nonnull Element fst(TokenExecutor exec) {
		throw builtInMethodError("fst");
	}
	
	public @Nonnull Element snd(TokenExecutor exec) {
		throw builtInMethodError("snd");
	}
	
	public @Nonnull Element last(TokenExecutor exec) {
		throw builtInMethodError("last");
	}
	
	public @Nonnull Element indexOf(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("indexOf");
	}
	
	public @Nonnull Element lastIndexOf(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("lastIndexOf");
	}
	
	public void reverse(TokenExecutor exec) {
		throw builtInMethodError("reverse");
	}
	
	public void sort(TokenExecutor exec) {
		throw builtInMethodError("sort");
	}
	
	public void sortBy(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("sortBy");
	}
	
	public void shuffle(TokenExecutor exec) {
		throw builtInMethodError("shuffle");
	}
	
	public void put(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("put");
	}
	
	public void putAll(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("putAll");
	}
	
	public void removeEntry(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("removeEntry");
	}
	
	public boolean containsKey(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("containsKey");
	}
	
	public boolean containsValue(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("containsValue");
	}
	
	public boolean containsEntry(TokenExecutor exec, @Nonnull Element elem0, @Nonnull Element elem1) {
		throw builtInMethodError("containsEntry");
	}
	
	public @Nonnull Element keys(TokenExecutor exec) {
		throw builtInMethodError("keys");
	}
	
	public @Nonnull Element values(TokenExecutor exec) {
		throw builtInMethodError("values");
	}
	
	public @Nonnull Element entries(TokenExecutor exec) {
		throw builtInMethodError("entries");
	}
	
	public @Nonnull Element collectString(TokenExecutor exec) {
		throw builtInMethodError("collectString");
	}
	
	public @Nonnull Element collectList(TokenExecutor exec) {
		throw builtInMethodError("collectList");
	}
	
	public @Nonnull Element collectSet(TokenExecutor exec) {
		throw builtInMethodError("collectSet");
	}
	
	public @Nonnull Element collectDict(TokenExecutor exec) {
		throw builtInMethodError("collectDict");
	}
	
	public @Nonnull Element stepBy(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("stepBy");
	}
	
	public @Nonnull Element chain(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("chain");
	}
	
	public @Nonnull Element zip(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("zip");
	}
	
	public @Nonnull Element map(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("map");
	}
	
	public @Nonnull Element filter(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("filter");
	}
	
	public @Nonnull Element filterMap(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("filterMap");
	}
	
	public @Nonnull Element enumerate(TokenExecutor exec) {
		throw builtInMethodError("enumerate");
	}
	
	public @Nonnull Element takeWhile(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("takeWhile");
	}
	
	public @Nonnull Element mapWhile(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("mapWhile");
	}
	
	public @Nonnull Element skip(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("skip");
	}
	
	public @Nonnull Element take(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("take");
	}
	
	public @Nonnull Element flatten(TokenExecutor exec) {
		throw builtInMethodError("flatten");
	}
	
	public @Nonnull Element flatMap(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("flatMap");
	}
	
	public @Nonnull Element chunks(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("chunks");
	}
	
	public int count(TokenExecutor exec) {
		throw builtInMethodError("count");
	}
	
	public void forEach(TokenExecutor exec, @Nonnull Element elem) {
		throw builtInMethodError("forEach");
	}
	
	public boolean all(TokenExecutor exec) {
		throw builtInMethodError("all");
	}
	
	public boolean any(TokenExecutor exec) {
		throw builtInMethodError("any");
	}
	
	public @Nonnull Element min(TokenExecutor exec) {
		throw builtInMethodError("min");
	}
	
	public @Nonnull Element max(TokenExecutor exec) {
		throw builtInMethodError("max");
	}
	
	public @Nonnull Element supers(TokenExecutor exec) {
		throw builtInMethodError("supers");
	}
	
	public @Nonnull Element dynClone(TokenExecutor exec) {
		memberAction(exec, "clone", false);
		return exec.pop();
	}
	
	public int dynHash(TokenExecutor exec) {
		memberAction(exec, "hash", false);
		
		IntElement result = exec.pop().asInt(exec);
		if (result == null) {
			throw new IllegalArgumentException(String.format("Element hashing requires method \"hash\" to return %s element!", BuiltIn.INT));
		}
		
		return result.value.raw.intValue();
	}
	
	public boolean dynEqualTo(TokenExecutor exec, @Nonnull Element elem) {
		onEqualTo(exec, elem);
		
		BoolElement result = exec.pop().asBool(exec);
		if (result == null) {
			throw new IllegalArgumentException(String.format("Element equality check requires binary operator \"==\" to return %s element!", BuiltIn.BOOL));
		}
		
		return result.primitiveBool();
	}
	
	public int dynCompareTo(TokenExecutor exec, @Nonnull Element elem) {
		onEqualTo(exec, elem);
		
		BoolElement result = exec.pop().asBool(exec);
		if (result == null) {
			throw new IllegalArgumentException(String.format("Element comparison requires binary operator \"==\" to return %s element!", BuiltIn.BOOL));
		}
		
		if (result.primitiveBool()) {
			return 0;
		}
		
		onLessThan(exec, elem);
		
		result = exec.pop().asBool(exec);
		if (result == null) {
			throw new IllegalArgumentException(String.format("Element comparison requires binary operator \"<\" to return %s element!", BuiltIn.BOOL));
		}
		
		return result.primitiveBool() ? -1 : 1;
	}
	
	public @Nonnull String innerString(@Nullable TokenExecutor exec, @Nonnull Element container) {
		return this == container ? "this" : (exec == null ? this : stringCast(exec)).toString(exec);
	}
	
	protected RuntimeException builtInMethodArgumentError(String name, String type, int n) {
		String ordinal = n > 0 ? " " + DSSLHelpers.ordinal(n) : "";
		return new IllegalArgumentException(String.format("Built-in method \"%s\" requires %s element as%s argument!", name, type, ordinal));
	}
	
	public @Nonnull IntElement methodInt(TokenExecutor exec, @Nonnull Element elem, String name, String type, int n) {
		IntElement intElem = elem.asInt(exec);
		if (intElem == null) {
			throw builtInMethodArgumentError(name, type, n);
		}
		return intElem;
	}
	
	public @Nonnull IntElement methodInt(TokenExecutor exec, @Nonnull Element elem, String name, int n) {
		return methodInt(exec, elem, name, BuiltIn.INT, n);
	}
	
	public @Nonnull IntElement methodInt(TokenExecutor exec, @Nonnull Element elem, String name) {
		return methodInt(exec, elem, name, 0);
	}
	
	public int methodIndex(TokenExecutor exec, @Nonnull Element elem, String name, int n) {
		@Nonnull IntElement intElem = methodInt(exec, elem, name, DSSLHelpers.NON_NEGATIVE_INT, n);
		int primitiveInt = intElem.primitiveInt();
		if (primitiveInt < 0) {
			throw builtInMethodArgumentError(name, DSSLHelpers.NON_NEGATIVE_INT, n);
		}
		return primitiveInt;
	}
	
	public int methodIndex(TokenExecutor exec, @Nonnull Element elem, String name) {
		return methodIndex(exec, elem, name, 0);
	}
	
	public long methodLongIndex(TokenExecutor exec, @Nonnull Element elem, String name, int n) {
		@Nonnull IntElement intElem = methodInt(exec, elem, name, DSSLHelpers.NON_NEGATIVE_INT, n);
		long primitiveLong = intElem.primitiveLong();
		if (primitiveLong < 0) {
			throw builtInMethodArgumentError(name, DSSLHelpers.NON_NEGATIVE_INT, n);
		}
		return primitiveLong;
	}
	
	public long methodLongIndex(TokenExecutor exec, @Nonnull Element elem, String name) {
		return methodLongIndex(exec, elem, name, 0);
	}
	
	public @Nonnull Element clone(TokenExecutor exec) {
		return clone();
	}
	
	public int hash(TokenExecutor exec) {
		return hashCode();
	}
	
	public @Nonnull Element scope(TokenExecutor exec) {
		@Nullable Scope memberScope = getMemberScope(MemberAccessType.STATIC);
		if (memberScope == null) {
			throw builtInMethodError("scope");
		}
		else {
			Map<ElementKey, Element> map = new HashMap<>();
			memberScope.addToScopeMap(exec, map);
			return new DictElement(interpreter, map);
		}
	}
	
	public @Nonnull String debug(TokenExecutor exec) {
		TokenResult magic = magicAction(exec, "__debug__");
		return (magic == null ? this : exec.pop()).toString(exec);
	}
	
	protected RuntimeException magicMethodError(String name) {
		return new IllegalArgumentException(String.format("Magic method \"%s\" is undefined for argument type \"%s\"!", name, typeName()));
	}
	
	public @Nullable Scope getMemberScope(@Nonnull MemberAccessType access) {
		return access.equals(MemberAccessType.STATIC) ? null : clazz;
	}
	
	public @Nullable TokenResult memberAction(TokenExecutor exec, @Nonnull String member, boolean allowStatic) {
		if (allowStatic) {
			@Nullable Scope memberScope = getMemberScope(MemberAccessType.STATIC);
			if (memberScope != null) {
				TokenResult result = memberScope.scopeAction(exec, member);
				if (result != null) {
					return result;
				}
			}
		}
		
		@Nullable Scope memberScope = getMemberScope(MemberAccessType.INSTANCE);
		if (memberScope != null) {
			exec.push(this);
			return memberScope.scopeAction(exec, member);
		}
		
		return null;
	}
	
	public @Nullable TokenResult magicAction(TokenExecutor exec, @Nonnull String identifier, @Nonnull Element... args) {
		@Nullable Scope memberScope = getMemberScope(MemberAccessType.INSTANCE);
		if (memberScope != null) {
			@Nullable Supplier<TokenResult> invokable = memberScope.scopeInvokable(exec, identifier);
			if (invokable != null) {
				for (int i = args.length - 1; i >= 0; --i) {
					exec.push(args[i]);
				}
				exec.push(this);
				return invokable.get();
			}
		}
		
		return null;
	}
	
	public @Nullable String memberAccessIdentifier(@Nonnull MemberAccessType access) {
		@Nullable Scope memberScope = getMemberScope(access);
		if (memberScope != null) {
			return memberScope.scopeIdentifier();
		}
		else {
			return toString();
		}
	}
	
	public @Nonnull String extendedIdentifier(@Nonnull String extension, @Nonnull MemberAccessType access) {
		return DSSLHelpers.extendedIdentifier(memberAccessIdentifier(access), extension);
	}
	
	public RuntimeException memberAccessError(@Nonnull String member) {
		@Nonnull String staticIdentifier = extendedIdentifier(member, MemberAccessType.STATIC);
		@Nonnull String instanceIdentifier = extendedIdentifier(member, MemberAccessType.INSTANCE);
		
		String desc;
		if (staticIdentifier.equals(instanceIdentifier)) {
			desc = String.format("\"%s\"", staticIdentifier);
		}
		else {
			desc = String.format("\"%s\" or \"%s\"", staticIdentifier, instanceIdentifier);
		}
		
		return new IllegalArgumentException(String.format("Member %s not defined!", desc));
	}
	
	protected Object formattedInternal(TokenExecutor exec) {
		return this;
	}
	
	public Object formatted(TokenExecutor exec) {
		TokenResult magic = magicAction(exec, "__fmt__");
		return (magic == null ? this : exec.pop()).formattedInternal(exec);
	}
	
	public @Nonnull Element __init__(TokenExecutor exec) {
		return this;
	}
	
	public @Nonnull StringElement __str__(TokenExecutor exec) {
		return stringCastInternal(exec);
	}
	
	public @Nonnull StringElement __debug__(TokenExecutor exec) {
		return stringCastInternal(exec);
	}
	
	public @Nonnull Element __fmt__(TokenExecutor exec) {
		return this;
	}
	
	public @Nonnull TokenResult __eq__(TokenExecutor exec, @Nonnull Element other) {
		return onEqualToInternal(exec, other);
	}
	
	public @Nonnull TokenResult __ne__(TokenExecutor exec, @Nonnull Element other) {
		return onNotEqualToInternal(exec, other);
	}
	
	public @Nonnull TokenResult __lt__(TokenExecutor exec, @Nonnull Element other) {
		return onLessThanInternal(exec, other);
	}
	
	public @Nonnull TokenResult __le__(TokenExecutor exec, @Nonnull Element other) {
		return onLessOrEqualInternal(exec, other);
	}
	
	public @Nonnull TokenResult __gt__(TokenExecutor exec, @Nonnull Element other) {
		return onMoreThanInternal(exec, other);
	}
	
	public @Nonnull TokenResult __ge__(TokenExecutor exec, @Nonnull Element other) {
		return onMoreOrEqualInternal(exec, other);
	}
	
	public @Nonnull TokenResult __add__(TokenExecutor exec, @Nonnull Element other) {
		return onPlusInternal(exec, other);
	}
	
	public @Nonnull TokenResult __and__(TokenExecutor exec, @Nonnull Element other) {
		return onAndInternal(exec, other);
	}
	
	public @Nonnull TokenResult __or__(TokenExecutor exec, @Nonnull Element other) {
		return onOrInternal(exec, other);
	}
	
	public @Nonnull TokenResult __xor__(TokenExecutor exec, @Nonnull Element other) {
		return onXorInternal(exec, other);
	}
	
	public @Nonnull TokenResult __sub__(TokenExecutor exec, @Nonnull Element other) {
		return onMinusInternal(exec, other);
	}
	
	public @Nonnull TokenResult __concat__(TokenExecutor exec, @Nonnull Element other) {
		return onConcatInternal(exec, other);
	}
	
	public @Nonnull TokenResult __lshift__(TokenExecutor exec, @Nonnull Element other) {
		return onLeftShiftInternal(exec, other);
	}
	
	public @Nonnull TokenResult __rshift__(TokenExecutor exec, @Nonnull Element other) {
		return onRightShiftInternal(exec, other);
	}
	
	public @Nonnull TokenResult __mul__(TokenExecutor exec, @Nonnull Element other) {
		return onMultiplyInternal(exec, other);
	}
	
	public @Nonnull TokenResult __div__(TokenExecutor exec, @Nonnull Element other) {
		return onDivideInternal(exec, other);
	}
	
	public @Nonnull TokenResult __rem__(TokenExecutor exec, @Nonnull Element other) {
		return onRemainderInternal(exec, other);
	}
	
	public @Nonnull TokenResult __pow__(TokenExecutor exec, @Nonnull Element other) {
		return onPowerInternal(exec, other);
	}
	
	public @Nonnull TokenResult __floordiv__(TokenExecutor exec, @Nonnull Element other) {
		return onIdivideInternal(exec, other);
	}
	
	public @Nonnull TokenResult __mod__(TokenExecutor exec, @Nonnull Element other) {
		return onModuloInternal(exec, other);
	}
	
	public @Nonnull TokenResult __not__(TokenExecutor exec) {
		return onNotInternal(exec);
	}
	
	public @Nonnull ElementKey toKey(TokenExecutor exec) {
		return new ElementKey(exec, this);
	}
	
	@Override
	public abstract @Nonnull Element clone();
	
	@Override
	public abstract int hashCode();
	
	protected int objectHashCode() {
		return super.hashCode();
	}
	
	@Override
	public abstract boolean equals(Object obj);
	
	protected boolean objectEquals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public @Nonnull String toString() {
		return toString(null);
	}
	
	public @Nonnull String toString(TokenExecutor exec) {
		return clazz.fullIdentifier + "@" + Integer.toString(objectHashCode(), 16);
	}
}
