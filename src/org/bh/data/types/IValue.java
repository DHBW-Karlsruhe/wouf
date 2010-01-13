package org.bh.data.types;

import java.io.Serializable;

public interface IValue extends Serializable ,Cloneable {
	IValue clone();
}
