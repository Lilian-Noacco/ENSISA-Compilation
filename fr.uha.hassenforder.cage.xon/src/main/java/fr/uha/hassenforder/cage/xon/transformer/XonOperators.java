package fr.uha.hassenforder.cage.xon.transformer;

import java.util.ArrayList;
import java.util.List;

public class XonOperators {

    static public XonValue addition(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() + right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() + right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() + right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() + right.getReal());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.TEXT) {
            return new XonValue().setText(left.getText() + right.getText());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setText(left.getText() + right.getInteger().toString());
        } else if (left.getType() == XonValueType.TEXT && right.getType() == XonValueType.REAL) {
            return new XonValue().setText(left.getText() + right.getReal().toString());
        } else if (left.getType() == XonValueType.BOOL && right.getType() == XonValueType.BOOL) {
            return new XonValue().setText(left.getBool().toString() + right.getBool().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for addition ");
        }
    }

    static public XonValue substract(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() - right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() - right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() - right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() - right.getReal());
        } else if (left.getType() == XonValueType.BOOL && right.getType() == XonValueType.BOOL) {
            return new XonValue().setText(left.getBool().toString() + right.getBool().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for subtraction ");
        }
    }

    static public XonValue multiply(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() * right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() * right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() * right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() * right.getReal());
        } else if (left.getType() == XonValueType.BOOL && right.getType() == XonValueType.BOOL) {
            return new XonValue().setText(left.getBool().toString() + right.getBool().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for multiplication ");
        }
    }

    static public XonValue divide(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() / right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() / right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() / right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() / right.getReal());
        } else if (left.getType() == XonValueType.BOOL && right.getType() == XonValueType.BOOL) {
            return new XonValue().setText(left.getBool().toString() + right.getBool().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for division ");
        }
    }

    static public XonValue negation(XonValue operand) throws TransformerException {
        if (operand.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(-operand.getInteger());
        } else if (operand.getType() == XonValueType.REAL) {
            return new XonValue().setReal(-operand.getReal());
        }
        throw new IllegalArgumentException("Cannot negate type: " + operand.getType());
    }

    static public XonValue modulo(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setInteger(left.getInteger() % right.getInteger());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getReal() % right.getReal());
        } else if (left.getType() == XonValueType.REAL && right.getType() == XonValueType.INTEGER) {
            return new XonValue().setReal(left.getReal() % right.getInteger().doubleValue());
        } else if (left.getType() == XonValueType.INTEGER && right.getType() == XonValueType.REAL) {
            return new XonValue().setReal(left.getInteger().doubleValue() % right.getReal());
        } else if (left.getType() == XonValueType.BOOL && right.getType() == XonValueType.BOOL) {
            return new XonValue().setText(left.getBool().toString() + right.getBool().toString());
        } else {
            throw new IllegalArgumentException("Incompatible types for modulo ");
        }
    }

    static public XonValue checkEqual(XonValue left, XonValue right) throws TransformerException {
        if (left.getType() != right.getType()) {
            return new XonValue().setBool(false);
        }
        switch (left.getType()) {
        case INTEGER:
            return new XonValue().setBool(left.getInteger().equals(right.getInteger()));
        case REAL:
            return new XonValue().setBool(left.getReal().equals(right.getReal()));
        case TEXT:
            return new XonValue().setBool(left.getText().equals(right.getText()));
        case BOOL:
            return new XonValue().setBool(left.getBool().equals(right.getBool()));
        default:
            throw new IllegalArgumentException("Unsupported types for equality: " + left.getType());
        }
    }

    static public XonValue checkNotEqual(XonValue left, XonValue right) throws TransformerException {
        XonValue eq = checkEqual(left, right);
        return new XonValue().setBool(!eq.getBool());
    }

    private static int compareNumeric(XonValue left, XonValue right) {
        double lv = (left.getType() == XonValueType.INTEGER) ? left.getInteger().doubleValue() : left.getReal();
        double rv = (right.getType() == XonValueType.INTEGER) ? right.getInteger().doubleValue() : right.getReal();
        return Double.compare(lv, rv);
    }

    private static boolean isNumeric(XonValueType t) {
        return t == XonValueType.INTEGER || t == XonValueType.REAL;
    }

    static public XonValue checkLessThan(XonValue left, XonValue right) throws TransformerException {
        if (isNumeric(left.getType()) && isNumeric(right.getType())) {
            return new XonValue().setBool(compareNumeric(left, right) < 0);
        } else if (left.getType() == XonValueType.TEXT || right.getType() == XonValueType.TEXT) {
            String ls = (left.getType() == XonValueType.TEXT) ? left.getText() : left.getContent().toString();
            String rs = (right.getType() == XonValueType.TEXT) ? right.getText() : right.getContent().toString();
            return new XonValue().setBool(ls.compareTo(rs) < 0);
        }
        throw new IllegalArgumentException("Incompatible types for less-than comparison");
    }

    static public XonValue checkGreaterThan(XonValue left, XonValue right) throws TransformerException {
        if (isNumeric(left.getType()) && isNumeric(right.getType())) {
            return new XonValue().setBool(compareNumeric(left, right) > 0);
        }
        throw new IllegalArgumentException("Incompatible types for greater-than comparison");
    }

    static public XonValue checkLessOrEqual(XonValue left, XonValue right) throws TransformerException {
        if (isNumeric(left.getType()) && isNumeric(right.getType())) {
            return new XonValue().setBool(compareNumeric(left, right) <= 0);
        }
        throw new IllegalArgumentException("Incompatible types for less-or-equal comparison");
    }

    static public XonValue checkGreaterOrEqual(XonValue left, XonValue right) throws TransformerException {
        if (isNumeric(left.getType()) && isNumeric(right.getType())) {
            return new XonValue().setBool(compareNumeric(left, right) >= 0);
        }
        throw new IllegalArgumentException("Incompatible types for greater-or-equal comparison");
    }

    static public List<XonValue> unwrap (List<XonValue> initial) {
        List<XonValue> unwrapped = new ArrayList<XonValue>();
        for (XonValue value : initial) {
            if (value.getType() == XonValueType.LIST) {
                unwrapped.addAll(unwrap(value.getList()));
            } else {
                unwrapped.add(value);
            }
        }
        return unwrapped;
    }

}
