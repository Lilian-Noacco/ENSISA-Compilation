package fr.uha.hassenforder.cage.xon.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.uha.hassenforder.cage.xon.model.xon.Node;
import fr.uha.hassenforder.cage.xon.model.xon.NodeType;
import fr.uha.hassenforder.cage.xon.model.xon.NodeVisitor;

public class Xon2Json extends NodeVisitor {

    private Map<String, XonValue> symbols = new TreeMap<String, XonValue> ();

    private XonValue result = null;

    public Xon2Json() {
    }

    private XonValue getResult() {
        return result;
    }

    private void setResult(XonValue value) {
        this.result = value;
    }

    private void storeSymbol (String key, XonValue val) {
        symbols.put(key, val);
    }

    private XonValue loadSymbol (String key) {
        if (! symbols.containsKey(key)) {
            throw new TransformerException("Undefined variable: " + key);
        }
        return symbols.get(key);
    }

    public JSONObject transform(Node node) {
        visit_Node(node);
        XonValue out = getResult();
        if (out.getType() != XonValueType.OBJECT) {
            throw new TransformerException("Expected an object as root but got: " + out);
        }
        return out.getObject();
    }

    private List<XonValue> visitAllChildren(Node node) {
        List<XonValue> collected = new ArrayList<XonValue>();
        for (Node child : node.getChildren()) {
            visit_Node(child);
            if (getResult() != null) {
                collected.add(getResult());
            }
        }
        return collected;
    }

    @Override
    public void visit_JsonNode(Node node) {
        List<XonValue> parts = visitAllChildren(node);
        if (parts.size() != 1) {
            throw new TransformerException("Expected exactly 1 child for a json node but got: " + parts.size());
        }
        setResult(parts.get(0));
    }

    @Override
    public void visit_ObjectNode(Node node) {
        List<XonValue> parts = visitAllChildren(node);
        JSONObject json = new JSONObject();
        for (XonValue part : XonOperators.unwrap(parts)) {
            if (part.getType() == XonValueType.PAIR) {
                XonValue k = part.getPair().getKey();
                if (k.getType() != XonValueType.TEXT) {
                    throw new TransformerException("Expected a text as key but got: " + k);
                }
                XonValue v = part.getPair().getValue();
                json.put(k.getText(), v.getContent());
            } else {
                throw new TransformerException("Expected a pair but got: " + part);
            }
        }
        setResult(new XonValue().setObject(json));
    }

    @Override
    public void visit_ArrayNode(Node node) {
        List<XonValue> parts = visitAllChildren(node);
        JSONArray arr = new JSONArray();
        for (XonValue part : XonOperators.unwrap(parts)) {
            arr.put(part.getContent());
        }
        setResult(new XonValue().setArray(arr));
    }

    @Override
    public void visit_PairNode(Node node) {
        List<XonValue> parts = visitAllChildren(node);
        if (parts.size() != 2) {
            throw new TransformerException("Expected exactly 2 children for a pair but got: " + parts.size());
        }
        setResult(new XonValue().setPair(new Pair<XonValue, XonValue>(parts.get(0), parts.get(1))));
    }

    @Override
    public void visit_ListNode(Node node) {
        List<XonValue> parts = visitAllChildren(node);
        setResult(new XonValue().setList(XonOperators.unwrap(parts)));
    }

    @Override
    public void visit_ConstantNode(Node node) {
        setResult(new XonValue().setValue(node.getValue()));
    }

    @Override
    public void visit_VariableNode(Node node) {
        String identifier = node.getValue().asText();
        setResult(loadSymbol(identifier));
    }

    @Override
    public void visit_LetNode(Node node) {
        Node nameNode = node.getChildren().get(0);
        visit_Node(nameNode);
        XonValue nameVal = getResult();
        if (nameVal.getType() != XonValueType.TEXT) {
            throw new TransformerException("Expected a text as variableName but got: " + nameVal);
        }
        Node exprNode = node.getChildren().get(1);
        visit_Node(exprNode);
        XonValue exprVal = getResult();
        storeSymbol(nameVal.getText(), exprVal);
        setResult(null);
    }

    @Override
    public void visit_Divide(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.divide(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Multiply(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.multiply(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Add(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        if (operands.size() == 1) {
            setResult(operands.get(0));
        } else {
            setResult(XonOperators.addition(operands.get(0), operands.get(1)));
        }
    }

    @Override
    public void visit_Sub(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        if (operands.size() == 1) {
            setResult(XonOperators.negation(operands.get(0)));
        } else {
            setResult(XonOperators.substract(operands.get(0), operands.get(1)));
        }
    }

    @Override
    public void visit_Modulo(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.modulo(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Ternary(Node node) throws TransformerException {
        setResult(null);
    }

    @Override
    public void visit_Eq(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkEqual(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Neq(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkNotEqual(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Lt(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkLessThan(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Gt(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkGreaterThan(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Lte(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkLessOrEqual(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_Gte(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        setResult(XonOperators.checkGreaterOrEqual(operands.get(0), operands.get(1)));
    }

    @Override
    public void visit_If(Node node) throws TransformerException {
        List<XonValue> operands = visitAllChildren(node);
        XonValue condVal = operands.get(0);
        XonValue thenVal = operands.get(1);
        XonValue elseVal = operands.get(2);
        if (condVal.getType() != XonValueType.BOOL) {
            throw new TransformerException("Expected a boolean as condition but got: " + condVal);
        }
        setResult((Boolean) condVal.getContent() ? thenVal : elseVal);
    }

    private XonValue fetchTail(XonValue val) {
        if (val != null && val.getType() == XonValueType.LIST) {
            List<XonValue> items = val.getList();
            if (!items.isEmpty()) {
                return items.get(items.size() - 1);
            }
        }
        return val;
    }

    private XonValue toXonValue(Object raw) throws TransformerException {
        if (raw instanceof Integer) return new XonValue().setInteger((Integer) raw);
        if (raw instanceof Double) return new XonValue().setReal((Double) raw);
        if (raw instanceof String) return new XonValue().setText((String) raw);
        if (raw instanceof Boolean) return new XonValue().setBool((Boolean) raw);
        if (raw instanceof JSONObject) return new XonValue().setObject((JSONObject) raw);
        if (raw instanceof JSONArray) return new XonValue().setArray((JSONArray) raw);
        if (raw == JSONObject.NULL) throw new TransformerException("Cannot iterate over null value");
        throw new TransformerException("Unsupported array element type: " + raw.getClass().getName());
    }

    @Override
    public void visit_Loop(Node node) throws TransformerException {
        Node varNode = node.getChildren().get(0);
        visit_Node(varNode);
        XonValue varVal = getResult();
        String iterVarName = varVal.getText();

        Node srcNode = node.getChildren().get(1);
        visit_Node(srcNode);
        XonValue srcVal = getResult();

        Node bodyNode = node.getChildren().get(2);

        List<XonValue> accumulated = new ArrayList<>();

        if (srcVal.getType() == XonValueType.INTEGER) {
            int bound = (Integer) srcVal.getContent();
            for (int idx = 0; idx < bound; idx++) {
                visit_Node(bodyNode);
                XonValue partial = fetchTail(getResult());
                if (partial != null) accumulated.add(partial);
            }
        } else if (srcVal.getType() == XonValueType.ARRAY) {
            JSONArray sourceArr = srcVal.getArray();
            for (int idx = 0; idx < sourceArr.length(); idx++) {
                Object elem = sourceArr.get(idx);
                XonValue elemVal = toXonValue(elem);
                storeSymbol(iterVarName, elemVal);
                visit_Node(bodyNode);
                XonValue partial = fetchTail(getResult());
                if (partial != null) accumulated.add(partial);
            }
        } else {
            throw new TransformerException(
                "Loop iteration value must be INTEGER or ARRAY, got: " + srcVal.getType()
            );
        }

        JSONArray outputArr = new JSONArray();
        for (XonValue item : accumulated) {
            outputArr.put(item.getContent());
        }
        setResult(new XonValue().setArray(outputArr));
    }

    @Override
    public void visit_WhileLoop(Node node) throws TransformerException {
        Node condNode = node.getChildren().get(0);
        Node bodyNode = node.getChildren().get(1);

        List<XonValue> accumulated = new ArrayList<>();

        while (true) {
            visit_Node(condNode);
            XonValue condVal = getResult();
            if (condVal.getType() != XonValueType.BOOL) {
                throw new TransformerException("Expected a boolean as condition but got: " + condVal);
            }
            if (!(Boolean) condVal.getContent()) break;
            visit_Node(bodyNode);
            XonValue partial = fetchTail(getResult());
            if (partial != null) accumulated.add(partial);
        }

        JSONArray outputArr = new JSONArray();
        for (XonValue item : accumulated) {
            outputArr.put(item.getContent());
        }
        setResult(new XonValue().setArray(outputArr));
    }

}
