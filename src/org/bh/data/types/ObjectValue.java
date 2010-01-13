package org.bh.data.types;

public class ObjectValue implements IValue {
	private static final long serialVersionUID = 1392784563718846200L;
	protected final Object object;

	public ObjectValue(Object object) {
		this.object = object;
	}

	@Override
	public ObjectValue clone() {
		try {
			Object clone = object.getClass().getMethod("clone").invoke(object);
			return new ObjectValue(clone);
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not clone object", e);
		}

	}
	
	public Object getObject() {
		return object;
	}

	@Override
	public String toString() {
		return object.toString();
	}

	@Override
	public int hashCode() {
		return object.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ObjectValue))
			return false;
		ObjectValue other = (ObjectValue) obj;
		return object.equals(other.object);
	}
}
