package fr.uha.hassenforder.cage.xon.model.xon;

import java.util.List;

public class NodeFactory {

    public NodeFactory() {
    }

    public Node createJsonNode(Node child) {
        Node node = new Node();
        node.setType(NodeType.JSON);
        node.addNode(child);
        return node;
    }

    public Node createObjectNode(List<Node> children) {
        Node node = new Node();
        node.setType(NodeType.OBJECT);
        node.addNodes(children);
        return node;
    }

    public Node createArrayNode(List<Node> children) {
        Node node = new Node();
        node.setType(NodeType.ARRAY);
        node.addNodes(children);
        return node;
    }

    private Node createConstantNode(NodeValue value) {
        Node node = new Node();
        node.setType(NodeType.CONSTANT);
        node.setValue(value);
        return node;
    }

    private Node createVariableNode(NodeValue value) {
        Node node = new Node();
        node.setType(NodeType.VARIABLE);
        node.setValue(value);
        return node;
    }

    public Node createPairNode(String name, Node value) {
        Node node = new Node();
        node.setType(NodeType.PAIR);
        NodeValue nv = new NodeValue(name);
        node.addNode(createConstantNode(nv));
        node.addNode(value);
        return node;
    }

    public Node createPairNode(Node name, Node value) {
        Node node = new Node();
        node.setType(NodeType.PAIR);
        node.addNode(name);
        node.addNode(value);
        return node;
    }

    public Node createLetNode(String name, Node value) {
        Node node = new Node();
        node.setType(NodeType.INSTR_LET);
        NodeValue nv = new NodeValue(name);
        node.addNode(createConstantNode(nv));
        node.addNode(value);
        return node;
    }

    public Node createGetNode(String name) {
        NodeValue nv = new NodeValue(name);
        return createVariableNode(nv);
    }

    public Node createStringNode(String text) {
        NodeValue nv = new NodeValue(text);
        return createConstantNode(nv);
    }

    public Node createIntegerNode(int value) {
        NodeValue nv = new NodeValue(value);
        return createConstantNode(nv);
    }

    public Node createRealNode(double value) {
        NodeValue nv = new NodeValue(value);
        return createConstantNode(nv);
    }

    public Node createBoolNode(boolean value) {
        NodeValue nv = new NodeValue(value);
        return createConstantNode(nv);
    }

    public Node createAddNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_ADD);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createSubNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_SUB);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createMultNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_MULTIPLY);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createDivNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_DIVIDE);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createModNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_MODULO);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createUnaryPlusNode(Node operand) {
        Node node = new Node();
        node.setType(NodeType.EXPR_ADD);
        node.addNode(operand);
        return node;
    }

    public Node createUnaryMinusNode(Node operand) {
        Node node = new Node();
        node.setType(NodeType.EXPR_SUB);
        node.addNode(operand);
        return node;
    }

    public Node createEqNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_EQ);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createNeqNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_NEQ);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createLtNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_LT);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createGtNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_GT);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createLteNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_LTE);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createGteNode(Node left, Node right) {
        Node node = new Node();
        node.setType(NodeType.EXPR_GTE);
        node.addNode(left);
        node.addNode(right);
        return node;
    }

    public Node createIfNode(Node condition, Node thenBody, Node elseBody) {
        Node node = new Node();
        node.setType(NodeType.INSTR_IF);
        node.addNode(condition);
        node.addNode(thenBody);
        if (elseBody != null) {
            node.addNode(elseBody);
        }
        return node;
    }

    static private int loopCounter = 0;
    static private String loopPrefix = "!_i_";

    public Node createLoopNode(String name, Node count, List<Node> body) {
        Node node = new Node();
        node.setType(NodeType.INSTR_LOOP);
        if (name == null) {
            name = loopPrefix + loopCounter++;
        }
        NodeValue varName = new NodeValue(name);
        Node bodyNode = new Node();
        bodyNode.setType(NodeType.LIST);
        bodyNode.addNodes(body);
        node.addNode(createConstantNode(varName));
        node.addNode(count);
        node.addNode(bodyNode);
        return node;
    }

    public Node createWhileLoopNode(Node cond, List<Node> body) {
        Node node = new Node();
        node.setType(NodeType.INSTR_WHILE_LOOP);
        Node bodyNode = new Node();
        bodyNode.setType(NodeType.LIST);
        bodyNode.addNodes(body);
        node.addNode(cond);
        node.addNode(bodyNode);
        return node;
    }

}
